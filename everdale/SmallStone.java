package everdale;


/**
 * The SmallStone class represents a Consumable block of Stone. It has 4 hp and
 * uses the same generation time as the Stone Pit
 */
public class SmallStone extends Consumable {

    /**
     * Creates a new SmallStone Object at the given Coordinate
     * @param c The Coordinate of the SmallStone
     */
    public SmallStone(Coordinate c) {
        super(Resource.Stone, 1, StoneMine.GENERATION_TIME / 2, 4, c);
    }

}

