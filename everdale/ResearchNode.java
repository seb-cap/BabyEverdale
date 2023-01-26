package everdale;

/**
 * The ResearchNode enum holds all Research Nodes in Everdale and their respective
 * fields including name, description, cost, and requirements.
 */
public enum ResearchNode {

    WoodStorageUpgrade2("Upgrade Wood Storage", "Lets you increase the capacity of your wood storages.", 1, 1, WoodStorage.class, 2),
    KitchenUpgrade2("Upgrade Village Kitchen", "Lets you increase the capacity of your village kitchen.", 1, 1, Kitchen.class, 2, WoodStorageUpgrade2),
    Home3("A new Home", "Lets you build a new Home for a new Villager.", 2, 2, Home.class, KitchenUpgrade2),
    ClayStorage1("Clay Digging", "Lets you dig clay and store it.", 3, 2, ClayStorage.class, Home3),
    // TODO
    ;




    public final String name;
    public final String description;
    public final int scrollsNeeded;
    public final ResearchNode[] prereqs;
    public final int studyLevel;
    public final Class<? extends Building> building;
    public final int level;
    public final Item item;
    public final boolean newBuilding;

    ResearchNode(String name, String description, int scrollsNeeded, int studyLevel, Class<? extends Building> b, int level, ResearchNode... prereqs) {
        this.name = name;
        this.description = description;
        this.scrollsNeeded = scrollsNeeded;
        this.prereqs = prereqs;
        this.studyLevel = studyLevel;
        this.building = b;
        this.level = level;
        this.newBuilding = false;
        this.item = null;
    }

    ResearchNode(String name, String description, int scrollsNeeded, int studyLevel, Item item, ResearchNode... prereqs) {
        this.name = name;
        this.description = description;
        this.scrollsNeeded = scrollsNeeded;
        this.prereqs = prereqs;
        this.studyLevel = studyLevel;
        this.item = item;
        this.building = null;
        this.newBuilding = false;
        this.level = 1;
    }

    ResearchNode(String name, String description, int scrollsNeeded, int studyLevel, Class<? extends Building> b, ResearchNode... prereqs) {
        this.name = name;
        this.description = description;
        this.scrollsNeeded = scrollsNeeded;
        this.prereqs = prereqs;
        this.studyLevel = studyLevel;
        this.building = b;
        this.level = 1;
        this.newBuilding = true;
        this.item = null;
    }

}
