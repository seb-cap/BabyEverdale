package everdale;


/**
 * The Empty class represents the absence of a Building
 */
public class Empty extends Building {

    /**
     * Creates a new Empty object at the given Coordinate
     * @param c the location of the Empty Object
     */
    public Empty(Coordinate c) {
        super(1, c);
    }

    /**
     * @return "Empty."
     */
    public String toString() {
        return "Empty.";
    }
}

