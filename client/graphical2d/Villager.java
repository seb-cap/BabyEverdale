package client.graphical2d;

import everdale.*;

import java.awt.image.BufferedImage;

/**
 * The Villager class represents an Everdale Resident in visual form. It handles animation
 * and logic for moving based on the Residents in Everdale.
 */
public class Villager {

    /**
     * The number of steps in the Animation
     */
    private final int ANIMATION_QUANTITY = 3;

    // Info for the SpriteSheet
    private final int rows = 4;
    private final int cols = 3;
    private final int width = 16;
    private final int height = 18;
    private final int startX = 256;
    private final int startY = 306;

    private final BufferedImage[] images;

    private int x;
    private int y;
    private Resident representing;
    private int animationState;
    private int direction;
    private int diagonalCounter;

    /**
     * Creates a new Villager representing the given Resident
     * @param r The Resident to represent
     */
    public Villager(Resident r) {
        this.representing = r;
        this.x = r.getLocation().getX();
        this.y = r.getLocation().getY();
        this.images = SpriteSheet.getSprites(
                SpriteSheet.VILLAGER_SPRITE_SHEET, startX, startY, rows, cols, width, height);
        this.animationState = 0;
        this.direction = SpriteSheet.RIGHT;
        this.diagonalCounter = 0;
    }

    /**
     * @return The X value of this Villager
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return The Y value of this Villager
     */
    public int getY() {
        return this.y;
    }

    /**
     * Selects the Villager. The Resident will stop and wait for a Command.
     */
    public void select() {
        this.representing.halt();
    }

    /**
     * Deselects the Villager.
     */
    public void deselect() {
        this.representing.unhalt();
    }

    /**
     * @return The Resident this Villager is representing
     */
    public Resident asResident() {
        return this.representing;
    }

    /**
     * Updates the location of the Villager and animates it accordingly.
     */
    public void updateLocation() {
        int oldX = this.x;
        int oldY = this.y;
        int oldDirection = this.direction;
        this.x = representing.getLocation().getX();
        this.y = representing.getLocation().getY();
        this.direction = getDirection(this.x, this.y, oldX, oldY);

        if (this.representing.isWorking()) {
            animationState++;
            if (animationState % (ANIMATION_QUANTITY) == 0 || this.direction != oldDirection) {
                animationState = 3 * direction;
            }
        }
        else {
            animationState = 3 * direction + 1;
        }
    }

    /**
     * Gets the Direction of the Villager based on changes in its location.
     * @param x The current X
     * @param y The current Y
     * @param oldX The old X
     * @param oldY The old Y
     * @return The direction the Villager is going, based on SpriteSheet.RIGHT/LEFT/DOWN/UP
     */
    private int getDirection(int x, int y, int oldX, int oldY) {
        if (x > oldX) {
            return findYDirection(y, oldY, SpriteSheet.RIGHT);
        }
        if (x < oldX) {
            return findYDirection(y, oldY, SpriteSheet.LEFT);
        }
        if (y > oldY) {
            return findXDirection(x, oldX, SpriteSheet.DOWN);
        }
        if (y < oldY) {
            return findXDirection(x, oldX, SpriteSheet.UP);
        }
        return this.direction;
    }

    /**
     * Finds the Y direction. If moving diagonally, it will alternate directions
     * @param y The current Y coordinate
     * @param oldY The old Y coordinate
     * @param alternateDirection The alternate direction to go if diagonally.
     * @return The direction, either alternateDirection, SpriteSheet.DOWN or SpriteSheet.UP.
     */
    private int findYDirection(int y, int oldY, int alternateDirection) {
        if (y != oldY) {
            diagonalCounter++;
            if (diagonalCounter % 2 == 0) {
                diagonalCounter = 0;
                return (y > oldY) ? SpriteSheet.DOWN : SpriteSheet.UP;
            }
        }
        return alternateDirection;
    }

    /**
     * Finds the X direction. If moving diagonally, it will alternate directions
     * @param x The current X coordinate
     * @param oldX The old X coordinate
     * @param alternateDirection The alternate direction to go if diagonally.
     * @return The direction, either alternateDirection, SpriteSheet.LEFT or SpriteSheet.RIGHT.
     */
    private int findXDirection(int x, int oldX, int alternateDirection) {
        if (x != oldX) {
            diagonalCounter++;
            if (diagonalCounter % 2 == 0) {
                diagonalCounter = 0;
                return (x < oldX) ? SpriteSheet.LEFT : SpriteSheet.RIGHT;
            }
        }
        return alternateDirection;
    }

    /**
     * Gets the BufferedImage sprite corresponding to the current animation state of the Villager.
     * @return The BufferedImage Sprite
     */
    public BufferedImage getImage() {
        return this.images[this.animationState];
    }

}
