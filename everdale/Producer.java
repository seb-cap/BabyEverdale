package everdale;

/**
 * The Producer abstract class represents a Building that produces a Resource.
 * Residents can work at a Producer to generate a Resource for the Village
 */
public abstract class Producer extends Building {

    protected int generationTime;
    protected int generation;
    protected Resource res;

    /**
     * Creates a new Producer with the given parameters
     * @param sizeX The X size of the Producer
     * @param sizeY The Y size of the Producer
     * @param generationTime The number of ticks it takes to generate one Resource
     * @param res The type of Resource produced
     * @param c The Coordinate of the Producer
     */
    public Producer(int sizeX, int sizeY, int generationTime, Resource res, Coordinate c) {
        super(sizeX, sizeY, c);
        this.generationTime = generationTime;
        this.res = res;
    }

    /**
     * Creates a new Producer of equal X and Y size
     * @param size The size of the Producer
     * @param generationTime The number of ticks it takes to generate one Resource
     * @param res The type of Resource produced
     * @param c The Coordinate of the Producer
     */
    public Producer(int size, int generationTime, Resource res, Coordinate c) {
        this(size, size, generationTime, res, c);
    }

    /**
     * Generates a Resource for the Resident contingent upon there being an available storage.
     * If there is no Storage available, the status of the Resident is set to waiting.
     * @param rFor The Resident trying to generate a Resource
     * @return True if a Resource was generated, False if it was not.
     */
    public boolean generate(Resident rFor) {
        if (this.level == 0 || rFor.getResidency().allStoragesFull(this.res)) {
            Game.c.prompt(rFor.getName() + " is waiting for a " + this.res + " storage!", Client.Type.Warning);
            rFor.setStatus(Resident.Status.waiting);
            return false;
        }
        this.generation++;
        if (this.generation >= this.generationTime) {
            rFor.store(this.res);
            if (!(this instanceof Consumable)) this.generation = 0;
        }
        return true;
    }
}

