package client.graphical2d;

import everdale.*;


import java.awt.image.BufferedImage;

public class Villager {

    private final int ANIMATION_QUANTITY = 3;

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

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void select() {
        this.representing.halt();
    }

    public void deselect() {
        this.representing.unhalt();
    }

    public Resident asResident() {
        return this.representing;
    }

    public void updateLocation() {
        int oldX = this.x;
        int oldY = this.y;
        int oldDirection = this.direction;
        this.x = representing.getLocation().getX();
        this.y = representing.getLocation().getY();
        this.direction = getDirection(this.x, this.y, oldX, oldY);

        if (!this.representing.isIdle()) {
            animationState++;
            if (animationState % (ANIMATION_QUANTITY) == 0 || this.direction != oldDirection) {
                animationState = 3 * direction;
            }
        }
        else {
            animationState = 3 * direction + 1;
        }
    }

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

    public BufferedImage getImage() {
        return this.images[this.animationState];
    }

}
