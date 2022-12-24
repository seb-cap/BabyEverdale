package everdale;


public class Evergrove extends Producer {

    public static final int GENERATION_TIME = 5;

    public Evergrove(Coordinate c) {
        super(3, GENERATION_TIME, Resource.wood, c);
    }

}
