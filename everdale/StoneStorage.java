package everdale;

public class StoneStorage extends Storage {

    public static final int[] STONE_STORAGE_LEVELS = {0, 20, 40, 60, 120, 180, 240, 300, 360};
    public static final int MAX_LEVEL = 8;
    public StoneStorage() {
        super (Resource.stone);
        this.capacity = STONE_STORAGE_LEVELS[1];
    }

    public void levelUp() {
        if (this.level < MAX_LEVEL) {
            this.level++;
            this.capacity = STONE_STORAGE_LEVELS[this.level];
        }
        Game.home.updateInventoryMaxes();
    }
}
