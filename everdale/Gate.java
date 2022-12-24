package everdale;

import java.awt.Color;

public class Gate extends Building {

    private Color color;

    public Gate(Coordinate c) {
        super(3, 5, c);
        color = Color.yellow;
    }

}

