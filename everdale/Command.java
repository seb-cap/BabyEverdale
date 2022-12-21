package everdale;

public class Command implements Action {
    private Resident who;
    private Coordinate where;

    public Command(Resident who, Coordinate where) {
        this.who = who;
        this.where = where;
    }
}
