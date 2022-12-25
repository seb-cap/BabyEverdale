package everdale;


/**
 * The SmallTree class represents a single Consumable tree that has 4 hp and
 * uses the same generation time as the Evergrove
 */
public class SmallTree extends Consumable {

    /**
     * Creates a SmallTree Object at the given Coordinate
     * @param c The Coordinate of the SmallTree
     */
    public SmallTree(Coordinate c) {
        super(Resource.Wood, 1, Evergrove.GENERATION_TIME, 4, c);
    }

}

