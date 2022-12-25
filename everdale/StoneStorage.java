package everdale;

/**
 * The StoneStorage class represents a Storage for Stone.
 */
public class StoneStorage extends Storage {

    /**
     * The amount of Stone storage for each level. index = level.
     */
    public static final int[] STONE_STORAGE_LEVELS = {0, 20, 40, 60, 120, 180, 240, 300, 360};
    /**
     * The max level of the StoneStorage
     */
    public static final int MAX_LEVEL = 8;

    /**
     * Creates a new StoneStorage at the given Coordinate
     * @param c The Coordinate of the StoneStorage
     */
    public StoneStorage(Coordinate c) {
        super (Resource.Stone, c);
        this.capacity = STONE_STORAGE_LEVELS[1];
    }

    /**
     * Levels up the StoneStorage as long as it is below max level.
     * This also updates the inventory maxes in the home Village.
     */
    public void levelUp() {
        if (this.level < MAX_LEVEL) {
            this.level++;
            this.capacity = STONE_STORAGE_LEVELS[this.level];
        }
        Game.home.updateInventoryMaxes();
    }
}
