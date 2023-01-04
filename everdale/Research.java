package everdale;

/**
 * The Research class contains the progress done towards a specific ResearchNode.
 */
public class Research {

    private int scrollsDone;
    private final ResearchNode research;

    /**
     * Creates a new Research Object with the given parameters
     * @param r The ResearchNode node this Research Object represents
     */
    public Research(ResearchNode r) {
        this.research = r;
        this.scrollsDone = 0;
    }

    /**
     * Adds the specified number of scrolls to the Research progress
     * @param n The number of scrolls to add
     * @return True if the Research was completed, False if not.
     */
    public boolean addScrolls(int n) {
        this.scrollsDone += n;
        return this.scrollsDone >= this.research.scrollsNeeded;
    }

    /**
     * @return A String Representation of this Research: &lt;name&gt;
     */
    public String toString() {
        return this.research.name;
    }

    public ResearchNode getResearch() {
        return this.research;
    }

    public int getProgress() {
        return this.scrollsDone;
    }

    /**
     * @return The Set of prerquisite Research as a String.
     */
    public String prereqsToString() {
        return this.research.prereqs.toString();
    }

    public boolean equals(Object o) {
        if (o instanceof Research) {
            return this.research == ((Research)o).research;
        }
        return false;
    }
}

