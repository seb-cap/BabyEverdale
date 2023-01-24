package client.graphical2d;

import everdale.*;
import everdale.Action;
import everdale.Client.Type;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
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
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;


/**
 * The Background class is a JPanel that represents the background
 * of the Everdale game. This holds the information for moving the view
 * and logic for drawing sprites.
 * <br>
 * Over time, this class has evolved to be the main logic of the graphical2d package.
 * The Background class handles all mouse presses and keystrokes.
 */
public class Background extends JPanel implements MouseListener, ActionListener {


    private List<Villager> villagers;
    private Set<Building> buildings;
    private Villager selectedVillager = null;
    private int currentViewX;
    private int currentViewY;
    private Class<? extends Building> toBuild;
    private int toBuildLevel;
    private boolean buildingBuilt = false;


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

        this.updateVillagers();

        this.addKeyListener(new Keys(this));

        final int DELAY = 100;
        Timer timer = new Timer(DELAY, this);
        timer.start();

    }

    private void updateVillagers() {
        if (Game.home.getResidents().size() == this.villagers.size()) return;

        this.villagers.clear();
        for (Resident r : Game.home.getResidents()) {
            this.villagers.add(new Villager(r));
        }
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
                drawImage(g, SpriteSheet.KITCHEN_SPRITES[b.getLevel()-1], b);
                continue;
            }
            if (b instanceof Home) {
                drawImage(g, SpriteSheet.HOME_SPRITES[b.getLevel()-1], b);
                continue;
            }
            if (b instanceof Gate) {
                drawImage(g, SpriteSheet.GATE_SPRITE, b);
            }
            if (b instanceof Study) {
                drawImage(g, SpriteSheet.STUDY_SPRITES[b.getLevel()-1], b);
            }
            if (b instanceof Patch) {
                drawImage(g, SpriteSheet.PATCH_SPRITE, b);
            }
        }
        for (Villager v : villagers) {
            drawImage(g, v.getImage(), v.getX(), v.getY(), 1, 1);
        }

        // draw text
        List<Text> text = Graphical2dClient.getText();
        int drawn = 0;
        for (int i = 0; i < text.size(); i++) {
            g.setColor(text.get(i).findColor());
            if (text.get(i).toString() == null) {
                text.remove(i);
                i--;
                continue;
            }
                g.drawString(text.get(i).toString(), 0, 10 * drawn + 10);
                drawn++;
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
     * Determines whether a coordinate has been clicked within a radius of 2 units. Specifically made
     * to click Villagers
     * @param clickedX The x coordinate clicked.
     * @param clickedY The y coordinate clicked.
     * @param absoluteX The true x coordinate being checked.
     * @param absoluteY The true y coordinate being checked.
     * @return True if the coordinates align, false otherwise.
     */
    private boolean villagerClicked(int clickedX, int clickedY, int absoluteX, int absoluteY) {
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (clicked(clickedX + i, clickedY + j, absoluteX, absoluteY)) return true;
            }
        }
        return false;
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

            // Build a building
            if (this.toBuild != null) {
                try {
                    // If it's a new building, build.
                    if (this.toBuildLevel == 1) {
                        Graphical2dClient.actions.add(
                                new Build(
                                        this.toBuild.getDeclaredConstructor(Coordinate.class).newInstance(
                                                new Coordinate(x, y)
                                        )
                                )
                        );
                        buildingBuilt = true;
                    }
                    // Otherwise, level up the clicked place
                    else {
                        Building here = Game.home.buildingAt(adjustedX(x), adjustedY(y));
                        if (this.toBuild.isInstance(here)) {
                            if (this.toBuildLevel == here.getLevel() + 1) here.levelUp(); // TODO send villager to construct
                            else Graphical2dClient.ex.prompt("Invalid Upgrade Level!", Type.Notice);
                        }
                        else {
                            Graphical2dClient.ex.prompt("Invalid Upgrade Location!", Type.Notice);
                        }
                    }
                    this.toBuild = null;
                    this.toBuildLevel = 0;
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                return;
            }

            // Otherwise, Command the Villager
            if (this.selectedVillager != null) {
                Graphical2dClient.actions.add(
                        new Command(this.selectedVillager.asResident(),
                                new Coordinate(adjustedX(x), adjustedY(y))
                        )
                );
            }

            // Select new Villager
            this.selectedVillager = null;
            for (Villager v : this.villagers) {
                v.deselect();
                if (villagerClicked(x, y, v.getX(), v.getY())) {
                    this.selectedVillager = v;
                    v.select();
                }
            }
        }
        else if (mouseButton == MouseEvent.BUTTON3) {
            Building here = Game.home.buildingAt(adjustedX(x), adjustedY(y));
            Graphical2dClient.ex.prompt(here);

            if (here instanceof Study) {
                Set<ResearchNode> available = Study.getAvailableResearches();
                Set<Research> progress = Study.getInProgressResearches();

                String[] researches; // String Representations
                List<ResearchNode> availableResearches = new ArrayList<>();

                if (available.size() != 0 || progress.size() != 0) {
                    researches = new String[available.size() + progress.size()];
                    int i = 0;
                    for (ResearchNode rn : available) {
                        researches[i] = rn.name;
                        availableResearches.add(rn);
                        i++;
                    }
                    for (Research r : progress) {
                        researches[i] = r.toString() + " (" + r.getProgress() + "/" + r.getResearch().scrollsNeeded + ")";
                        availableResearches.add(r.getResearch());
                        i++;
                    }
                }
                else {
                    researches = new String[]{"No Research Available!"};
                }

                String s = (String)JOptionPane.showInputDialog(
                        this,
                        "Select a Research",
                        "Study",
                        JOptionPane.PLAIN_MESSAGE,
                        new ImageIcon(SpriteSheet.STUDY_SPRITES[0]),
                        researches,
                        researches[0]);

                if (s != null && !s.equals("No Research Available!")) Study.selectResearch(availableResearches.get(Arrays.asList(researches).indexOf(s)));
            }
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
        if (buildingBuilt) {
            this.updateVillagers();
            buildingBuilt = false;
        }
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
                case KeyEvent.VK_B -> b.build();
            }
        }
    }

    /**
     * Handles the UI and logic for building after pressing the Build button.
     * Will prompt the user to select a building or upgrade from the available list.
     * This will update the toBuild and toBuildLevel fields for the next click.
     */
    private void build() {
        Map<Class<? extends Building>, Integer> buildables = Game.home.getBuildables();
        String[] buildablesString = new String[buildables.keySet().size()];
        List<Class<? extends Building>> buildablesSet = new ArrayList<>();

        if (buildablesString.length > 0) {
            int i = 0;
            for (Class<? extends Building> c : buildables.keySet()) {
                buildablesString[i] = c.getSimpleName() + " (Level " + buildables.get(c) + ")";
                buildablesSet.add(c);
                i++;
            }
        }
        else {
            buildablesString = new String[]{"Research to Unlock more Buildings!"};
        }

        String s = (String)JOptionPane.showInputDialog(
                this,
                "Select a Building",
                "Build",
                JOptionPane.PLAIN_MESSAGE,
                new ImageIcon(SpriteSheet.BUILD_CART_SPRITES[0]),
                buildablesString,
                buildablesString[0]);

        if (s == null || s.equals("Research to Unlock more Buildings!")) return;

        Class<? extends Building> b = buildablesSet.get(Arrays.asList(buildablesString).indexOf(s));

        this.toBuild = b;
        this.toBuildLevel = buildables.get(b);
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
