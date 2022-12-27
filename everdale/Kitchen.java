package everdale;

import java.util.*;

/**
 * The Kitchen class represents the place where all Food is stored
 * and can be converted into Soup
 */
public class Kitchen extends Storage { //TODO

    public static final int[] KITCHEN_SOUP_STORAGE_LEVELS = {0, 5, 10, 20, 40, 60, 80, 100, 120, 140, 160, 180, 200};
    public static final int MAX_LEVEL = 12;

    private Map<Food, Integer> inventory;

    /**
     * Creates a new Kitchen Object at the specified Coordinate
     * @param c The Coordinate of the Kitchen
     */
    public Kitchen(Coordinate c) {
        super(3, Resource.Soup, c);
        this.initializeInventory();
    }

    /**
     * Initializes the inventory of the Kitchen to an empty HashMap
     */
    private void initializeInventory() {
        this.capacity = KITCHEN_SOUP_STORAGE_LEVELS[1];
        this.inventory = new HashMap<>();
    }

    /**
     * Levels up the Kitchen. TODO: level up food storage.
     */
    @Override
    public void levelUp() {
        if (this.level < MAX_LEVEL) {
            this.level++;
            this.capacity = KITCHEN_SOUP_STORAGE_LEVELS[this.level];
        }
    }
}

