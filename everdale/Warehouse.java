package everdale;

import java.util.*;

/**
 * The Warehouse class represents a Storage for all Products
 */
public class Warehouse extends Building { // TODO Make another class? interface? for this and kitchen
    Map<Product, Integer> inventory;

    /**
     * Creates a new Warehouse at the given Coordinate
     * @param c The Coordinate of the Warehouse
     */
    public Warehouse(Coordinate c) {
        super(3, c);
        this.initializeInventory();
    }

    /**
     * Initializes the Inventory of the Warehouse to a HashMap
     */
    private void initializeInventory() {
        this.inventory = new HashMap<>();
        this.inventory.put(Product.Figurine, 0);
    }
}

