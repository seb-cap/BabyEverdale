package everdale;

/**
 * The Build class represents a Build Action to build a Building at a Coordinate
 */
public class Build implements Action {
    private Building b;
    private int x;
    private int y;

    /**
     * Creates a new Build Object to Construct a building at a coordinate
     * @param building The Building to build.
     * @param x The X coordinate to build at
     * @param y The Y coordinate to build at
     */
    public Build(Building building, int x, int y) {
        this.b = building;
        this.x = x;
        this.y = y;
    }

    /**
     * @return The Building of the Build Object
     */
    public Building getB() {
        return b;
    }

    /**
     * @return The X Coordinate of the Build Object
     */
    public int getX() {
        return x;
    }

    /**
     * @return The Y Coordinate of the Build Object
     */
    public int getY() {
        return y;
    }
}
