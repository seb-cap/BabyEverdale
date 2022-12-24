package everdale;

public abstract class Consumable extends Producer {

    protected int hp;

    public Consumable(Resource r, int size, int generation_time, int hp, Coordinate c) {
        super(size, generation_time, r, c);
        this.hp = hp;
    }

    public void destroy(Village from) {
        from.destroy(this.getCoord());
    }

    public boolean generate(Resident rFor) {
        if (super.generate(rFor)) {
            if (this.generation >= this.generationTime) {
                this.hp--;
                this.generation = 0;
            }
            if (this.hp <= 0) {
                this.destroy(rFor.getResidency());
            }
            return true;
        }
        return false;
    }
}

