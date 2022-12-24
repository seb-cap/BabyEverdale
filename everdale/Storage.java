package everdale;


public abstract class Storage extends Building {

    protected Resource type;
    protected int capacity;
    protected int contains;

    /**
     * Creates a new Storage of size 2x2 for the given Resource
     * @param type the Resource for the Storage
     */
    public Storage(Resource type, Coordinate c) {
        super(2, c);
        this.type = type;
    }

    /**
     * Get the capacity of the Storage
     * @return the capacity of the Storage
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Get the current contained amount of the Storage
     * @return the current amount of the Storage
     */
    public int getCurrent() {
        return this.contains;
    }

    public Resource getResource() {
        return this.type;
    }

    public boolean isFull() {
        return this.capacity - this.contains <= 0;
    }
    public boolean storageIsEmpty() {
        return this.contains == 0;
    }
    public abstract void levelUp();

    /**
     * Adds or subtracts Resources from a Storage
     * @param n the number to add/subtract. negative means subtract.
     * @return the number of Resources that are overflown
     */
    public int add(int n) {
        while (n != 0) {
            if (n > 0) {
                this.contains++;
                n--;
            }
            if (n < 0) {
                this.contains--;
                n++;
            }
        }

        int ret = 0;
        if (this.contains > this.capacity) {
            ret = this.contains - this.capacity;
            this.contains = this.capacity;
        }
        else if (this.contains < 0) {
            ret = 0 - this.contains;
            this.contains = 0;
        }
        return ret;

    }

    public String toString() {
        return this.getResource() + " Storage: " + this.contains + "/" + this.capacity;
    }

}

