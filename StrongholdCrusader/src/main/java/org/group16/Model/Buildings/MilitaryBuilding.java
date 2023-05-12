package org.group16.Model.Buildings;

import org.group16.Lib.Pair;
import org.group16.Model.Cell;
import org.group16.Model.Kingdom;
import org.group16.Model.KingdomType;
import org.group16.Model.People.Soldier;
import org.group16.Model.People.SoldierDetail;
import org.group16.Model.Resources.BasicResource;
import org.group16.Model.Resources.ProductData;
import org.group16.Model.Resources.Resource;

import java.util.ArrayList;

public class MilitaryBuilding extends EconomicBuilding {

    public MilitaryBuilding(ArrayList<Cell> cells, Kingdom kingdom, double buildTime, EconomicBuildingDetail detail) {
        super(cells, kingdom, buildTime, detail);
    }

    @Override
    public boolean makeResource(Resource resource, int cnt) {
        ProductData productData = null;
        for (ProductData pr : getDetail().getProductsData()) {
            if (pr.resource().equals(resource))
                productData = pr;
        }
        if (productData==null)
            return false ;
        boolean canBeBuilt = true;
        SoldierDetail soldierDetail = ((Soldier) productData.resource()).getSoldierDetail();
        if (soldierDetail.getKingdomType().equals(KingdomType.EUROPEAN) &&
                getKingdom().getResourceCount(soldierDetail.getArmor()) < cnt)
            canBeBuilt = false;
        if (soldierDetail.getKingdomType().equals(KingdomType.EUROPEAN) &&
                getKingdom().getResourceCount(soldierDetail.getWeapon()) < cnt)
            canBeBuilt = false;
        if (getKingdom().getResourceCount(BasicResource.GOLD) < cnt * soldierDetail.getGoldNeeded())
            canBeBuilt = false;
        if (!canBeBuilt || getKingdom().availableHumans() < cnt)
            return false;
        if (soldierDetail.getKingdomType().equals(KingdomType.EUROPEAN)) {
            getKingdom().useResource(soldierDetail.getArmor(), cnt);
            getKingdom().useResource(soldierDetail.getWeapon(), cnt);
        }
        getKingdom().useResource(BasicResource.GOLD, cnt * soldierDetail.getGoldNeeded());
        getKingdom().useHuman(cnt);
        while (cnt > 0) {
            new Soldier(this.getCells(), this.getKingdom(), soldierDetail);
            cnt--;
        }
        return true  ;
    }
}
