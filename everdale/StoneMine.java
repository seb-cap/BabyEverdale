package everdale;


/**
 * The StoneMine class represents an infinite source of Stone
 */
public class StoneMine extends Producer {

    /**
     * The number of ticks it takes to generate one Stone
     */
    public static final int GENERATION_TIME = 90;

    /**
     * Creates a new StoneMine Object at the given Coordinate
     * @param c The Coordinate of the StoneMine
     */
    public StoneMine(Coordinate c) {
        super(3, GENERATION_TIME, Resource.Stone, c);
    }

}

