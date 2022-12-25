package everdale;

/**
 * The Building abstract class represents a Building in Everdale.
 */
public abstract class Building {
    protected int level;
    private int sizeX;
    private int sizeY;
    private Coordinate coord;

    /**
     * Initializes the Building
     * @param sizeX The X Size of the Building
     * @param sizeY The Y Size of the Building
     * @param c The Coordinate of the Building
     */
    public Building(int sizeX, int sizeY, Coordinate c) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        if (this instanceof Producer) {
            this.level = 0;
        }
        else {
            this.level = 1;
        }
        this.coord = c;
    }

    /**
     * Initializes the Building
     * @param size The size of the Building
     * @param c The Coordinate of the Building
     */
    public Building(int size, Coordinate c) {
        this(size, size, c);
    }

    /**
     * @return The Level of the Building
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * @return the sizeX
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * @return the sizeY
     */
    public int getSizeY() {
        return sizeY;
    }


    /**
     * @return The Coordinate of this Building
     */
    public Coordinate getCoord() {
        return this.coord;
    }

    /**
     * @return The X coordinate of this Building
     */
    public int getX() {
        return this.coord.getX();
    }

    /**
     * @return The Y coordinate of this Building
     */
    public int getY() {
        return this.coord.getY();
    }

    /**
     * @return Whether the Building is an instance of Empty
     */
    public boolean isEmpty() {
        return this instanceof Empty;
    }

    /**
     * Gets the starting value for creating a larger Building such that the true coordinate is centered top left
     * @param location The int true location for this coordinate (either X or Y)
     * @param size The size in this direction (X or Y)
     * @return The int start value for the Building to begin being built.
     */
    public int getStartVal(int location, int size) {
        return location - (size % 2 + size / 2 - 1);
    }

    /**
     * @return A String representation of the Building: The name of the Class by default.
     */
    public String toString() {
        String name = this.getClass().getName();
        return (name.substring(0, 9).equals("everdale.") ? name.substring(9) : name);
    }
}
