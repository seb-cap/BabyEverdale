package everdale;


/**
 * The Evergrove class represents an infinite source of Wood
 */
public class Evergrove extends Producer {

    /**
     * The number of ticks it takes to generate one Wood
     */
    public static final int GENERATION_TIME = 60;

    /**
     * Creates a new Evergrove at the specified Coordinate
     * @param c The Coordinate of the Evergrove
     */
    public Evergrove(Coordinate c) {
        super(3, GENERATION_TIME, Resource.Wood, c);
    }

}
