package everdale;

import java.awt.Color;
import java.util.Map;

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
    private Home home;
    private Village residency;
    private Item holding;
    private Status status;

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        working, idle, waiting, away
    }

    public Resident(String name, Home house) {
        this.name = name;
        this.location = new Coordinate(0, 0);
        this.home = house;
        this.status = Status.idle;
    }

    public void goTo(Coordinate c) {
        this.destination = c;
        this.status = Status.working;
    }
    public void dropOff(Coordinate c) {
        this.returnDestination = new Coordinate(this.location);
        goTo(c);
    }

    public Status getStatus() {
        return this.status;
    }

    public boolean isIdle() {
        return this.status == Status.idle;
    }

    public void walk() {
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

    public void work() {
        Building at = residency.buildingAt(this.location);
        if (at instanceof Producer) {
            ((Producer)at).generate(this);
        }
        else if (at instanceof Storage) {
            if (this.holding == null) return;
            Storage here = (Storage)at;
            if (this.holding.equals(here.getResource())) {
                if (here.add(1) == 0) {
                    this.holding = null;
                }
            }
            System.out.println(returnDestination);
            if (returnDestination != null) this.goTo(returnDestination);
        }
        else if (at instanceof Home) {
            this.status = Status.idle;
        }
        else {
            this.status = Status.idle;
        }
    }

    public void setResidency(Village v) {
        this.residency = v;
    }
    public Village getResidency() {
        return this.residency;
    }

    public Coordinate getLocation() {
        return this.location;
    }

    public String getName() {
        return this.name;
    }
    public Item getHolding() {
        return this.holding;
    }
    public void give(Item i) {
        this.holding = i;
    }

    @Override
    public int compareTo(Resident o) {
        return this.name.compareTo(o.getName());
    }

    public <T extends Storage> void store(Resource r) {
        this.give(r);
        Class<? extends Storage> T = r.getResourceStorage();
        Coordinate destination = null;
        Map<Building, Coordinate> buildings = this.getResidency().buildings();
        for (Building b : buildings.keySet()) {
            if ((T.isInstance(b)) && !((T) b).isFull()) {
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

    public String toString() {
        return this.name + " (Holding: " + this.holding + ", Status: " + this.status + ")";
    }
}

