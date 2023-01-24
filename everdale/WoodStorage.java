package everdale;


/**
 * The WoodStorage class represents a Storage for Wood
 */
public class WoodStorage extends Storage {

    public static int built = 1;
    public static int max = 1;

    /**
     * The amount of storage for each level of the WoodStorage. index = level.
     */
    public static final int[] WOOD_STORAGE_LEVELS = {0, 5, 10, 20, 40, 60, 90, 120, 150, 180, 210, 240, 270};
    /**
     * The max level for a WoodStorage
     */
    public static final int MAX_LEVEL = 12;

    /**
     * Creates a new WoodStorage Object at the given Coordinate
     * @param c The Coordinate of the WoodStorage
     */
    public WoodStorage(Coordinate c) {
        super(Resource.Wood, c);
        this.capacity = WOOD_STORAGE_LEVELS[1];
    }

    /**
     * Levels up the WoodStorage if it is not max level. This also
     * updates the inventory maxes of the home Village.
     */
    public void levelUp() {
        if (this.level < MAX_LEVEL) {
            this.level++;
            this.capacity = WOOD_STORAGE_LEVELS[this.level];
            Game.c.prompt(this.getClass().getSimpleName() + " construction success!", Client.Type.Success);
        }
        Game.home.updateInventoryMaxes();
    }
}

