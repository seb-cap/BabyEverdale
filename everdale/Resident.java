package everdale;

import java.awt.Color;
import java.util.Map;

/**
 * The Resident class represents a worker from everdale. A resident can be
 * assigned a destination, and they will go there. The Resident class manages
 * the logic for moving around and storing Resources.
 */
public class Resident implements Comparable<Resident> {
    private String name;
    private Color color;
    private int lumberSkill;
    private int claySkill;
    private int stoneSkill;
    private int farmingSkill;
    private int buildingSkill;
    private int researchSkill;
    private int animalSkill;
    private Coordinate destination;
    private Coordinate location;
    private Coordinate returnDestination;
    private Coordinate nextDestination;
    private Home home;
    private Village residency;
    private Item holding;
    private Status status;
    private boolean halted;
    private int hunger;

    /**
     * Sets the Status of the Resident to the given Status
     * @param status The Status to assign
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * The Status enum contains various statuses a Resident can be in
     */
    public enum Status {
        working,
        idle,
        waiting,
        away
    }

    /**
     * Creates a new Resident Object with the given name who lives at the given Home
     * @param name The name of the Resident
     * @param house The Resident's Home
     */
    public Resident(String name, Home house) {
        this.name = name;
        this.location = new Coordinate(0, 0);
        this.home = house;
        this.status = Status.idle;
        this.halted = false;
        this.hunger = 160;
    }

    /**
     * Commands the Resident to go to the given Coordinate. The status of the
     * Resident is set to Status.working.
     * @param c The Coordinate to go to.
     */
    public void goTo(Coordinate c) {

        // If holding an Item, finish storing first
        if ((this.getHolding() instanceof Resource) && (!((Resource)holding).getResourceStorage().isInstance(this.residency.buildingAt(c)))) {
            this.nextDestination = c;
            return;
        }

        // After Storing Item, go to the place
        if (nextDestination != null) {
            this.destination = new Coordinate(nextDestination);
            this.nextDestination = null;
            return;
        }

        if (!(residency.buildingAt(c) instanceof Storage) || this.nextDestination != null) {
            this.returnDestination = null;
        }
        this.destination = c;
        if (this.residency.buildingAt(this.destination) instanceof Home) this.status = Status.idle;
        else this.status = Status.working;
    }

    /**
     * Commands the Resident to go to the given Coordinate, then return to the
     * original (current) Coordinate.
     * @param c The Coordinate to go to.
     */
    public void dropOff(Coordinate c) {
        this.returnDestination = new Coordinate(this.location);
        goTo(c);
    }

    /**
     * @return The Status of the Resident
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * @return True if the Resident is working, False if not.
     */
    public boolean isWorking() {
        return this.status == Status.working;
    }

    /**
     * Causes the Resident to take one step towards its destination. If the destination
     * is diagonally, it will change both X and Y. If the resident did not move, it will
     * work at the current coordinate. If the Resident is halted, nothing will happen.
     */
    public void walk() {
        System.out.println(this.hunger + ", " +
                this.residency.buildingAt(this.destination).toString() + ", " +
                ((this.returnDestination != null) ?
                this.residency.buildingAt(this.returnDestination).toString() :
                "null" ) +
                ", " + this.holding + ", " + this.status);
        if (halted) return;

        if (needsSoup() && !workingAtPatch()) {
            this.eat();
        }
        else {
            if (!workingAtPatch() && this.status == Status.working) {
                this.hunger--;
            }
        }

        boolean walking = false;
        if (location.getX() > destination.getX()) {
            this.location.translate(-1, 0);
            walking = true;
        }
        if (location.getX() < destination.getX()) {
            this.location.translate(1, 0);
            walking = true;
        }
        if (location.getY() > destination.getY()) {
            this.location.translate(0, -1);
            walking = true;
        }
        if (location.getY() < destination.getY()) {
            this.location.translate(0, 1);
            walking = true;
        }

        if (!walking) this.work();

    }

    private boolean workingAtPatch() {
        return (this.residency.buildingAt(this.destination) instanceof Patch) ||
                (this.returnDestination != null && this.residency.buildingAt(this.returnDestination) instanceof Patch);
    }

    /**
     * Determines if a Resident needs soup (i.e. their hunger is at 0).
     * @return True if the Resident needs Soup, False if not.
     */
    public boolean needsSoup() {
        return this.hunger <= 0;
    }

    /**
     * Sends the Resident to the Kitchen to get food.
     * Interrupts the Resident's current process if they are not holding an Item.
     * If they are holding an Item, they will finish putting it away, then go get food.
     */
    public void eat() {
        if (returnDestination == null) returnDestination = new Coordinate(destination);
        Coordinate kCoord = this.residency.k.getCoord();
        if (!this.destination.equals(kCoord)) this.goTo(kCoord);
    }

    /**
     * Halts the Resident.
     */
    public void halt() {
        this.halted = true;
    }

    /**
     * Unhalts the Resident.
     */
    public void unhalt() {
        this.halted = false;
    }

    /**
     * @return True if the resident is halted, False if not.
     */
    public boolean isHalted() {
        return this.halted;
    }

    /**
     * The Resident works at its current location. The definition of work depends on what
     * Building is at the location.<br>
     * When at a Producer, a Resource is attempted to be generated.<br>
     * When at a Storage and holding an item, the item is dropped off<br>
     * When at a Home, the Resident becomes idle<br>
     * When at an Empty, the Resident become idle
     */
    public void work() {
        Building at = residency.buildingAt(this.location);
        if (at instanceof Producer) {
            this.status = Status.working;
            ((Producer)at).generate(this);
            return;
        }
        if (at instanceof Kitchen) {
            if (needsSoup() && !workingAtPatch()) {
                if (this.residency.inventoryFor(Resource.Soup) > 0) {
                    ((Kitchen)at).drink();
                    this.hunger = 160;
                    this.residency.increaseInventory(Resource.Soup, -1);
                    this.goTo(returnDestination);
                }
                else {
                    this.status = Status.waiting;
                }
            }
            else {
                storageStuff((Storage)at);
            }
            return;
        }
        if (at instanceof Storage) {
            storageStuff((Storage)at);
            return;
        }
        if (at instanceof Home) {
            this.status = Status.idle;
            return;
        }
        this.status = Status.idle;
    }

    private void storageStuff(Storage here) {
        if (this.holding == null) return;

        if (this.holding.equals(here.getResource())) {
            if (here.add(1) == 0) {
                this.holding = null;
                this.residency.increaseInventory(here.getResource());
            }
        }
        if (returnDestination != null) this.goTo(returnDestination);
    }

    /**
     * Sets the Village residency of the Resident
     * @param v The Village of the Resident
     */
    public void setResidency(Village v) {
        this.residency = v;
    }

    /**
     * @return The Village Object the Resident belongs to
     */
    public Village getResidency() {
        return this.residency;
    }

    /**
     * @return The Coordinate location of the Resident
     */
    public Coordinate getLocation() {
        return this.location;
    }

    /**
     * @return The String name of the Resident
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return The Item the Resident is holding
     */
    public Item getHolding() {
        return this.holding;
    }

    /**
     * Gives the Resident an Item to hold. If there is already a held object,
     * nothing happens.
     * @param i The Item to give.
     * @return True if the Item was given, False if it was not.
     */
    public boolean give(Item i) {
        if (this.holding != null) return false;
        this.holding = i;
        return true;
    }

    /**
     * Compares the name of this Resident to that of the other. If the Name of a Resident
     * is alphabetically first, they will be first.
     * @param o the object to be compared.
     * @return The result of name.compareTo(o.name).
     */
    @Override
    public int compareTo(Resident o) {
        return this.name.compareTo(o.getName());
    }

    /**
     * Finds a Storage to store the given Resource in. If available, gives the Resident
     * the Resource and commands it to drop it off at the found Storage. If no Storages
     * are available, the Resident is set to waiting status.
     * @param r The Resource to Store
     */
    public <T extends Storage> void store(Resource r) {
        Class<? extends Storage> T = r.getResourceStorage();
        Coordinate destination = null;
        Map<Building, Coordinate> buildings = this.getResidency().buildings();
        for (Building b : buildings.keySet()) {
            if ((T.isInstance(b)) && !((T) b).isFull()) {
                this.give(r);
                destination = buildings.get(b);
                break;
            }
        }
        if (destination != null) {
            dropOff(destination);
        }
        else {
            this.status = Status.waiting;
        }
    }

    /**
     * @return A String representation of the Resident: &lt;name&gt; (Holding: &lt;Item&gt;, Status: &lt;Status&gt;)
     */
    public String toString() {
        return this.name + " (Holding: " + this.holding + ", Status: " + this.status + ")";
    }
}

