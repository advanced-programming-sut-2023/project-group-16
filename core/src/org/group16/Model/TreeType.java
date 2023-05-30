package org.group16.Model;

public enum TreeType {
    NONE,
    DESERT_SHRUB,
    CHERRY_PALM,
    OLIVE_TREE,
    COCONUT_PALM,
    DATE_PALM;

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
}
