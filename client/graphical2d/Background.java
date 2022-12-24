package client.graphical2d;

import everdale.*;
import everdale.Action;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;


public class Background extends JPanel implements MouseListener, ActionListener {


    private List<Villager> villagers;
    private Set<Building> buildings;
    private Villager selectedVillager = null;


    public Background() {
        this.initBackground();
    }

    private void initBackground() {
        this.addMouseListener(this);
        this.setBackground(Color.green);
        this.setFocusable(true);

        this.buildings = Game.home.buildings().keySet();

        this.villagers = new ArrayList<>();
        this.villagers.add(new Villager(Game.home.getResidents().iterator().next()));

        final int DELAY = 100;
        Timer timer = new Timer(DELAY, this);
        timer.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing((Graphics2D)g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics2D g) {
        for (Building b : buildings) {
            if (b instanceof Empty) continue;

            if (b instanceof SmallTree) {
                drawImage(g, SpriteSheet.SMALL_TREE_SPRITE, b);
                continue;
            }
            if (b instanceof SmallStone) {
                drawImage(g, SpriteSheet.SMALL_STONE_SPRITE, b);
                continue;
            }
            if (b instanceof SmallClay) {
                drawImage(g, SpriteSheet.SMALL_CLAY_SPRITE, b);
                continue;
            }
            if (b instanceof Evergrove) {
                drawImage(g, SpriteSheet.EVERGROVE_SPRITE, b);
                continue;
            }
            if (b instanceof WoodStorage) {
                drawImage(g, SpriteSheet.WOOD_STORAGE_SPRITES[storageSpriteFinder((WoodStorage)b)], b);
                continue;
            }
            if (b instanceof BuildCart) {
                drawImage(g, SpriteSheet.BUILD_CART_SPRITES[cartSpriteFinder((BuildCart)b)], b);
                continue;
            }
        }
        for (Villager v : villagers) {
            g.drawImage(v.getImage(), v.getX(), v.getY(), this);
        }
    }

    private void drawImage(Graphics2D g, BufferedImage i, Building b) {
        for (int y = 0; y < b.getSizeY() * 10; y+= 10){
            for(int x=0;x<b.getSizeX()*10;x+=10){
                g.drawImage(i, b.getX() * 10 + x, b.getY() * 10 + y, this);
            }
        }
    }

    private int storageSpriteFinder(Storage s) {
        return s.storageIsEmpty() ? 0 : s.isFull() ? 2 : 1;
    }

    private int cartSpriteFinder(Cart c) {
        return c.progressIsEmpty() ? 0 : 1;
    }


    // Mouse Listener Methods
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX() / 10;
        int y = e.getY() / 10;
        System.out.println(x + ", " + y);
        if (this.selectedVillager != null) Graphical2dClient.actions.add(new Command(this.selectedVillager.asResident(), new Coordinate(x, y)));

        for (Villager v : this.villagers) {
            this.selectedVillager = null;
            v.deselect();
            if (v.getX() / 10 == x && v.getY() / 10 == y) {
                this.selectedVillager = v;
                v.select();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // Will Run once every DELAY ms
    @Override
    public void actionPerformed(ActionEvent e) {
        for (Villager v : villagers) {
            v.updateLocation();
        }
        Graphical2dClient.actions.add(Action.PASS);
        Game.play(Graphical2dClient.actions);
        repaint();
    }
}
