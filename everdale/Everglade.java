package everdale;


import java.util.Map;

public class Everglade extends Producer {

    public static final int GENERATION_TIME = 5;

    public Everglade() {
        super(3, GENERATION_TIME, Resource.wood);
    }

}
