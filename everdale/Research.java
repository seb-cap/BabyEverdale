package everdale;

import java.util.Set;

/**
 * The Research class represents a research node in the research tree.
 */
public class Research {
    private int scrollsNeeded;
    private int scrollsDone;
    private String name;
    private String description;
    private Set<Research> prereqs;

    /**
     * Creates a new Research Object with the given parameters
     * @param name The String name of the Research
     * @param desc The String description of the Research
     * @param scrolls The number of scrolls required to complete the Research
     * @param prereqs The Set of Researches that must be completed prior to this one
     */
    public Research(String name, String desc, int scrolls, Set<Research> prereqs) {
        this.name = name;
        this.description = desc;
        this.scrollsNeeded = scrolls;
        this.scrollsDone = 0;
        this.prereqs = prereqs;
    }

    /**
     * Adds the specified number of scrolls to the Research progress
     * @param n The number of scrolls to add
     * @return True if the Research was completed, False if not.
     */
    public boolean addScrolls(int n) {
        this.scrollsDone += n;
        return this.scrollsDone >= this.scrollsNeeded;
    }

    /**
     * @return A String Representation of this Research: &lt;name&gt;
     */
    public String toString() {
        return this.name;
    }

    /**
     * @return The Set of prerquisite Research as a String.
     */
    public String prereqsToString() {
        return prereqs.toString();
    }
}

