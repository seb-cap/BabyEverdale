package everdale;

/**
 * Represents a Coordinate with x and y
 */
public class Coordinate implements Comparable<Coordinate> {
    private int x;
    private int y;

    /**
     * Creates a new Coordinate of x and y
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Defaults Coordinate to position (0,0)
     */
    public Coordinate() {
        this(0, 0);
    }

    /**
     * Creates a new Coordinate with the same x and y as the given Coordinate
     * @param other The Coordinate to copy
     */
    public Coordinate(Coordinate other) {
        this(other.x, other.y);
    }

    /**
     * @return The X value of the Coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return The Y value of the Coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     * Sets a Coordinate's x and y values to the parameters
     * @param x The X value
     * @param y The Y value
     */
    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets a Coordinate's x and y values to that of the given Coordinate
     * @param c The Coordinate whose values to copy
     */
    public void setCoordinate(Coordinate c) {
        this.x = c.getX();
        this.y = c.getY();
    }

    /**
     * Shifts a Coordinate's values by the given amounts
     * @param x The change in X
     * @param y The change in Y
     */
    public void translate(int x, int y) {
        this.setCoordinate(this.x + x, this.y + y);
    }

    /**
     * @return A String Representation of the Coordinate: (x, y)
     */
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    /**
     * @param o Another Object
     * @return True if this and o are equal, False if they are not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        else if (o instanceof Coordinate) {
            Coordinate other = (Coordinate) o;
            return this.x == other.getX() && this.y == other.getY();
        }
        else {
            return false;
        }
    }

    /**
     * Determines whether this Coordinate has the given x and y coordinates
     * @param x The X to check
     * @param y The Y to check
     * @return True if same Coordinate, False otherwise.
     */
    public boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }

    /**
     * This compareTo method specifies greater as a larger X value. If the X values are tied,
     * then Y values are compared.<br>
     * This means that (2, 1) > (1, 1) > (0, 1) > (0, 0).
     * @param other the object to be compared.
     * @return 1 if this is greater, 0 if they are equal, -1 if other is greater.
     */
    public int compareTo(Coordinate other) {
        if (this.x > other.getX()) {
            return 1;
        }
        if (this.x < other.getX()) {
            return -1;
        }
        if (this.y > other.getY()) {
            return 1;
        }
        if (this.y < other.getY()) {
            return -1;
        }
        return 0;
    }
}

