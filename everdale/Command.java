package everdale;

/**
 * The Command class represents a Command Action to make a Resident go somewhere.
 */
public class Command implements Action {
    private Resident who;
    private Coordinate where;

    /**
     * Creates a new Command Object for the given Resident to go to the given Coordinate
     * @param who The Resident being commanded
     * @param where The Coordinate to go to
     */
    public Command(Resident who, Coordinate where) {
        this.who = who;
        this.where = where;
    }

    /**
     * @return The Resident being commanded
     */
    public Resident who() {
        return this.who;
    }

    /**
     * @return The Coordinate the Resident is being sent to
     */
    public Coordinate where() {
        return this.where;
    }
}
