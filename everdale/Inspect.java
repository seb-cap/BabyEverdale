package everdale;

/**
 * The Inspect class represents an Action where a Coordinate is inspected
 * to find what is on it.
 */
public class Inspect implements Action {

    private int x;
    private int y;

    /**
     * Creates a new Inspect object
     * @param x The X coordinate to inspect
     * @param y The Y coordinate to inspect
     */
    public Inspect(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return The X coordinate to inspect
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return The Y coordinate to inspect
     */
    public int getY() {
        return this.y;
    }

}
