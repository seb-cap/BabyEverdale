package everdale;

import java.util.*;

public class Kitchen extends Building { //TODO make this extend Storage
    private Map<Food, Integer> inventory;

    public Kitchen(Coordinate c) {
        super(3, c);
        this.initializeInventory();
    }

    private void initializeInventory() {
        this.inventory = new HashMap<>();
    }
}

