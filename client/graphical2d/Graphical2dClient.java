package client.graphical2d;

import everdale.*;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.util.LinkedList;
import java.util.Queue;

public class Graphical2dClient extends JFrame implements Client {

    protected static Queue<everdale.Action> actions;

    public Graphical2dClient() {
        actions = new LinkedList<>();
        this.initUI();
    }

    private void initUI() {

        Background background = new Background();

        this.setPreferredSize(new Dimension(1050, 1050));
        this.setContentPane(background);
        pack();

        this.setTitle("Everdale");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

    }


    public void prompt(Object o) {
        //System.out.println(o); // TODO
    }



    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Graphical2dClient ex = new Graphical2dClient();
            Game.setClient(ex);
            ex.setVisible(true);
        });
    }

}
