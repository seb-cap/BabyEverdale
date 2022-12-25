package everdale;


/**
 * The StonePit class represents an infinite source of Stone
 */
public class StonePit extends Producer {

    /**
     * The number of ticks it takes to generate one Stone
     */
    public static final int GENERATION_TIME = 20;

    /**
     * Creates a new StonePit Object at the given Coordinate
     * @param c The Coordinate of the StonePit
     */
    public StonePit(Coordinate c) {
        super(3, GENERATION_TIME, Resource.Stone, c);
    }

}

