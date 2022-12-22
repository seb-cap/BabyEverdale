package everdale;


import java.util.Map;

public abstract class Producer extends Building {

    protected int generationTime;
    protected int generation;
    protected Resource res;

    public Producer(int sizeX, int sizeY, int generationTime, Resource res) {
        super(sizeX, sizeY);
        this.generationTime = generationTime;
        this.res = res;
    }

    public Producer(int size, int generationTime, Resource res) {
        this(size, size, generationTime, res);
    }

    public void generate(Resident rFor) {
        if (rFor.getResidency().allStoragesFull(this.res)) {
            rFor.setStatus(Resident.Status.waiting);
            return;
        }
        this.generation++;
        if (this.generation >= this.generationTime) {
            rFor.store(this.res);
            this.generation = 0;
        }
    }
}

