package everdale;

public class ClayStorage extends Storage {

    public static final int[] CLAY_STORAGE_LEVELS = {0, 10, 20, 30, 40, 60, 90, 120, 150, 180, 210, 240, 270};
    public static final int MAX_LEVEL = 12;

    public ClayStorage() {
        super(Resource.clay);
        this.capacity = CLAY_STORAGE_LEVELS[1];
    }

    public void levelUp() {
        if (this.level < MAX_LEVEL) {
            this.level++;
            this.capacity = CLAY_STORAGE_LEVELS[this.level];
        }
        Game.home.updateInventoryMaxes();
    }

}
