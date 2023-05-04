package org.group16.Model.Buildings;

public enum BuildingType {
    //economy
    SMALL_STONE_GATEHOUSE("small stone gatehouse"),
    //economy
    LARGE_STONE_GATEHOUSE("large stone gatehouse"),
    //war
    DRAWBRIDGE("drawbridge"),
    //war
    LOOKOUT_TOWER("lookout tower"),
    //war
    PERIMETER_TOWER("perimeter tower"),
    //war
    DEFENCE_TURRET("defence turret"),
    //war
    SQUARE_TOWER("square tower"),
    //war
    ROUND_TOWER("round tower"),
    //economy
    ARMOURY("armoury"),
    //economy
    BARRACKS("barracks"),
    //economy
    MERCENARY_POST("mercenary post"),
    //economy
    ENGINEER_GUILD("engineer guid"),
    //war
    KILLING_PIT("killing pit"),
    //economy
    INN("inn"),
    //economy
    MILL("mill"),
    //economy
    IRON_MINE("iron mine"),
    //economy
    MARKET("market"),
    //economy
    OX_TETHER("ox tether"),
    //economy
    PITCH_RIG("pitch rig"),
    //economy
    QUARRY("quarry"),
    //economy
    STOCKPILE("stockpile"),
    //economy
    WOOD_CUTTER("wood cutter"),
    //economy
    HOVEL("hove"),
    //economy
    CHURCH("church"),
    //economy
    CATHEDRAL("cathedral"),
    //economy
    ARMOURER("armourer"),
    //economy
    BLACKSMITH("blacksmith"),
    //economy
    FLETCHER("fletcher"),
    //economy
    POLE_TURNER("pole turner"),
    //economy
    OIL_SMELTER("oil smelter"),
    //TODO : ????
    //war ?
    PITCH_DITCH("pitch dig"),
    //TODO : ????
    //war?
    CAGED_WAR_DOGS("caged war dogs"),
    //TODO : ????
    //war?
    SIEGE_TENT("siege tent"),
    //economy
    STABLE("stable"),
    //economy
    APPLE_ORCHARD("apple orchard"),
    //economy
    DIARY_FARMER("diary farm"),
    //economy
    HOPS_FARMER("hops farmer"),
    //economy
    HUNTER_POST("hunter post"),
    //economy
    WHEAT_FARMER("wheat farmer"),
    //economy
    BAKERY("bakery"),
    //economy
    BREWER("brewer"),
    //economy
    GRANARY("granary"),
    TOWN_BUILDING("town building") ,
    UNEMPLOYED_PLACE("unemployed place")
    ;
    private final String strName ;
    private BuildingType(String strName){
        this.strName = strName ;
    }
}
