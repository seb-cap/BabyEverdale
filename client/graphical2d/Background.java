package client.graphical2d;

import everdale.*;
import everdale.Action;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Background extends JPanel implements MouseListener, ActionListener {


    private Timer timer;
    private List<Villager> villagers;
    private final int DELAY = 100;

    public Background() {
        this.initBackground();
    }

    private void initBackground() {
        this.addMouseListener(this);
        this.setBackground(Color.green);
        this.setFocusable(true);

        this.villagers = new ArrayList<>();
        this.villagers.add(new Villager(Game.home.getResidents().iterator().next()));

        timer = new Timer(DELAY, this);
        timer.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing((Graphics2D)g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics2D g) {
        for (Villager v : villagers) {
            g.drawImage(v.getImage(), v.getX(), v.getY(), this);
        }
    }


    // Mouse Listener Methods
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (Villager v : this.villagers) {
            if (v.getX() == e.getX() && v.getY() == e.getY()) {
                v.select();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Villager v : villagers) {
            v.updateLocation();
        }
        Graphical2dClient.actions.add(new Pass());
        Game.play(Graphical2dClient.actions);
        repaint();
    }
}
