package everdale;


/**
 * The Patch class represents a Producer of Infinite Soup.
 */
public class Patch extends Producer {

    /**
     * The number of ticks it takes to generate one Soup
     */
    public static final int GENERATION_TIME = 80;

    /**
     * Creates a new Patch Object at the specified Coordinate
     * @param c The Coordinate of the Patch
     */
    public Patch(Coordinate c) {
        super(3, GENERATION_TIME, Resource.Soup, c);
    }

}

