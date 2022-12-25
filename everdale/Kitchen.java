package everdale;

import java.util.*;

/**
 * The Kitchen class represents the place where all Food is stored
 * and can be converted into Soup
 */
public class Kitchen extends Storage { //TODO
    private Map<Food, Integer> inventory;

    /**
     * Creates a new Kitchen Object at the specified Coordinate
     * @param c The Coordinate of the Kitchen
     */
    public Kitchen(Coordinate c) {
        super(Resource.Soup, c);
        this.initializeInventory();
    }

    /**
     * Initializes the inventory of the Kitchen to an empty HashMap
     */
    private void initializeInventory() {
        this.inventory = new HashMap<>();
    }

    /**
     * Levels up the Kitchen. TODO.
     */
    @Override
    public void levelUp() {

    }
}

