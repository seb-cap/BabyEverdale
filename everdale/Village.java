package everdale;

import java.util.*;

public class Village {

    public static final int X_SIZE = 100;
    public static final int Y_SIZE = 100;

    private Set<Coordinate> coords = new HashSet<>();

    private Map<Coordinate, Building> layout;
    private Map<Resource, Integer> inventory;

    private Set<Resident> residents;

    /**
     * Initializes a Beginner Village
     */
    public Village() {
        this.layout = new TreeMap<>();
        this.residents = new TreeSet<>();
        for (int x = 0; x < X_SIZE; x++) {
            for (int y = 0; y < Y_SIZE; y++) {
                Coordinate cur = new Coordinate(x, y);
                coords.add(cur);
                this.layout.put(cur, new Empty());
            }
        }
        // set up basic structures
        this.build(this.getCoord(X_SIZE / 2, Y_SIZE / 2), new Kitchen());

        this.build(this.getCoord(X_SIZE / 10, Y_SIZE / 10), new ClayPit());
        this.build(this.getCoord(X_SIZE - X_SIZE / 10 - 1, Y_SIZE - Y_SIZE / 10 - 1), new ClayPit());

        this.build(this.getCoord(X_SIZE / 2, Y_SIZE / 10), new Everglade());
        this.build(this.getCoord(X_SIZE / 10, Y_SIZE / 2), new Everglade());
        this.build(this.getCoord(X_SIZE / 2, Y_SIZE - Y_SIZE / 10 - 1), new Everglade());

        this.build(this.getCoord(X_SIZE - X_SIZE / 10 - 1, Y_SIZE / 10), new StonePit());
        this.build(this.getCoord(X_SIZE / 10, Y_SIZE - Y_SIZE / 10 - 1), new StonePit());

        Coordinate houseCoord = this.getCoord(X_SIZE / 2, Y_SIZE / 2 + Y_SIZE / 10);
        this.build(houseCoord, new Home());
        Resident r = ((Home)this.buildingAt(houseCoord)).getResident();
        r.goTo(houseCoord);
        this.residents.add(r);

        this.build(this.getCoord(X_SIZE / 2, Y_SIZE / 2 - Y_SIZE / 10), new Patch());

        this.build(this.getCoord(X_SIZE / 2 + X_SIZE / 10, Y_SIZE / 2), new Study());

        this.build(this.getCoord(X_SIZE / 2 - X_SIZE / 10, Y_SIZE / 2), new WoodStorage());

        this.build(this.getCoord(X_SIZE - 2, Y_SIZE / 2), new Gate());

        this.build(this.getCoord(X_SIZE / 20 * 17, Y_SIZE / 2 + Y_SIZE / 10), new OttoCart());
        this.build(this.getCoord(X_SIZE / 20 * 16, Y_SIZE / 2 - Y_SIZE / 10), new ShipCart());
        this.build(this.getCoord(X_SIZE / 20 * 17, Y_SIZE / 2 - Y_SIZE / 10), new BuildCart());
        this.build(this.getCoord(X_SIZE / 20 * 18, Y_SIZE / 2 - Y_SIZE / 10), new EventCart());

        try {
            this.initializeShrubbery(SmallTree.class);
            this.initializeShrubbery(SmallClay.class);
            this.initializeShrubbery(SmallStone.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.initializeInventory();
    }

    /**
     * Creates inventory and sets all values to 0
     */
    private void initializeInventory() {
        this.inventory = new HashMap<>();
        for (Resource r : Resource.values()) {
            this.inventory.put(r, 0);
        }
    }

    /**
     * Builds a building at a coordinate
     * @param c The Coordinate to build at
     * @param b The Building to build
     * @return String feedback for whether or not the building was placed.
     */
    public boolean build(Coordinate c, Building b) {
        if (allEmpty(c, b.getSizeX(), b.getSizeY())) {
            for (int x = b.getStartVal(c.getX(), b.getSizeX());
                 x < b.getStartVal(c.getX(), b.getSizeX()) + b.getSizeX();
                 x++) {
                for (int y = b.getStartVal(c.getY(), b.getSizeY());
                     y < b.getStartVal(c.getY(), b.getSizeY()) + b.getSizeY();
                     y++) {
                    this.layout.put(this.getCoord(x, y), b);
                }
            }
            return true;
        }
        else {
            Game.c.prompt("Invalid.");
            return false;
        }
    }

    private boolean allEmpty(Coordinate c, int Xsize, int Ysize) {
        for (int x = c.getX(); x < c.getX() + Xsize - 1; x++) {
            for (int y = c.getY(); y < c.getY() + Ysize - 1; y++) {
                if (!this.buildingAt(x, y).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Coordinate getCoord(int x, int y) {
        for (Coordinate c : this.coords) {
            if (c.equals(x, y)) return c;
        }
        throw new IllegalArgumentException("Invalid Coordinate: " + x + ", " + y);
    }

    public Building buildingAt(int x, int y) {
        return layout.get(getCoord(x, y));
    }

    public Building buildingAt(Coordinate c) {
        return buildingAt(c.getX(), c.getY());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    /**
     *
     * @param c
     * @throws Exception
     */
    private void initializeShrubbery(Class c) throws Exception {
        if (!c.getSuperclass().equals(Consumable.class)) throw new IllegalArgumentException("Not Consumable.");

        Random r = new Random();
        for (int i = 0; i < X_SIZE * Y_SIZE / 20; i++) {
            while(!this.build(this.getCoord(r.nextInt(0, X_SIZE), r.nextInt(0, Y_SIZE)),
                    (Consumable)c.getDeclaredConstructor().newInstance()));
        }
    }

    public Set<Resident> getResidents() {
        return new TreeSet<>(this.residents);
    }

    @Override
    public String toString() {
        // size of each entry in characters
        final int sizeEach = 15;
        String ret = "";
        // first blank space
        for (int j = 0; j < sizeEach; j++) {
            ret += " ";
        }
        // row of numbers at the top
        for (int i = 0; i < Y_SIZE; i++) {
            ret += i;
            for (int j = 0; j < sizeEach - ("" + i).length(); j++) {
                ret += " ";
            }
        }
        // first coordinate on left
        ret += "\n" + 0;
        for (int j = 0; j < sizeEach - 1; j++) {
            ret += " ";
        }

        int r = 0;
        for (Coordinate c : layout.keySet()) {
            // make a new row
            if (c.getX() != r) {
                ret += "\n" + c.getX();
                // add spaces for coordinate
                for (int j = 0; j < sizeEach - ("" + c.getX()).length(); j++) {
                    ret += " ";
                }
                r = c.getX();
            }
            // add building + blank space
            ret += layout.get(c).toString();
            for (int i=0; i<sizeEach-layout.get(c).toString().length(); i++) {
                ret += " ";
            }
        }
        return ret;
    }
}
