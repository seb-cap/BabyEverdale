package everdale;


public abstract class Producer extends Building {

    private int generationTime;

    public Producer(int sizeX, int sizeY, int generationTime) {
        super(sizeX, sizeY);
        this.generationTime = generationTime;
    }

    public Producer(int size, int generationTime) {
        this(size, size, generationTime);
    }


}

