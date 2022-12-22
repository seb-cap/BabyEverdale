package everdale;


public class StonePit extends Producer {

    public static final int GENERATION_TIME = 20;

    public StonePit() {
        super(3, GENERATION_TIME, Resource.stone);
    }

}

