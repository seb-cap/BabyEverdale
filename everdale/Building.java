package everdale;

public abstract class Building {
    protected int level;
    private int sizeX;
    private int sizeY;
    private Coordinate coord;

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

    public Building(int size, Coordinate c) {
        this(size, size, c);
    }

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
     * @param sizeX the sizeX to set
     */
    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    /**
     * @return the sizeY
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * @param sizeY the sizeY to set
     */
    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public void setSize(int size) {
        this.sizeX = size;
        this.sizeY = size;
    }

    public Coordinate getCoord() {
        return this.coord;
    }

    public int getX() {
        return this.coord.getX();
    }

    public int getY() {
        return this.coord.getY();
    }

    public boolean isEmpty() {
        return this instanceof Empty;
    }

    public int getStartVal(int location, int size) {
        return location - (size % 2 + size / 2 - 1);
    }

    public String toString() {
        String name = this.getClass().getName();
        return (name.substring(0, 9).equals("everdale.") ? name.substring(9) : name);
    }
}
