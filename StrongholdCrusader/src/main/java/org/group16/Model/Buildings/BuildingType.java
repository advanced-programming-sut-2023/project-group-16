package org.group16.Model.Buildings;

import org.checkerframework.checker.units.qual.A;
import org.group16.Lib.Pair;
import org.group16.Model.Cell;
import org.group16.Model.CellType;
import org.group16.Model.Resources.BasicResource;
import org.group16.Model.Resources.Resource;

import java.util.ArrayList;
import java.util.List;

public enum BuildingType implements Resource {
    //economy
    SMALL_STONE_GATEHOUSE("small stone gatehouse", 0, null, 0),
    //economy
    LARGE_STONE_GATEHOUSE("large stone gatehouse", 0, BasicResource.STONE, 20),
    //war
    DRAWBRIDGE("drawbridge", 0, BasicResource.WOOD, 10),
    //war
    LOOKOUT_TOWER("lookout tower", 0, BasicResource.STONE, 10),
    //war
    PERIMETER_TOWER("perimeter tower", 0, BasicResource.STONE, 10),
    //war
    DEFENCE_TURRET("defence turret", 0, BasicResource.STONE, 15),
    //war
    SQUARE_TOWER("square tower", 0, BasicResource.STONE, 35),
    //war
    ROUND_TOWER("round tower", 0, BasicResource.STONE, 40),
    //economy
    ARMOURY("armoury", 0, BasicResource.WOOD, 5),
    //economy
    BARRACKS("barracks", 0, BasicResource.STONE, 15),
    //economy
    MERCENARY_POST("mercenary post", 0, BasicResource.WOOD, 10),
    //economy
    ENGINEER_GUILD("engineer guid", 100, BasicResource.WOOD, 10),
    //war
    KILLING_PIT("killing pit", 0, BasicResource.WOOD, 6),
    //economy
    INN("inn", 100, BasicResource.WOOD, 20),
    //economy
    MILL("mill", 0, BasicResource.WOOD, 20),
    //economy
    IRON_MINE("iron mine", 0, BasicResource.WOOD, 20, CellType.IRON),
    //economy
    MARKET("market", 0, BasicResource.WOOD, 5),
    //economy
    OX_TETHER("ox tether", 0, BasicResource.WOOD, 5),
    //economy
    PITCH_RIG("pitch rig", 0, BasicResource.WOOD, 20, CellType.OIL),
    //economy
    QUARRY("quarry", 0, BasicResource.WOOD, 20, CellType.STONE),
    //economy
    STOCKPILE("stockpile" , 0 , null , 0 ),
    //economy
    WOOD_CUTTER("wood cutter" , 0 , BasicResource.WOOD , 3),
    //economy
    HOVEL("hovel" , 0 , BasicResource.WOOD , 6),
    //economy
    CHURCH("church" , 500 , null , 0),
    //economy
    CATHEDRAL("cathedral" , 1000 , null , 0),
    //economy
    ARMOURER("armourer" , 100 , BasicResource.WOOD , 20),
    //economy
    BLACKSMITH("blacksmith" , 200 , BasicResource.WOOD , 20),
    //economy
    FLETCHER("fletcher" , 100 , BasicResource.WOOD , 20),
    //economy
    POLE_TURNER("pole turner" , 100 , BasicResource.WOOD , 10),
    //economy
    OIL_SMELTER("oil smelter" , 100 , BasicResource.IRON , 10),
    //TODO : ????
    //war ?
    PITCH_DITCH("pitch dig" , 0 , null , 0),
    //TODO : ????
    //war?
    CAGED_WAR_DOGS("caged war dogs" , 0 , null , 0),
    //TODO : ????
    //war?
    SIEGE_TENT("siege tent" , 0 , null , 0),
    //economy
    STABLE("stable" , 400 , BasicResource.WOOD , 20),
    //economy
    APPLE_ORCHARD("apple orchard" , 0 , BasicResource.WOOD , 5 , CellType.GRASS),
    //economy
    DIARY_FARMER("diary farm" , 0 , BasicResource.WOOD , 10 , CellType.GRASS),
    //economy
    HOPS_FARMER("hops farmer" , 0 , BasicResource.WOOD , 15 , CellType.GRASS),
    //economy
    HUNTER_POST("hunter post" , 0 , BasicResource.WOOD , 5),
    //economy
    WHEAT_FARMER("wheat farmer" , 0 , BasicResource.WOOD , 15 , CellType.GRASS),
    //economy
    BAKERY("bakery" , 0 , BasicResource.WOOD , 10),
    //economy
    BREWER("brewer" , 0 , BasicResource.WOOD , 10),
    //economy
    GRANARY("granary" , 0  , BasicResource.WOOD , 5),
    TOWN_BUILDING("town building" , 0 , null , 0),
    UNEMPLOYED_PLACE("unemployed place" , 0 , null , 0);
    private final String strName;
    private final int cellSize = 1;
    //TODO : should be added
    private final ArrayList<Pair<Resource, Integer>> dependencies = new ArrayList<>();
    //TODO : should change
    private CellType cellTypeNeeded = CellType.NORMAL;

    private final int goldNeeded;
    private final Resource resource;
    private final int resourceNeeded;

    private BuildingType(String strName, int goldNeeded, Resource resource, int resourceNeeded) {
        this.strName = strName;
        this.goldNeeded = goldNeeded;
        this.resource = resource;
        this.resourceNeeded = resourceNeeded;
        this.dependencies.add(new Pair<>(BasicResource.GOLD, goldNeeded));
        if (resource != null)
            this.dependencies.add(new Pair<>(resource, resourceNeeded));
    }

    private BuildingType(String strName, int goldNeeded, Resource resource, int resourceNeeded, CellType cellTypeNeeded) {
        this(strName, goldNeeded, resource, resourceNeeded);
        this.cellTypeNeeded = cellTypeNeeded;
    }

    public String getStrName() {
        return strName;
    }

    public int getCellSize() {
        return cellSize;
    }

    public CellType getCellTypeNeeded() {
        return cellTypeNeeded;
    }

    public static BuildingType getBuildingTypeByName(String name) {
        for (BuildingType buildingType : BuildingType.values()) {
            if (buildingType.getStrName().equals(name))
                return buildingType;
        }
        return null;
    }

    public ArrayList<Pair<Resource, Integer>> getDependencies() {
        return dependencies;
    }

    public int getResultCount() {
        return 1;
    }

    public int getPrice() {
        //just should be something
        return 0;
    }

}
