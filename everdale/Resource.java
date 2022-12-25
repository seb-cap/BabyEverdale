package everdale;


/**
 * The Resource enum contains various Resources to be used in construction and production
 */
public enum Resource implements Item {
    Wood,
    Clay,
    Stone,
    Brick,
    Plank,
    Soup;

    /**
     * @return The Class for the Storage of the Resource
     */
    public Class<? extends Storage> getResourceStorage() {
        if (this == Wood) return WoodStorage.class;
        if (this == Clay) return ClayStorage.class;
        if (this == Stone) return StoneStorage.class;
        if (this == Soup) return Kitchen.class;
        // TODO add brick and plank
        return null;
    }
}

