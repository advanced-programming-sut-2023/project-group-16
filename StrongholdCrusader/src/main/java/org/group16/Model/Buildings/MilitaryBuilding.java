package org.group16.Model.Buildings;

import org.group16.Model.Cell;
import org.group16.Model.Kingdom;

import java.util.ArrayList;

public class MilitaryBuilding extends EconomicBuilding{

    public MilitaryBuilding(ArrayList<Cell> cells, Kingdom kingdom, EconomicBuildingDetail detail) {
        super(cells, kingdom, detail);
        //this.detail = detail;
    }
}
