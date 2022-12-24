package everdale;


public class SmallStone extends Consumable {

    public SmallStone(Coordinate c) {
        super(Resource.stone, 1, StonePit.GENERATION_TIME, 4, c);
    }

}

