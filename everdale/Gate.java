package everdale;

import java.awt.Color;

/**
 * The Gate class represents the entry gate for the Village
 */
public class Gate extends Building {

    private Color color;

    /**
     * Creates a yellow Gate at the given Coordinate
     * @param c The Coordinate of the Gate
     */
    public Gate(Coordinate c) {
        super(3, 5, c);
        color = Color.yellow;
    }

}

