package client.console;

import everdale.*;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;


/**
 * The EverdaleConsole class is a Client for Everdale.<br>
 * It uses the java console to prompt the user for actions
 * that are then sent to the Everdale package for gameplay.
 */
public class EverdaleConsole implements Client {


    /**
     * Prompts the user with information via the console
     * @param o The Object to be prompted
     */
    public void prompt(Object o) {
        System.out.println(o);
    }

    /**
     * Prints the Object to the console
     * @param o The Object to be prompted
     */
    private static void promptS(Object o) {
        System.out.println(o);
    }

    /**
     * Prompts the user for the next action, then adds it to the Action Queue.
     * This includes View, Build, Command, Pass, Inspect, and Check.
     * Invalid inputs are accounted for.
     * @param acs the Action Queue
     * @param console the Scanner to read inputs
     */
    private static void addAction(Queue<Action> acs, Scanner console) {
        promptS("What's Next? (View, Build, Command, Pass, Inspect, Check)");
        String next = console.nextLine().toLowerCase();
        switch (next) {
            case "build":
                promptS("What to Build?");
                try {
                    String lower = (console.nextLine()).toLowerCase();
                    String firstUpper = "everdale." + lower.substring(0, 1).toUpperCase() + lower.substring(1);
                    Class<?> b = Class.forName(firstUpper);
                    if (!b.getSuperclass().equals(Building.class)) {
                        throw new ClassNotFoundException("Not Building.");
                    }

                    promptS("Select X");
                    int x = Integer.parseInt(console.nextLine());
                    promptS("Select Y");
                    int y = Integer.parseInt(console.nextLine());

                    acs.add(new Build((Building)b.getDeclaredConstructor().newInstance(), x, y));

                } catch (NumberFormatException e) {
                    promptS("Invalid Number.");
                } catch (Exception e) {
                    promptS("Invalid Building.");
                }
                break;
            case "view":
                acs.add(Action.VIEW);
                break;
            case "pass":
                promptS("How many times?");
                try {
                    int times = Integer.parseInt(console.nextLine());
                    for (int i = 0; i < times; i++) {
                        acs.add(new Pass());
                    }
                } catch (Exception e) {
                    promptS("Invalid Number.");
                }
                break;
            case "command":
                promptS("Who?");
                for (Resident r : Game.home.getResidents()) {
                    promptS(r.getName());
                }
                Resident who = Game.home.getResident(console.nextLine());
                if (who == null) {
                    promptS("Invalid name.");
                    break;
                }

                int x;
                int y;

                try {
                    promptS("Select X");
                    x = Integer.parseInt(console.nextLine());
                    promptS("Select Y");
                    y = Integer.parseInt(console.nextLine());
                } catch (NumberFormatException e) {
                    promptS("Invalid Number.");
                    break;
                }
                Coordinate where = Game.home.getCoord(x, y);
                promptS("Sending " + who.getName() + " to " + where + " (" + Game.home.buildingAt(where) + ")");
                acs.add(new Command(who, where));
                break;
            case "inspect":
                try {
                    promptS("Select X");
                    x = Integer.parseInt(console.nextLine());
                    promptS("Select Y");
                    y = Integer.parseInt(console.nextLine());

                    acs.add(new Inspect(x, y));
                } catch (Exception e) {
                    promptS("Invalid Number.");
                }
                break;
            case "check":
                promptS("Who?");
                for (Resident r : Game.home.getResidents()) {
                    promptS(r.getName());
                }
                who = Game.home.getResident(console.nextLine());
                if (who == null) {
                    promptS("Invalid name.");
                    break;
                }
                acs.add(new Check(who));
                break;
            default:
                promptS("Failed to add: " + next);
                break;
        }
    }

    public static void main(String args[]) {
        EverdaleConsole e = new EverdaleConsole();
        Game.setClient(e);
        Queue<Action> actions = new LinkedList<>();
        Scanner console = new Scanner(System.in);
        while (true) {
            addAction(actions, console);
            Game.play(actions);
        }
    }
}
