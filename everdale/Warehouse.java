package everdale;

import java.util.*;

public class Warehouse extends Building { // TODO Make another class? interface? for this and kitchen
    Map<Product, Integer> inventory;

    public Warehouse(Coordinate c) {
        super(3, c);
        this.initializeInventory();
    }

    private void initializeInventory() {
        this.inventory = new HashMap<>();
        this.inventory.put(Product.Figurine, 0);
    }
}

