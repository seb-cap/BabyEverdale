package everdale;


/**
 * The ClayPit class represents an infinite source of Clay
 */
public class ClayPit extends Producer {

    /**
     * The number of ticks it takes for one Clay to be produced from a Clay Pit.
     */
    public static final int GENERATION_TIME = 10;

    /**
     * Creates a new ClayPit at the given Coordinate
     * @param c The Coordinate of the ClayPit
     */
    public ClayPit(Coordinate c) {
        super(3, GENERATION_TIME, Resource.Clay, c);
    }
}

