package everdale;

import java.awt.Color;

public class Home extends Building {
    private Resident owner;
    private Color color;

    public Home() {
        this("Carolina");
    }

    public Home(String name) {
        super(2);
        this.color = Color.yellow;
        this.owner = new Resident(name, this);
    }

    public Resident getResident() {
        return this.owner;
    }

    public String toString() {
        return owner.getName().charAt(0) + "'s Home.";
    }
}

