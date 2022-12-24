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
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Background extends JPanel implements MouseListener, ActionListener {


    private List<Villager> villagers;
    private Set<Building> buildings;
    private Villager selectedVillager = null;
    private int currentViewX;
    private int currentViewY;


    public Background() {
        this.initBackground();
    }

    private void initBackground() {
        this.addMouseListener(this);
        this.setBackground(Color.darkGray);
        this.setFocusable(true);
        this.setSize(1050, 1050);

        this.currentViewX = 0;
        this.currentViewY = 0;

        this.buildings = Game.home.buildings().keySet();

        this.villagers = new ArrayList<>();
        this.villagers.add(new Villager(Game.home.getResidents().iterator().next()));

        this.addKeyListener(new Keys(this));

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
            // If out of view, don't draw
            if (b.getX() < currentViewX / 10 || b.getX() > currentViewX / 10 + this.getWidth() / 10 ||
                b.getY() < currentViewY / 10 || b.getY() > currentViewY / 10 + this.getHeight()) continue;

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
            drawImage(g, v.getImage(), v.getX(), v.getY(), 1, 1);
        }
    }

    private void drawImage(Graphics2D g, BufferedImage i, Building b) {
        drawImage(g, i, b.getX(), b.getY(), b.getSizeX(), b.getSizeY());
    }

    private void drawImage(Graphics2D g, BufferedImage i, int locX, int locY, int sizeX, int sizeY) {
        for (int y = 0; y < sizeY * 10; y+= 10) {
            for(int x = 0; x < sizeX * 10; x+=10) {
                g.drawImage(i, locX * 10 + x - this.currentViewX, locY * 10 + y - this.currentViewY, this);
            }
        }
    }

    private int storageSpriteFinder(Storage s) {
        return s.storageIsEmpty() ? 0 : s.isFull() ? 2 : 1;
    }

    private int cartSpriteFinder(Cart c) {
        return c.progressIsEmpty() ? 0 : 1;
    }

    private boolean clicked(int clickedX, int clickedY, int absoluteX, int absoluteY) {
        return absoluteX - this.currentViewX / 10 == clickedX && absoluteY - this.currentViewY / 10 == clickedY;

    }

    // Mouse Listener Methods
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX() / 10;
        int y = e.getY() / 10;
        if (x + currentViewX / 10 > Village.X_SIZE - 1 || y + currentViewY / 10 > Village.Y_SIZE - 1) return;
        if (this.selectedVillager != null) {
            Graphical2dClient.actions.add(
                    new Command(this.selectedVillager.asResident(),
                            new Coordinate(x + currentViewX / 10, y + currentViewY / 10)
                    )
            );
        }

        for (Villager v : this.villagers) {
            this.selectedVillager = null;
            v.deselect();
            if (clicked(x, y, v.getX(), v.getY())) {
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

    public void moveRight() {
        if (this.currentViewX / 10 < Village.X_SIZE - 1) this.currentViewX += 10;
    }

    public void moveLeft() {
        if (this.currentViewX > 0) this.currentViewX -= 10;
    }

    public void moveDown() {
        if (this.currentViewY / 10 < Village.Y_SIZE - 1) this.currentViewY += 10;
    }

    public void moveUp() {
        if (this.currentViewY > 0) this.currentViewY -= 10;
    }


    private static class Keys extends KeyAdapter {

        private final Background b;

        public Keys(Background b) {
            this.b = b;
        }
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT -> b.moveRight();
                case KeyEvent.VK_LEFT -> b.moveLeft();
                case KeyEvent.VK_DOWN -> b.moveDown();
                case KeyEvent.VK_UP -> b.moveUp();
            }
        }
    }
}
