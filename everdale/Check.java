package everdale;

public class Check implements Action {

    private Resident who;

    public Check(Resident who) {
        this.who = who;
    }

    public Resident who() {
        return this.who;
    }

}
