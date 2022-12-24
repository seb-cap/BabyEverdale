package everdale;


public class SmallTree extends Consumable {

    public SmallTree(Coordinate c) {
        super(Resource.wood, 1, Evergrove.GENERATION_TIME, 4, c);
    }

}

