package everdale;

/**
 * The ClayStorage class represents a Storage for Clay.
 */
public class ClayStorage extends Storage {

    public static int built = 0;
    public static int max = 0;

    /**
     * The amount of Clay storage for each level of the ClayStorage. index = level.
     */
    public static final int[] CLAY_STORAGE_LEVELS = {0, 10, 20, 30, 40, 60, 90, 120, 150, 180, 210, 240, 270};
    /**
     * The max level of the ClayStorage
     */
    public static final int MAX_LEVEL = 12;

    /**
     * Creates a new ClayStorage at the given Coordinate
     * @param c The Coordinate of the ClayStorage
     */
    public ClayStorage(Coordinate c) {
        super(Resource.Clay, c);
        this.capacity = CLAY_STORAGE_LEVELS[1];
    }

    /**
     * Levels up the Storage to the next level. If it is already at max level, nothing happens.
     * This also updates the maxes of the inventory.
     */
    public void levelUp() {
        if (this.level < MAX_LEVEL) {
            this.level++;
            this.capacity = CLAY_STORAGE_LEVELS[this.level];
            Game.c.prompt(this.getClass().getSimpleName() + " construction success!", Client.Type.Success);
        }
        Game.home.updateInventoryMaxes();
    }

}
