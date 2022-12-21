package everdale;

import java.util.*;

public class Warehouse extends Building {
    Map<Product, Integer> inventory;

    public Warehouse() {
        super(3);
        this.initializeInventory();
    }

    private void initializeInventory() {
        this.inventory = new HashMap<>();
        this.inventory.put(Product.Figurine, 0);
    }
}

