package everdale;

import java.util.Queue;

/**
 * The Game class contains the main logic for playing Everdale. It handles a Queue of Actions
 * and carries them out in order.
 */
public final class Game {

    /**
     * The Client for the Game
     */
    public static Client c;
    /**
     * The home Village
     */
    public static final Village home = new Village();
    /**
     * The current tick of the game
     */
    public static long tick = 0;

    /**
     * Sets the Client of the Game. This method should be called before play in the implementing class.
     * @param c The Client to use.
     */
    public static void setClient(Client c) {
        Game.c = c;
    }

    /**
     * Plays the Game by running the given Queue of Actions one at a time. Every time an Action is
     * run, the tick counter increments by one. <br><br>
     * If a Client has not been Set, an IllegalStateException will be thrown.
     * @param actions The Queue of Actions.
     */
    public static void play(Queue<Action> actions) {

        if (c == null) throw new IllegalStateException("Client has not been set!");

        while (!actions.isEmpty()) {
            Action cur = actions.peek();
            switch (actions.remove().getClass().getSimpleName()) {
                case "Build":
                    Build buildAction = (Build)cur;
                    Building b = buildAction.getB();
                    home.build(home.getCoord(b.getX(), b.getY()), b);
                    break;
                case "View":
                    c.prompt(home.toString());
                    break;
                case "Command":
                    Command commandAction = (Command)cur;
                    commandAction.who().goTo(commandAction.where());
                    break;
                case "Inspect":
                    Inspect inspectAction = (Inspect)cur;
                    c.prompt(home.buildingAt(inspectAction.getX(), inspectAction.getY()));
                    break;
                case "Check":
                    Check checkAction = (Check)cur;
                    c.prompt(checkAction.who());
                    break;
                case "Pass":
                    break;
                default:
                    c.prompt("Invalid");
                    break;
            }
            for (Resident r : home.getResidents()) {
                r.walk();
            }
            tick++;
        }
    }
}
