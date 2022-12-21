package everdale;

import java.util.*;

public class Kitchen extends Building {
    private Map<Food, Integer> inventory;

    public Kitchen() {
        super(3);
        this.initializeInventory();
    }

    private void initializeInventory() {
        this.inventory = new HashMap<>();
        this.inventory.put(Food.Soup, 0);
    }
}

