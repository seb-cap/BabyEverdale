package client.graphical2d;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * The SpriteSheet class is a helper class with methods to get Sprites from a SpriteSheet.
 * It also contains constants holding important Sprites.
 */
public class SpriteSheet {

    // Villager
    public static final String VILLAGER_SPRITE_SHEET = "client/graphical2d/images/characters.png";
    // Consumables
    public static final BufferedImage SMALL_TREE_SPRITE = getSprite("client/graphical2d/images/small_tree.png");
    public static final BufferedImage SMALL_STONE_SPRITE = getSprite("client/graphical2d/images/small_stone.png");
    public static final BufferedImage SMALL_CLAY_SPRITE = getSprite("client/graphical2d/images/small_clay.png");
    // Producers
    public static final BufferedImage[] EVERGROVE_SPRITES = getSprites("client/graphical2d/images/evergrove.png", 2, 1);
    public static final BufferedImage[] STONE_MINE_SPRITES = getSprites("client/graphical2d/images/stone_mine.png", 2, 1);
    public static final BufferedImage[] CLAY_PIT_SPRITES = getSprites("client/graphical2d/images/clay_pit.png", 2, 1);
    // Storages TODO add the rest
    public static final BufferedImage[] WOOD_STORAGE_SPRITES = getSprites("client/graphical2d/images/wood_storage.png", 12, 3);
    public static final BufferedImage[] KITCHEN_SPRITES = getSprites("client/graphical2d/images/kitchen.png", 12, 1);
    // Carts
    public static final BufferedImage[] BUILD_CART_SPRITES = getSprites("client/graphical2d/images/build_cart.png", 1, 2);
    public static final BufferedImage[] SHIP_CART_SPRITES = getSprites("client/graphical2d/images/ship_cart.png", 1, 2);
    public static final BufferedImage[] OTTO_CART_SPRITES = getSprites("client/graphical2d/images/otto_cart.png", 1, 2);
    public static final BufferedImage[] EVENT_CART_SPRITES = getSprites("client/graphical2d/images/event_cart.png", 1, 2);
    // Other
    public static final BufferedImage[] HOME_SPRITES = getSprites("client/graphical2d/images/home.png", 10, 1);
    public static final BufferedImage GATE_SPRITE = getSprite("client/graphical2d/images/gate.png");
    public static final BufferedImage[] STUDY_SPRITES = getSprites("client/graphical2d/images/study.png", 16, 1);
    public static final BufferedImage PATCH_SPRITE = getSprite("client/graphical2d/images/patch.png");


    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;


    /**
     * Returns the BufferedImage Sprite when the entire file is the sprite
     * @param fileName The name of the File containing the entire Sprite
     * @return The BufferedImage containing the full Sprite
     */
    public static BufferedImage getSprite(String fileName) {
        try {
            return ImageIO.read(new File(fileName));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets an Array of BufferedImage Sprites from a uniform SpriteSheet
     * @param fileName The String name of the SpriteSheet file
     * @param startX The x value to start searching from
     * @param startY The y value to start searching from
     * @param rows The number of rows to extract
     * @param cols The number of columns to extract
     * @param width The width of each Sprite
     * @param height The height of each Sprite
     * @return The Array of BufferedImages containing all the specified Sprites.
     */
    public static BufferedImage[] getSprites(String fileName, int startX, int startY, int rows, int cols, int width, int height) {
        try {
            BufferedImage allSprites = ImageIO.read(new File(fileName));
            BufferedImage[] images = new BufferedImage[rows * cols];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    BufferedImage sprite = allSprites.getSubimage(
                            startX + col * width,
                            startY + row * height,
                            width,
                            height);

                    images[col + row * cols] = removeBackground(sprite, new Color(246, 139, 205));
                }
            }
            return images;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets an Array of BufferedImage Sprites from a uniform SpriteSprite sheet with
     * each Sprite 10x10 pixels and starting from the top left.
     * @param fileName The String name of the SpriteSheet file
     * @param rows The number of rows to extract
     * @param cols The number of columns to extract
     * @return The Array of BufferedImages containing all the Sprites
     */
    public static BufferedImage[] getSprites(String fileName, int rows, int cols) {
        return getSprites(fileName, 0, 0, rows, cols, 10, 10);
    }

    /**
     * Creates a new BufferedImage with the background of an Image removed by replacing all
     * pixels of that Color with transparent pixels.
     * @param image The Image to manipulate
     * @param c The background Color to be replaced
     * @return A new BufferedImage identical to the original, but with the background removed.
     */
    private static BufferedImage removeBackground(BufferedImage image, Color c) {
        BufferedImage transparent = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int cur = image.getRGB(x, y);
                if (cur == c.getRGB()) {
                    transparent.setRGB(x, y, new Color(0, 0,0, 0).getRGB());
                }
                else {
                    transparent.setRGB(x, y, cur);
                }
            }
        }
        return transparent;
    }
}
