package everdale;

import java.util.Queue;

public class Game {

    protected static Client c;
    public static final Village home = new Village();
    public static int tick = 0;

    public static void setClient(Client c) {
        Game.c = c;
    }

    public static void play(Queue<Action> actions) throws InterruptedException {

        while (!actions.isEmpty()) {
            c.prompt(tick);
            for (Resident r : home.getResidents()) {
                r.walk();
                c.prompt(r.getName() + " is at " + r.getLocation() + " (" + home.buildingAt(r.getLocation()) + ").");
            }
            Action cur = actions.peek();
            switch (actions.remove().getClass().toString().substring(15)) {
                case "Build":
                    Build buildAction = (Build)cur;
                    home.build(home.getCoord(buildAction.getX(), buildAction.getY()), buildAction.getB());
                    break;
                case "View":
                    c.prompt(home.toString());
                    break;
                case "Pass":
                    c.prompt("Passed.");
                default:
                    c.prompt("Invalid");
                    break;
            }
            tick++;
        }
    }
}