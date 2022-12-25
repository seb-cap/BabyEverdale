package everdale;

/**
 * The Check class represents a Check Action where the status of a Resident is shown.
 */
public class Check implements Action {

    private Resident who;

    /**
     * Creates a new Check Object for the given Resident
     * @param who The Resident being checked
     */
    public Check(Resident who) {
        this.who = who;
    }

    /**
     * @return The Resident the Check Action is checking
     */
    public Resident who() {
        return this.who;
    }

}
