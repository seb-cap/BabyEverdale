package everdale;

public abstract class Consumable extends Producer {

    protected int hp;

    public Consumable(Resource r, int size, int generation_time, int hp) {
        super(size, generation_time, r);
        this.hp = hp;
    }

    public void destroy(Village from) {
        from.destroy(from.findExactBuilding(this));
    }

    public void generate(Resident rFor) {
        super.generate(rFor);
        if (this.generation >= this.generationTime) {
            this.hp--;
        }
        if (this.hp <= 0) {
            this.destroy(rFor.getResidency());
        }
    }
}

