package everdale;

/**
 * The Build class represents a Build Action to build a Building at a Coordinate
 */
public class Build implements Action {
    private Building b;

    /**
     * Creates a new Build Object to Construct a building at a coordinate
     * @param building The Building to build.
     */
    public Build(Building building) {
        this.b = building;
    }

    /**
     * @return The Building of the Build Object
     */
    public Building getB() {
        return b;
    }
}
