package everdale;

import java.awt.Color;

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
    private Home home;

    public Resident(String name, Home house) {
        this.name = name;
        this.location = new Coordinate(0, 0);
        this.home = house;
    }

    public void goTo(Coordinate c) {
        this.destination = c;
    }

    public void walk() {
        if (location.getX() > destination.getX()) {
            this.location.translate(-1, 0);
        }
        if (location.getX() < destination.getX()) {
            this.location.translate(1, 0);
        }
        if (location.getY() > destination.getY()) {
            this.location.translate(0, -1);
        }
        if (location.getY() < destination.getY()) {
            this.location.translate(0, 1);
        }
    }

    public Coordinate getLocation() {
        return this.location;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int compareTo(Resident o) {
        return this.name.compareTo(o.getName());
    }
}

