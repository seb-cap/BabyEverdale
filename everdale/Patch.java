package everdale;


public class Patch extends Producer {

    public static final int GENERATION_TIME = 7;

    public Patch() {
        super(3, GENERATION_TIME, Resource.soup);
    }

}

