package everdale;


/**
 * The SmallClay class represents a Consumable block of Clay. It has 4 hp and
 * uses the same generation time as the Clay Pit.
 */
public class SmallClay extends Consumable {

    /**
     * Creates a new SmallClay at the given Coordinate
     * @param c The Coordinate of the SmallClay
     */
    public SmallClay(Coordinate c) {
        super(Resource.Clay, 1, ClayPit.GENERATION_TIME, 4, c);
    }

}

