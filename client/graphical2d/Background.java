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


/**
 * The Background class is a JPanel that represents the background
 * of the Everdale game. This holds the information for moving the view
 * and logic for drawing sprites.
 */
public class Background extends JPanel implements MouseListener, ActionListener {


    private List<Villager> villagers;
    private Set<Building> buildings;
    private Villager selectedVillager = null;
    private int currentViewX;
    private int currentViewY;


    /**
     * Creates a new Background object and initializes it
     */
    public Background() {
        this.initBackground();
    }

    /**
     * Initializes the Background by setting up event listeners and
     * specifying settings such as color, size, etc. Also initializes
     * the buildings Set and the villagers Set. Begins the Game loop.
     */
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

    /**
     * Repaints the screen every update.
     * @param g the Graphics object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing((Graphics2D)g);

        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Draws each building and updates the villagers.
     * @param g the Graphics object to draw onto
     */
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
                drawImage(g, SpriteSheet.EVERGROVE_SPRITES[b.getLevel()], b);
                continue;
            }
            if (b instanceof StoneMine) {
                drawImage(g, SpriteSheet.STONE_MINE_SPRITES[b.getLevel()], b);
                continue;
            }
            if (b instanceof ClayPit) {
                drawImage(g, SpriteSheet.CLAY_PIT_SPRITES[b.getLevel()], b);
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
            if (b instanceof ShipCart) {
                drawImage(g, SpriteSheet.SHIP_CART_SPRITES[cartSpriteFinder((ShipCart)b)], b);
                continue;
            }
            if (b instanceof EventCart) {
                drawImage(g, SpriteSheet.EVENT_CART_SPRITES[cartSpriteFinder((EventCart)b)], b);
                continue;
            }
            if (b instanceof OttoCart) {
                drawImage(g, SpriteSheet.OTTO_CART_SPRITES[cartSpriteFinder((OttoCart)b)], b);
                continue;
            }
            if (b instanceof Kitchen) {
                drawImage(g, SpriteSheet.KITCHEN_SPRITES[b.getLevel()], b);
                continue;
            }
            if (b instanceof Home) {
                drawImage(g, SpriteSheet.HOME_SPRITES[b.getLevel()], b);
                continue;
            }
            if (b instanceof Gate) {
                drawImage(g, SpriteSheet.GATE_SPRITE, b);
            }
            if (b instanceof Study) {
                drawImage(g, SpriteSheet.STUDY_SPRITES[b.getLevel()], b);
            }
            if (b instanceof Patch) {
                drawImage(g, SpriteSheet.PATCH_SPRITE, b);
            }
        }
        for (Villager v : villagers) {
            drawImage(g, v.getImage(), v.getX(), v.getY(), 1, 1);
        }
    }

    /**
     * Draws an image of a given Building at its coordinates.
     * @param g The Graphics to draw onto
     * @param i The BufferedImage to draw
     * @param b The Building being drawn
     */
    private void drawImage(Graphics2D g, BufferedImage i, Building b) {
        drawImage(g, i, b.getStartVal(b.getX(), b.getSizeX()), b.getStartVal(b.getY(), b.getSizeY()),
                b.getSizeX(), b.getSizeY());
    }

    /**
     * Draws an image at a given location
     * @param g The Graphics to draw onto
     * @param i The BufferedImage to draw
     * @param locX The x location of the Object
     * @param locY The y location of the Object
     * @param sizeX The x size of the Object (width)
     * @param sizeY The y size of the Object (height)
     */
    private void drawImage(Graphics2D g, BufferedImage i, int locX, int locY, int sizeX, int sizeY) {
        for (int y = 0; y < sizeY * 10; y+= 10) {
            for(int x = 0; x < sizeX * 10; x+=10) {
                g.drawImage(i, locX * 10 + x - this.currentViewX, locY * 10 + y - this.currentViewY, this);
            }
        }
    }

    /**
     * Gets the correct sprite value for a given Storage's state.
     * @param s The Storage being drawn.
     * @return The int corresponding to the Storage's state:<br>
     * 0 for empty,<br>1 for partially full,<br>2 for full.
     */
    private int storageSpriteFinder(Storage s) {
        return s.storageIsEmpty() ? 0 : s.isFull() ? 2 : 1;
    }

    /**
     * Gets the correct sprite value for a given Cart's state.
     * @param c The Cart being drawn.
     * @return The int corresponding to the Cart's state:<br>
     * 0 for empty,<br>1 for filling.
     */
    private int cartSpriteFinder(Cart c) {
        return c.progressIsEmpty() ? 0 : 1;
    }

    /**
     * Checks whether a certain coordinate has been clicked based on its absolute coordinates
     * and the relative coordinate from the view changes.
     * @param clickedX The x coordinate clicked.
     * @param clickedY The y coordinate clicked.
     * @param absoluteX The true x coordinate being checked.
     * @param absoluteY The true y coordinate being checked.
     * @return True if the coordinates align, false otherwise.
     */
    private boolean clicked(int clickedX, int clickedY, int absoluteX, int absoluteY) {
        return absoluteX - this.currentViewX / 10 == clickedX && absoluteY - this.currentViewY / 10 == clickedY;

    }

    /**
     * When the Mouse is pressed, does an action:<br>
     * LMB - Selects Villagers and moves them<br>
     * RMB - Gets info about the clicked Building
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int mouseButton = e.getButton();
        int x = e.getX() / 10;
        int y = e.getY() / 10;
        if (mouseButton == MouseEvent.BUTTON1) {
            if (adjustedX(x) > Village.X_SIZE - 1 || adjustedY(y) > Village.Y_SIZE - 1) return;
            if (this.selectedVillager != null) {
                Graphical2dClient.actions.add(
                        new Command(this.selectedVillager.asResident(),
                                new Coordinate(adjustedX(x), adjustedY(y))
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
        else if (mouseButton == MouseEvent.BUTTON3) {
            Graphical2dClient.ex.prompt(Game.home.buildingAt(adjustedX(x), adjustedY(y)));
        }
    }

    /**
     * Gets the X value adjusted for view changes
     * @param x The base X value
     * @return The adjusted X value
     */
    private int adjustedX(int x) {
        return x + currentViewX / 10;
    }

    /**
     * Gets the Y value adjusted for view changes
     * @param y The base Y value
     * @return The adjusted Y value
     */
    private int adjustedY(int y) {
        return y + currentViewY / 10;
    }


    /**
     * This function runs once every DELAY ms.
     * It updates the locations of Villagers, plays the game, and redraws the Image.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        for (Villager v : villagers) {
            v.updateLocation();
        }

        Graphical2dClient.actions.add(Action.PASS);
        Game.play(Graphical2dClient.actions);
        repaint();
    }

    /**
     * Moves the current view to the right as long as it is not too far.
     */
    public void moveRight() {
        if (this.currentViewX / 10 < Village.X_SIZE - 1) this.currentViewX += 10;
    }

    /**
     * Moves the current view to the left as long as it is not zero.
     */
    public void moveLeft() {
        if (this.currentViewX > 0) this.currentViewX -= 10;
    }

    /**
     * Moves the current view down as long as it is not too far.
     */
    public void moveDown() {
        if (this.currentViewY / 10 < Village.Y_SIZE - 1) this.currentViewY += 10;
    }

    /**
     * Moves the current view up as long as it is not zero.
     */
    public void moveUp() {
        if (this.currentViewY > 0) this.currentViewY -= 10;
    }


    /**
     * The Keys class monitors key presses for the Background.
     */
    private static class Keys extends KeyAdapter {

        private final Background b;

        /**
         * Creates a new Keys object for the given Background
         * @param b The Background using the Keys Object
         */
        public Keys(Background b) {
            this.b = b;
        }

        /**
         * Moves the view when an arrow key is pressed.
         * @param e the event to be processed
         */
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> b.moveRight();
                case KeyEvent.VK_LEFT, KeyEvent.VK_A -> b.moveLeft();
                case KeyEvent.VK_DOWN, KeyEvent.VK_S -> b.moveDown();
                case KeyEvent.VK_UP, KeyEvent.VK_W -> b.moveUp();

            }
        }
    }

    // Unused Methods from MouseListener.
    public void mouseReleased(MouseEvent e) {

    }
    public void mouseEntered(MouseEvent e) {

    }
    public void mouseExited(MouseEvent e) {

    }
    public void mouseClicked(MouseEvent e) {

    }
}
