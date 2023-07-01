package org.group16.Model;

import org.group16.GameGraphics.DetailGraphics;

public enum TreeType {
    NONE(new DetailGraphics[]{}),
    DESERT_SHRUB(new DetailGraphics[]{DetailGraphics.CACTII, DetailGraphics.SHRUB1, DetailGraphics.SHRUB2}),
    CHERRY_PALM(new DetailGraphics[]{DetailGraphics.TREE_CHESTNUT1, DetailGraphics.TREE_CHESTNUT2, DetailGraphics.TREE_CHESTNUT3, DetailGraphics.TREE_CHESTNUT4}),
    OLIVE_TREE(new DetailGraphics[]{DetailGraphics.TREE_OAK1, DetailGraphics.TREE_OAK2, DetailGraphics.TREE_OAK3, DetailGraphics.TREE_OAK4}),
    COCONUT_PALM(new DetailGraphics[]{DetailGraphics.TREE_BIRCH1, DetailGraphics.TREE_BIRCH2, DetailGraphics.TREE_BIRCH3, DetailGraphics.TREE_BIRCH4}),
    DATE_PALM(new DetailGraphics[]{DetailGraphics.TREE_PINE1, DetailGraphics.TREE_PINE2, DetailGraphics.TREE_PINE3, DetailGraphics.TREE_PINE4});
    private final DetailGraphics[] graphics;

    TreeType(DetailGraphics[] graphics) {

        this.graphics = graphics;
    }

    public static TreeType getTreeTypeByName(String name) {
        name = name.toUpperCase();
        TreeType treeType;
        try {
            treeType = TreeType.valueOf(name);
        } catch (IllegalArgumentException exception) {
            treeType = null;
        }
        return treeType;
    }

    public DetailGraphics[] getGraphics() {
        return graphics;
    }
}
