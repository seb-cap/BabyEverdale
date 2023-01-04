package client.graphical2d;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import everdale.Client.Type;

/**
 * The Text class represents a String to be printed and then deleted a short
 * while after.
 */
public class Text implements ActionListener {

    /**
     * How long the Text should remain on display.
     */
    private static final int DURATION = 3000;

    private String text;
    private Type type;

    /**
     * Creates a new Text Object with the given text and starts its Timer.
     * @param text The text for the Text
     */
    public Text(String text, Type type) {
        this.text = text;
        this.type = type;
        Timer timer = new Timer(DURATION, this);
        timer.start();
    }

    public Color findColor() {
        Color c = switch (this.type) {
            case Information: yield Color.white;
            case Warning: yield Color.yellow;
            case Notice: yield Color.red;
            case Success: yield Color.green;
        };
        return c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.text = null;
    }

    /**
     * @return The String this Text is representing
     */
    @Override
    public String toString() {
        return this.text;
    }
}
