package everdale;


public class SmallClay extends Consumable {

    public SmallClay(Coordinate c) {
        super(Resource.clay, 1, ClayPit.GENERATION_TIME, 4, c);
    }

}

