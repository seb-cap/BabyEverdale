package everdale;

import java.util.Set;
import java.util.HashSet;

public class Research {
    private int scrollsNeeded;
    private int scrollsDone;
    private String name;
    private String description;
    private Set<Research> prereqs;

    public Research(String name, String desc, int scrolls, Set<Research> prereqs) {
        this.name = name;
        this.description = desc;
        this.scrollsNeeded = scrolls;
        this.scrollsDone = 0;
        this.prereqs = prereqs;
    }

    public boolean addScrolls(int n) {
        this.scrollsDone += n;
        return this.scrollsDone >= this.scrollsNeeded;
    }

    public String toString() {
        return this.name;
    }

    public String prereqsToString() {
        return prereqs.toString();
    }
}

