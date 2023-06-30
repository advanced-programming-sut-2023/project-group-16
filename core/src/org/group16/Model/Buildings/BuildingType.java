package org.group16.Model.Buildings;

import org.group16.GameGraphics.BuildingGraphics;
import org.group16.Lib.Pair;
import org.group16.Model.CellType;
import org.group16.Model.Resources.BasicResource;
import org.group16.Model.Resources.Resource;

import java.util.ArrayList;

public enum BuildingType implements Resource {
    //economy
    SMALL_STONE_GATEHOUSE("small stone gatehouse", 0, null, 0, null),
    //economy
    LARGE_STONE_GATEHOUSE("large stone gatehouse", 0, BasicResource.STONE, 20, null),
    //war
    DRAWBRIDGE("drawbridge", 0, BasicResource.WOOD, 10, null),
    //war
    LOOKOUT_TOWER("lookout tower", 0, BasicResource.STONE, 10, BuildingGraphics.ARCHER_TOWER),
    //war
    PERIMETER_TOWER("perimeter tower", 0, BasicResource.STONE, 10, BuildingGraphics.SMALL_TOWER),
    //war
    DEFENCE_TURRET("defence turret", 0, BasicResource.STONE, 15, BuildingGraphics.BIG_TOWER),
    //war
    SQUARE_TOWER("square tower", 0, BasicResource.STONE, 35, BuildingGraphics.SQUARE_TOWER),
    //war
    ROUND_TOWER("round tower", 0, BasicResource.STONE, 40, BuildingGraphics.CIRCLE_TOWER),
    //economy
    ARMOURY("armoury", 0, BasicResource.WOOD, 5, null),
    //economy
    BARRACKS("barracks", 0, BasicResource.STONE, 15, null),
    //economy
    MERCENARY_POST("mercenary post", 0, BasicResource.WOOD, 10, null),
    //economy
    ENGINEER_GUILD("engineer guid", 100, BasicResource.WOOD, 10, null),
    //war
    KILLING_PIT("killing pit", 0, BasicResource.WOOD, 6, null),
    //economy
    INN("inn", 100, BasicResource.WOOD, 20, null),
    //economy
    MILL("mill", 0, BasicResource.WOOD, 20, null),
    //economy
    IRON_MINE("iron mine", 0, BasicResource.WOOD, 20, CellType.IRON, null),
    //economy
    MARKET("market", 0, BasicResource.WOOD, 5, null),
    //economy
    OX_TETHER("ox tether", 0, BasicResource.WOOD, 5, null),
    //economy
    PITCH_RIG("pitch rig", 0, BasicResource.WOOD, 20, CellType.OIL, null),
    //economy
    QUARRY("quarry", 0, BasicResource.WOOD, 20, CellType.STONE, null),
    //economy
    STOCKPILE("stockpile", 0, null, 0, BuildingGraphics.KING_CASTLE),
    //economy
    WOOD_CUTTER("wood cutter", 0, BasicResource.WOOD, 3, CellType.GRASS, null),
    //economy
    HOVEL("hovel", 0, BasicResource.WOOD, 6, null),
    //economy
    CHURCH("church", 500, null, 0, null),
    //economy
    CATHEDRAL("cathedral", 1000, null, 0, null),
    //economy
    ARMOURER("armourer", 100, BasicResource.WOOD, 20, null),
    //economy
    BLACKSMITH("blacksmith", 200, BasicResource.WOOD, 20, null),
    //economy
    FLETCHER("fletcher", 100, BasicResource.WOOD, 20, null),
    //economy
    POLE_TURNER("pole turner", 100, BasicResource.WOOD, 10, null),
    //economy
    OIL_SMELTER("oil smelter", 100, BasicResource.IRON, 10, null),
    //TODO : pitch ditch ?-
    //war ?
    PITCH_DITCH("pitch dig", 0, null, 0, null),
    //TODO : caged war dogs ?
    //war?
    CAGED_WAR_DOGS("caged war dogs", 0, null, 0, null),
    //TODO : siege tent ?-
    //war?
    SIEGE_TENT("siege tent", 0, null, 0, null),
    //economy
    STABLE("stable", 400, BasicResource.WOOD, 20, null),
    //economy
    APPLE_ORCHARD("apple orchard", 0, BasicResource.WOOD, 5, CellType.GRASS, null),
    //economy
    DIARY_FARMER("diary farm", 0, BasicResource.WOOD, 10, CellType.GRASS, null),
    //economy
    HOPS_FARMER("hops farmer", 0, BasicResource.WOOD, 15, CellType.GRASS, null),
    //economy
    HUNTER_POST("hunter post", 0, BasicResource.WOOD, 5, null),
    //economy
    WHEAT_FARMER("wheat farmer", 0, BasicResource.WOOD, 15, CellType.GRASS, null),
    //economy
    BAKERY("bakery", 0, BasicResource.WOOD, 10, null),
    //economy
    BREWER("brewer", 0, BasicResource.WOOD, 10, null),
    //economy
    GRANARY("granary", 0, BasicResource.WOOD, 5, null),
    TOWN_BUILDING("town building", 0, null, 0, BuildingGraphics.KING_CASTLE),
    UNEMPLOYED_PLACE("unemployed place", 0, null, 0, null);
    private final String strName;
    private final int cellSize = 1;
    private final ArrayList<Pair<Resource, Integer>> dependencies = new ArrayList<>();
    private final int goldNeeded;
    private final Resource resource;
    private final int resourceNeeded;
    private final BuildingGraphics graphics;
    //TODO : not sure about current cell types
    private CellType cellTypeNeeded = CellType.NORMAL;

    private BuildingType(String strName, int goldNeeded, Resource resource, int resourceNeeded, BuildingGraphics graphics) {
        this.strName = strName;
        this.goldNeeded = goldNeeded;
        this.resource = resource;
        this.resourceNeeded = resourceNeeded;
        this.graphics = graphics;
        this.dependencies.add(new Pair<>(BasicResource.GOLD, goldNeeded));
        if (resource != null)
            this.dependencies.add(new Pair<>(resource, resourceNeeded));
    }

    private BuildingType(String strName, int goldNeeded, Resource resource, int resourceNeeded, CellType cellTypeNeeded, BuildingGraphics graphics) {
        this(strName, goldNeeded, resource, resourceNeeded, graphics);
        this.cellTypeNeeded = cellTypeNeeded;
    }

    public static BuildingType getBuildingTypeByName(String name) {
        for (BuildingType buildingType : BuildingType.values()) {
            if (buildingType.getStrName().equals(name) || buildingType.toString().equals(name))
                return buildingType;
        }
        return null;
    }

    public int getGoldNeeded() {
        return goldNeeded;
    }

    public Resource getResource() {
        return resource;
    }

    public int getResourceNeeded() {
        return resourceNeeded;
    }

    public BuildingGraphics getGraphics() {
        return graphics;
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
    @Override
    public String GetName() {
        return this.name() ;
    }

}
