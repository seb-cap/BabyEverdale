package everdale;


public enum Resource implements Item {
    wood,
    clay,
    stone,
    brick,
    plank,
    soup;

    public Class getResourceStorage() {
        if (this == wood) return WoodStorage.class;
        if (this == clay) return ClayStorage.class;
        if (this == stone) return StoneStorage.class;
        if (this == soup) return Kitchen.class;
        // TODO add brick and plank
        return null;
    }
}

