package everdale;

import java.util.*;

/**
 * The Village class represents a Village in Everdale. The Village holds information
 * on all Buildings, Residents, and Resources. It keeps track of coordinates for each
 * Building in a Map.
 */
public class Village {

    /**
     * The X size of the Village. The maximum Coordinate will be (X_SIZE - 1, y)
     */
    public static final int X_SIZE = 100;
    /**
     * The Y size of the Village. The maximum Coordinate will be (x, Y_SIZE - 1)
     */
    public static final int Y_SIZE = 100;

    public final Kitchen k;
    public final Study s;

    private int villageXP = 6;
    private int villageLevel = 1;
    private final int[] requiredXP = new int[]{0, 8, 12, 18, 29, 41, 56, 74, 95, 116, 137, 161, 185, 209, 233, 263, 363};

    private Set<Coordinate> coords = new HashSet<>();

    private Map<Coordinate, Building> layout;
    private Map<Building, Coordinate> buildings;

    private Map<Resource, int[]> inventory;
    private List<Storage> storages;

    private Set<Resident> residents;

    private Map<Class<? extends Building>, Integer> buildables = new HashMap<>();

    private boolean initializing;

    /**
     * Creates a new Village Object, initializes all of its data structures, and
     * builds all basic starter Buildings as well as shrubbery (Consumables)
     */
    public Village() {
        this.initializing = true;
        this.layout = new TreeMap<>();
        this.residents = new TreeSet<>();
        this.buildings = new HashMap<>();
        for (int x = 0; x < X_SIZE; x++) {
            for (int y = 0; y < Y_SIZE; y++) {
                Coordinate cur = new Coordinate(x, y);
                coords.add(cur);
                this.layout.put(cur, new Empty(cur));
            }
        }
        // set up basic structures
        k = new Kitchen(this.getCoord(X_SIZE / 2, Y_SIZE / 2));
        this.build(this.getCoord(X_SIZE / 2, Y_SIZE / 2), k);

        this.build(this.getCoord(X_SIZE / 10, Y_SIZE / 10), new ClayPit(this.getCoord(X_SIZE / 10, Y_SIZE / 10)));
        this.build(this.getCoord(X_SIZE - X_SIZE / 10 - 1, Y_SIZE - Y_SIZE / 10 - 1), new ClayPit(this.getCoord(X_SIZE - X_SIZE / 10 - 1, Y_SIZE - Y_SIZE / 10 - 1)));

        this.build(this.getCoord(X_SIZE / 2, Y_SIZE / 10), new Evergrove(this.getCoord(X_SIZE / 2, Y_SIZE / 10)));
        this.build(this.getCoord(X_SIZE / 10, Y_SIZE / 2), new Evergrove(this.getCoord(X_SIZE / 10, Y_SIZE / 2)));
        this.build(this.getCoord(X_SIZE / 2, Y_SIZE - Y_SIZE / 10 - 1), new Evergrove(this.getCoord(X_SIZE / 2, Y_SIZE - Y_SIZE / 10 - 1)));

        this.build(this.getCoord(X_SIZE / 10, Y_SIZE - Y_SIZE / 10 - 1), new StoneMine(this.getCoord(X_SIZE / 10, Y_SIZE - Y_SIZE / 10 - 1)));
        this.build(this.getCoord(X_SIZE - X_SIZE / 10 - 1, Y_SIZE / 10), new StoneMine(this.getCoord(X_SIZE - X_SIZE / 10 - 1, Y_SIZE / 10)));

        // First Home
        Coordinate houseCoord = this.getCoord(X_SIZE / 2, Y_SIZE / 2 + Y_SIZE / 10);
        this.build(houseCoord, new Home("Carolina", houseCoord));
        Resident r = ((Home)this.buildingAt(houseCoord)).getResident();
        r.setResidency(this);
        r.goTo(houseCoord);
        this.residents.add(r);

        // Second Home
        Coordinate house2Coord = this.getCoord(X_SIZE / 2 + X_SIZE / 10, Y_SIZE / 2 + Y_SIZE / 10);
        this.build(house2Coord, new Home(house2Coord));
        Resident p = ((Home)this.buildingAt(house2Coord)).getResident();
        p.setResidency(this);
        p.goTo(house2Coord);
        this.residents.add(p);

        this.build(this.getCoord(X_SIZE / 2, Y_SIZE / 2 - Y_SIZE / 10), new Patch(this.getCoord(X_SIZE / 2, Y_SIZE / 2 - Y_SIZE / 10)));

        s = new Study(this.getCoord(X_SIZE / 2 + X_SIZE / 10, Y_SIZE / 2));
        this.build(this.getCoord(X_SIZE / 2 + X_SIZE / 10, Y_SIZE / 2), s);

        // Storages
        this.storages = new ArrayList<>();
        WoodStorage first = new WoodStorage(this.getCoord(X_SIZE / 2 - X_SIZE / 10, Y_SIZE / 2));
        this.build(this.getCoord(X_SIZE / 2 - X_SIZE / 10, Y_SIZE / 2), first);
        this.storages.add(first);

        this.storages.add(k);

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
        this.initializing = false;
        this.updateBuildingsMap();
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

    /**
     * Updates the max values of each inventory entry based on changes in
     * the number of or levels of Storages
     */
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

    /**
     * Adds the amount of the given resource to the inventory. This can cause
     * the inventory to go above the limit.
     * @param res The Resource to add
     * @param amount The amount of Resource to add
     */
    public void increaseInventory(Resource res, int amount) {
        this.inventory.get(res)[0] += amount;
    }

    /**
     * Adds one of the given resource to the inventory. This can cause the inventory
     * to go above the limit.
     * @param res The amount of Resource to add
     */
    public void increaseInventory(Resource res) {
        increaseInventory(res, 1);
    }

    public int inventoryFor(Resource r) {
        return this.inventory.get(r)[0];
    }

    /**
     * Updates the internal Map of all Buildings. This is called every time
     * a Building is built or destroyed.
     */
    private void updateBuildingsMap() {
        this.buildings.clear();
        for (Coordinate c : this.layout.keySet()) {
            if(!(this.buildingAt(c) instanceof Empty)) {
                this.buildings.put(this.buildingAt(c), c);
            }
        }
    }

    /**
     * @return The Map containing every Building paired with its Coordinate
     */
    public Map<Building, Coordinate> buildings() {
        return this.buildings;
    }

    /**
     * Builds a building at a coordinate. If any of the Coordinates the building will cover are
     * not Empty, the building will not be built.
     * @param c The Coordinate to build at
     * @param b The Building to build
     * @return True if the Building was build, False if not.
     */
    public boolean build(Coordinate c, Building b) {
        if (!allEmpty(c, b)) {
            if (!initializing) Game.c.prompt("Invalid location!", Client.Type.Notice);
            return false;
        }

        try {
            if (!initializing &&
                    b.getClass().getField("built").getInt(null) >= b.getClass().getField("max").getInt(null)) {
                Game.c.prompt("No more " + b.getClass().getSimpleName() + " available!", Client.Type.Notice);
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (int x = b.getStartVal(c.getX(), b.getSizeX());
             x < b.getStartVal(c.getX(), b.getSizeX()) + b.getSizeX();
             x++) {
            for (int y = b.getStartVal(c.getY(), b.getSizeY());
                 y < b.getStartVal(c.getY(), b.getSizeY()) + b.getSizeY();
                 y++) {
                this.layout.put(this.getCoord(x, y), b);
            }
        }
        if (!initializing) {
            if (b instanceof Storage) {
                this.storages.add((Storage) b);
                this.updateInventoryMaxes();
            }
            this.updateBuildingsMap();
            if (b instanceof Home) {
                Resident r = ((Home) b).getResident();
                r.setResidency(this);
                r.goTo(b.getCoord());
                this.residents.add(r);
            }
            try {
                b.getClass().getField("built").setInt(null, b.getClass().getField("built").getInt(null) + 1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.xpUp();
        }
        return true;
    }

    /**
     * Checks if all Coordinates a building will take up are Empty
     * @param c The base Coordinate of the Building
     * @param b The Building
     * @return True if all Coordinates are Empty, False if at least one is not Empty
     */
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

    /**
     * Gets the Coordinate object corresponding to an x, y pair. If a coordinate outside the
     * bounds of the Village (X_SIZE and Y_SIZE) is given, an IllegalArgumentException will be thrown.
     * @param x The X value of the Coordinate
     * @param y The Y value of the Coordinate
     * @return The corresponding Coordinate Object
     */
    public Coordinate getCoord(int x, int y) {
        for (Coordinate c : this.coords) {
            if (c.equals(x, y)) return c;
        }
        throw new IllegalArgumentException("Invalid Coordinate: " + x + ", " + y);
    }

    /**
     * Gets the Building at a given x y pair
     * @param x The X value of the Building
     * @param y The Y value of the Building
     * @return The Building at the given coordinate
     */
    public Building buildingAt(int x, int y) {
        return layout.get(getCoord(x, y));
    }

    /**
     * Gets the Building at a given Coordinate
     * @param c The Coordinate of the Building
     * @return The Building at the Coordinate
     */
    public Building buildingAt(Coordinate c) {
        return buildingAt(c.getX(), c.getY());
    }

    /**
     * Places many new Consumables around the Village. 1/60 of the area will be covered.
     * @param c The Consumable class to create
     */
    private void initializeShrubbery(Class<? extends Consumable> c) throws Exception {
        Random r = new Random();
        for (int i = 0; i < X_SIZE * Y_SIZE / 60; i++) {
            boolean built = false;
            while(!built) {
                Coordinate coord = this.getCoord(r.nextInt(0, X_SIZE), r.nextInt(0, Y_SIZE));
                built = this.build(coord, c.getDeclaredConstructor(Coordinate.class).newInstance(coord));
            }
        }
    }

    /**
     * @return A copy of the Residents Set
     */
    public Set<Resident> getResidents() {
        return new TreeSet<>(this.residents);
    }

    /**
     * Destroys the Building at the given Coordinate and replaces it with Empty.
     * @param c The Coordinate of the Building to destroy
     */
    public void destroy(Coordinate c) {
        c = this.getCoord(c.getX(), c.getY());
        this.layout.put(c, new Empty(c));
        this.updateBuildingsMap();
    }

    /**
     * @return A String representation of the layout map formatted nicely.
     */
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

    /**
     * Determines whether all Storages for the given Resource are full
     * @param res The Resource to check for
     * @return True if Storages are Full, False if not
     */
    public boolean allStoragesFull(Resource res) {
        return this.inventory.get(res)[0] >= this.inventory.get(res)[1];
    }

    /**
     * Gets a Resident based on their name. If there are duplicate names, the first one
     * returned by TreeSet will be picked. If the Resident does not exist, null will be returned.
     * @param name The name of the Resident
     * @return The Resident of that name
     */
    public Resident getResident(String name) {
        for (Resident r : this.getResidents()) {
            if (r.getName().equalsIgnoreCase(name)) return r;
        }
        return null;
    }

    /**
     * Updateds the buildables map according to the given research
     * @param searched The searched research.
     */
    public void updateBuildables(Set<ResearchNode> searched) {
        for (ResearchNode rn : searched) {
            Class<? extends Building> b = rn.building;
            int level = rn.level;

            if (b != null) {
                this.buildables.put(b, level);
            }
        }
    }

    /**
     * Gets the Buildables Map. The Keys are the classes of the Buildables. The values are the level for that Building.
     * @return The Buildables map.
     */
    public Map<Class<? extends Building>, Integer> getBuildables() {
        return this.buildables;
    }

    public List<Storage> getStorages() {
        return this.storages;
    }

    public int getVillageLevel() {
        return this.villageLevel;
    }

    public int getVillageXP() {
        return this.villageXP;
    }

    public int getNeededXP() {
        return this.requiredXP[this.villageLevel];
    }

    public void xpUp() {
        this.villageXP++;
        if (this.villageXP >= this.requiredXP[this.villageLevel]) {
            this.villageLevel++;
            this.buildables.put(Study.class, this.villageLevel);
        }
    }
}
