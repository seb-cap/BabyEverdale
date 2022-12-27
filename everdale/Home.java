package everdale;

import java.awt.Color;

/**
 * The Home class represents a house for an Everdale Resident
 */
public class Home extends Building {
    private Resident owner;
    private Color color;

    /**
     * Creates a new Home at the given Coordinate and
     * a new Resident to live there.
     * The name is defaulted to Carolina.
     * @param c The Coordinate of the Home
     */
    public Home(Coordinate c) {
        this("Carolina", c);
    }

    /**
     * Creates a new Home at the given Coordinate and
     * a new Resident to live there.
     * @param name The name of the new Resident
     * @param c The Coordinate of the Home
     */
    public Home(String name, Coordinate c) {
        super(2, c);
        this.color = Color.orange;
        this.owner = new Resident(name, this);
    }

    /**
     * @return The Resident owner of this Home
     */
    public Resident getResident() {
        return this.owner;
    }

    /**
     * @return A String representation of this Home:
     * &lt;first letter of owner's name&gt;'s Home.
     */
    public String toString() {
        return owner.getName().charAt(0) + "'s Home.";
    }
}

