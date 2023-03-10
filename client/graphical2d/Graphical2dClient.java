package client.graphical2d;

import everdale.*;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

/**
 * The Graphical2dClient class is a Client for Everdale<br>
 * It draws on JFrames to visually display the state of Everdale.
 * Players can interact with the Client to send Actions.
 */
public class Graphical2dClient extends JFrame implements Client {

    protected static Queue<everdale.Action> actions;
    protected static Graphical2dClient ex;

    private static List<Text> text = new ArrayList<>();

    /**
     * Creates a new Graphical2dClient Object and initializes the UI.
     */
    public Graphical2dClient() {
        actions = new LinkedList<>();
        this.initUI();
    }

    /**
     * Initializes the UI, giving it a Background and specifying its settings.
     */
    private void initUI() {

        Background background = new Background();

        this.setPreferredSize(new Dimension(1050, 1050));
        this.setContentPane(background);
        pack();

        this.setTitle("Everdale");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

    }


    /**
     * Prints the given Object at the top left of the Screen
     * @param o The Object to be prompted
     * @param t The Type of the Text
     */
    public void prompt(Object o, Client.Type t) {
        text.add(new Text(o.toString(), t));
    }

    /**
     * @return The List of Text to display
     */
    protected static List<Text> getText() {
        return text;
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            ex = new Graphical2dClient();
            Game.setClient(ex);
            ex.setVisible(true);
        });
    }

}
