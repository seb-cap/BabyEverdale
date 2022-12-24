package everdale;


import java.util.Map;

public abstract class Producer extends Building {

    protected int generationTime;
    protected int generation;
    protected Resource res;

    public Producer(int sizeX, int sizeY, int generationTime, Resource res, Coordinate c) {
        super(sizeX, sizeY, c);
        this.generationTime = generationTime;
        this.res = res;
    }

    public Producer(int size, int generationTime, Resource res, Coordinate c) {
        this(size, size, generationTime, res, c);
    }

    public boolean generate(Resident rFor) {
        if (rFor.getResidency().allStoragesFull(this.res)) {
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

