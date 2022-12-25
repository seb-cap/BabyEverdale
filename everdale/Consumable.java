package everdale;

/**
 * The Consumable class represents a Producer that can be used up (i.e. is not infinite).
 */
public abstract class Consumable extends Producer {

    protected int hp;

    /**
     * Creates a new Consumable Object with the given parameters.
     * @param r The Resource to produce
     * @param size The size of the Consumable
     * @param generation_time The number of ticks it takes to generate one Resource
     * @param hp The amount of Resources that can be extracted before the Consumable is destroyed
     * @param c The Coordinate of the Consumable
     */
    public Consumable(Resource r, int size, int generation_time, int hp, Coordinate c) {
        super(size, generation_time, r, c);
        this.hp = hp;
    }

    /**
     * Destroys the Consumable in the given Village
     * @param from The Village to delete from
     */
    public void destroy(Village from) {
        from.destroy(this.getCoord());
    }

    /**
     * Generates a Resource for the Resident at the Consumable and subtracts an HP.
     * If all Storages are full, nothing will happen.
     * @param rFor The Resident seeking a Resource
     * @return True if a Resource was generated. False if it wasn't.
     */
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

    /**
     * @return A String Representation of the Consumable: &lt;Consumable&gt; (&lt;hp&gt;)
     */
    public String toString() {
        return super.toString() + " (" + this.hp + ")";
    }
}

