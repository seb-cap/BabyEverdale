package everdale;

import java.util.*;

public class Village {

    public static final int X_SIZE = 100;
    public static final int Y_SIZE = 100;

    private Set<Coordinate> coords = new HashSet<>();

    private Map<Coordinate, Building> layout;
    private Map<Building, Coordinate> buildings;

    private Map<Resource, int[]> inventory;
    private List<Storage> storages;

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
                this.layout.put(cur, new Empty(cur));
            }
        }
        // set up basic structures
        this.build(this.getCoord(X_SIZE / 2, Y_SIZE / 2), new Kitchen(this.getCoord(X_SIZE / 2, Y_SIZE / 2)));

        this.build(this.getCoord(X_SIZE / 10, Y_SIZE / 10), new ClayPit(this.getCoord(X_SIZE / 10, Y_SIZE / 10)));
        this.build(this.getCoord(X_SIZE - X_SIZE / 10 - 1, Y_SIZE - Y_SIZE / 10 - 1), new ClayPit(this.getCoord(X_SIZE - X_SIZE / 10 - 1, Y_SIZE - Y_SIZE / 10 - 1)));

        this.build(this.getCoord(X_SIZE / 2, Y_SIZE / 10), new Evergrove(this.getCoord(X_SIZE / 2, Y_SIZE / 10)));
        this.build(this.getCoord(X_SIZE / 10, Y_SIZE / 2), new Evergrove(this.getCoord(X_SIZE / 10, Y_SIZE / 2)));
        this.build(this.getCoord(X_SIZE / 2, Y_SIZE - Y_SIZE / 10 - 1), new Evergrove(this.getCoord(X_SIZE / 2, Y_SIZE - Y_SIZE / 10 - 1)));

        this.build(this.getCoord(X_SIZE / 10, Y_SIZE - Y_SIZE / 10 - 1), new StonePit(this.getCoord(X_SIZE / 10, Y_SIZE - Y_SIZE / 10 - 1)));

        // First Home
        Coordinate houseCoord = this.getCoord(X_SIZE / 2, Y_SIZE / 2 + Y_SIZE / 10);
        this.build(houseCoord, new Home(houseCoord));
        Resident r = ((Home)this.buildingAt(houseCoord)).getResident();
        r.setResidency(this);
        r.goTo(houseCoord);
        this.residents.add(r);

        this.build(this.getCoord(X_SIZE / 2, Y_SIZE / 2 - Y_SIZE / 10), new Patch(this.getCoord(X_SIZE / 2, Y_SIZE / 2 - Y_SIZE / 10)));

        this.build(this.getCoord(X_SIZE / 2 + X_SIZE / 10, Y_SIZE / 2), new Study(this.getCoord(X_SIZE / 2 + X_SIZE / 10, Y_SIZE / 2)));

        // Storages
        this.storages = new ArrayList<>();
        WoodStorage first = new WoodStorage(this.getCoord(X_SIZE / 2 - X_SIZE / 10, Y_SIZE / 2));
        this.build(this.getCoord(X_SIZE / 2 - X_SIZE / 10, Y_SIZE / 2), first);
        this.storages.add(first);

        this.build(this.getCoord(X_SIZE - X_SIZE / 20, Y_SIZE / 2), new Gate(this.getCoord(X_SIZE - X_SIZE / 20, Y_SIZE / 2)));

        this.build(this.getCoord(X_SIZE / 20 * 17, Y_SIZE / 2 + Y_SIZE / 10), new OttoCart(this.getCoord(X_SIZE / 20 * 17, Y_SIZE / 2 + Y_SIZE / 10)));
        this.build(this.getCoord(X_SIZE / 20 * 16, Y_SIZE / 2 - Y_SIZE / 10), new ShipCart((this.getCoord(X_SIZE / 20 * 16, Y_SIZE / 2 - Y_SIZE / 10))));
        this.build(this.getCoord(X_SIZE / 20 * 17, Y_SIZE / 2 - Y_SIZE / 10), new BuildCart(this.getCoord(X_SIZE / 20 * 17, Y_SIZE / 2 - Y_SIZE / 10)));
        this.build(this.getCoord(X_SIZE / 20 * 18, Y_SIZE / 2 - Y_SIZE / 10), new EventCart(this.getCoord(X_SIZE / 20 * 18, Y_SIZE / 2 - Y_SIZE / 10)));

        try {
            this.initializeShrubbery(SmallTree.class);
            this.initializeShrubbery(SmallClay.class);
            this.initializeShrubbery(SmallStone.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.initializeInventory();
        this.initializeBuildingsMap();
    }

    /**
     * Creates inventory and sets all values to 0
     */
    private void initializeInventory() {
        this.inventory = new HashMap<>();
        for (Resource r : Resource.values()) {
            this.inventory.put(r, new int[]{0, 0});
        }
        this.updateInventoryMaxes();
    }

    public void updateInventoryMaxes() {
        Map<Resource, Integer> maxes = new HashMap<>();
        for (Storage s : this.storages) {
            if (!maxes.containsKey(s.getResource())) {
                maxes.put(s.getResource(), 0);
            }
            maxes.put(s.getResource(), maxes.get(s.getResource()) + s.getCapacity());
        }
        for (Resource r : maxes.keySet()) {
            this.inventory.get(r)[1] = maxes.get(r);
        }
    }

    public void updateBuildingsMap() {
        for (Coordinate c : this.layout.keySet()) {
            if(!(this.buildingAt(c) instanceof Empty)) {
                this.buildings.put(this.buildingAt(c), c);
            }
        }
    }

    private void initializeBuildingsMap() {
        this.buildings = new HashMap<>();
        this.updateBuildingsMap();
    }

    public Map<Building, Coordinate> buildings() {
        return this.buildings;
    }

    /**
     * Builds a building at a coordinate
     * @param c The Coordinate to build at
     * @param b The Building to build
     * @return String feedback for whether the building was placed.
     */
    public boolean build(Coordinate c, Building b) {
        if (!allEmpty(c, b))  return false;

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

    private boolean allEmpty(Coordinate c, Building b) {
        for (int x = b.getStartVal(c.getX(), b.getSizeX());
             x < b.getStartVal(c.getX(), b.getSizeX()) + b.getSizeX();
             x++) {
            for (int y = b.getStartVal(c.getY(), b.getSizeY());
                 y < b.getStartVal(c.getY(), b.getSizeY()) + b.getSizeY();
                 y++) {
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

    private void initializeShrubbery(Class<?> c) throws Exception {
        if (!c.getSuperclass().equals(Consumable.class)) throw new IllegalArgumentException("Not Consumable.");

        Random r = new Random();
        for (int i = 0; i < X_SIZE * Y_SIZE / 60; i++) {
            boolean built = false;
            while(!built) {
                Coordinate coord = this.getCoord(r.nextInt(0, X_SIZE), r.nextInt(0, Y_SIZE));
                built = this.build(coord, (Consumable)c.getDeclaredConstructor(Coordinate.class).newInstance(coord));
            }
        }
    }

    public Set<Resident> getResidents() {
        return new TreeSet<>(this.residents);
    }

    public void destroy(Coordinate c) {
        c = this.getCoord(c.getX(), c.getY());
        this.buildings.remove(this.buildingAt(c));
        this.layout.put(c, new Empty(c));
    }

    @Override
    public String toString() {
        // size of each entry in characters
        final int sizeEach = 15;
        StringBuilder ret = new StringBuilder();
        // first blank space
        ret.append(" ".repeat(sizeEach));
        // row of numbers at the top
        for (int i = 0; i < Y_SIZE; i++) {
            ret.append(i);
            ret.append(" ".repeat(Math.max(0, sizeEach - ("" + i).length())));
        }
        // first coordinate on left
        ret.append("\n" + 0);
        ret.append(" ".repeat(sizeEach - 1));

        int r = 0;
        for (Coordinate c : layout.keySet()) {
            // make a new row
            if (c.getX() != r) {
                ret.append("\n").append(c.getX());
                // add spaces for coordinate
                ret.append(" ".repeat(Math.max(0, sizeEach - ("" + c.getX()).length())));
                r = c.getX();
            }
            // add building + blank space
            ret.append(layout.get(c).toString());
            ret.append(" ".repeat(Math.max(0, sizeEach - layout.get(c).toString().length())));
        }
        return ret.toString();
    }

    public boolean allStoragesFull(Resource res) {
        return this.inventory.get(res)[0] >= this.inventory.get(res)[1];
    }

    public static Resident getResident(String name) {
        for (Resident r : Game.home.getResidents()) {
            if (r.getName().equalsIgnoreCase(name)) return r;
        }
        return null;
    }
}
