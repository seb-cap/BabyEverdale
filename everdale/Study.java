package everdale;

import java.lang.reflect.Field;
import java.util.*;

/**
 * The Study class represents the place where research takes place.
 * If a Resident is assigned to the Study, scrolls are produced.
 */
public class Study extends Building {

    /**
     * The number of ticks it takes to generate one Scroll
     */
    public static final int GENERATION_TIME = 1;

    private static final int MAX_LEVEL = 16;
    private static Set<ResearchNode> unsearched = initializeResearch();
    private static Set<ResearchNode> searched = new HashSet<>();
    private static Research current;
    private static Map<ResearchNode, Research> inProgress = new HashMap<>();
    private int generation;

    /**
     * Creates a new Study Object at the given Coordinate
     * @param c The Coordinate of the Study
     */
    public Study(Coordinate c) {
        super(3, c);
    }

    /**
     * Adds one Scroll to the current Research
     * If the research is finished, prompt and update all states.
     */
    public void research() {
        if (current == null) return;
        if (generation < GENERATION_TIME) {
            generation ++;
            return;
        }
        if (current.addScrolls(1)) {
            if (current.getResearch().newBuilding) incrementCounter();
            searched.add(current.getResearch());
            unsearched.remove(current.getResearch());
            inProgress.remove(current.getResearch());
            Game.c.prompt(current + " research completed!", Client.Type.Success);
            current = null;
            Game.home.updateBuildables(searched);
        }
    }

    /**
     * Initializes the Research tree by adding all Researches to a List
     * @return The List of all Researches in Everdale
     */
    private static Set<ResearchNode> initializeResearch() {
        Set<ResearchNode> all = new HashSet<>(Arrays.asList(ResearchNode.values()));

        current = null;

        return all;
    }

    public static void selectResearch(ResearchNode r) {
        if (inProgress.containsKey(r)) {
            current = inProgress.get(r);
        }
        else {
            Research made = new Research(r);
            inProgress.put(r, made);
            unsearched.remove(made.getResearch());
            current = made;
        }
    }

    public static Set<ResearchNode> getAvailableResearches() {
        Set<ResearchNode> rs = new HashSet<>();
        for (ResearchNode r : unsearched) {
            if (searched.containsAll(List.of(r.prereqs))) rs.add(r);
        }
        return rs;
    }

    public static Set<Research> getInProgressResearches() {
        return new HashSet<>(inProgress.values());
    }

    /**
     * Levels up the Study.
     */
    public void levelUp() {
        if (this.level < MAX_LEVEL) {
            this.level++;
            Game.c.prompt(this.getClass().getSimpleName() + " construction success!", Client.Type.Success);
        }
        // TODO
    }

    private void incrementCounter() {
        // TODO
        try {
            Field max = current.getResearch().building.getDeclaredField("max");
            max.set(null, max.getInt(null) + 1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

