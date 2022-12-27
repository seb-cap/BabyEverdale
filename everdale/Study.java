package everdale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Study class represents the place where research takes place.
 * If a Resident is assigned to the Study, scrolls are produced.
 */
public class Study extends Building {
    private static final int MAX_LEVEL = 16;
    private static List<Research> unsearched = initializeResearch();
    private static List<Research> searched = new ArrayList<>();
    private static Research current;

    public static List<Research> viewableSearched = Collections.unmodifiableList(searched);

    /**
     * Creates a new Study Object at the given Coordinate
     * @param c The Coordinate of the Study
     */
    public Study(Coordinate c) {
        super(3, c);
    }

    /**
     * Adds one Scroll to the current Research
     */
    public void research() {
        if (current == null) return;
        if (current.addScrolls(1)) {
            Game.c.prompt(current + " research completed!");
        }
    }

    /**
     * Initializes the Research tree by adding all Researches to a List
     * @return The List of all Researches in Everdale
     */
    private static List<Research> initializeResearch() {
        List<Research> all = new ArrayList<>();
        // Wood Storage Level 2
        String name = "Upgrade Wood Storage";
        String desc = "Lets you increase the capacity of your wood storages.";
        int scrolls = 1;
        Set<Research> prereqs = new HashSet<>();

        Research wood_storage_upgrade1 = new Research(name, desc, scrolls, prereqs);

        all.add(wood_storage_upgrade1);

        // Village Kitchen Level 2
        name = "Upgrade Village Kitchen";
        desc = "Lets you increase the capacity of your village kitchen.";
        scrolls = 1;
        prereqs = new HashSet<>();
        prereqs.add(wood_storage_upgrade1);

        Research village_kitchen_upgrade1 = new Research(name, desc, scrolls, prereqs);

        all.add(village_kitchen_upgrade1);

        // Home Unlocked
        name = "A new Home";
        desc = "Lets you build a new Home for a new Villager.";
        scrolls = 2;
        prereqs = new HashSet<>();
        prereqs.add(village_kitchen_upgrade1);

        Research a_new_home1 = new Research(name, desc, scrolls, prereqs);

        all.add(a_new_home1);

        // TODO DELETE THIS TEMP
        current = a_new_home1;
		/*for (Research r : all) {
			System.out.println(r.prereqsToString());
		}*/

        return all;
    }

    /**
     * Levels up the Study. This unlocks
     */
    public void levelUp() {
        if (this.level < MAX_LEVEL) {
            this.level++;
        }
        // TODO
    }

}

