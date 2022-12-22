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

    public Coordinate(Coordinate other) {
        this(other.x, other.y);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int n) {
        this.x = n;
    }

    public void setY(int n) {
        this.y = n;
    }

    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setCoordinate(Coordinate c) {
        this.x = c.getX();
        this.y = c.getY();
    }

    public void translate(int x, int y) {
        this.setCoordinate(this.x + x, this.y + y);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

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

    public boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }

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

