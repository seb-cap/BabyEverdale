package everdale;

public abstract class Consumable extends Producer {

    private int hp;

    private static Resource res;

    public Consumable(Resource r, int size, int generation_time, int hp) {
        super(size, generation_time);
        this.res = r;
        this.hp = hp;
    }
}

