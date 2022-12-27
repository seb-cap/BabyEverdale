package everdale;


/**
 * The Storage class represents a Storage for a Resource
 */
public abstract class Storage extends Building {

    protected Resource type;
    protected int capacity;
    protected int contains;

    /**
     * Creates a new Storage of size 2x2 for the given Resource
     * @param type The Resource for the Storage
     * @param c The Coordinate of the Storage
     */
    public Storage(Resource type, Coordinate c) {
        this(2, type, c);
    }

    /**
     * Creates a new Storage of the given size for the given Resource
     * @param size The size of the Storage
     * @param type The Resource for the Storage
     * @param c The Coordinate of the Storage
     */
    public Storage(int size, Resource type, Coordinate c) {
        super(size, c);
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

    /**
     * @return The Resource type of this Storage
     */
    public Resource getResource() {
        return this.type;
    }

    /**
     * @return True if the Storage is full, False if not.
     */
    public boolean isFull() {
        return this.capacity - this.contains <= 0;
    }

    /**
     * @return True if the Storage is empty, False if not.
     */
    public boolean storageIsEmpty() {
        return this.contains == 0;
    }

    /**
     * Levels up the Storage. The implementer should increase the Storage capacity
     * and update the level field, then update the Village inventory.
     */
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

    /**
     * @return A String representation of the Storage: &lt;Resource&gt; Storage: &lt;contained&gt;/&lt;capacity&gt;
     */
    public String toString() {
        return this.getResource() + " Storage: " + this.contains + "/" + this.capacity;
    }

}

