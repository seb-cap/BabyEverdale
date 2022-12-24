package everdale;


public class ClayPit extends Producer {

    public static final int GENERATION_TIME = 10;

    public ClayPit(Coordinate c) {
        super(3, GENERATION_TIME, Resource.clay, c);
    }
}

