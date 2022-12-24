package client.graphical2d;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class SpriteSheet {

    public static final String VILLAGER_SPRITE_SHEET = "client/graphical2d/images/characters.png";
    public static final BufferedImage SMALL_TREE_SPRITE = getSprite("client/graphical2d/images/small_tree.png");
    public static final BufferedImage EVERGROVE_SPRITE = getSprite("client/graphical2d/images/evergrove.png");
    public static final BufferedImage[] WOOD_STORAGE_SPRITES = getSprites("client/graphical2d/images/wood_storage.png", 12, 3);
    public static final BufferedImage SMALL_STONE_SPRITE = getSprite("client/graphical2d/images/small_stone.png");
    public static final BufferedImage SMALL_CLAY_SPRITE = getSprite("client/graphical2d/images/small_clay.png");
    public static final BufferedImage[] BUILD_CART_SPRITES = getSprites("client/graphical2d/images/build_cart.png", 1, 2);


    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;


    public static BufferedImage getSprite(String fileName) {
        try {
            return ImageIO.read(new File(fileName));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public static BufferedImage[] getSprites(String fileName, int rows, int cols) {
        return getSprites(fileName, 0, 0, rows, cols, 10, 10);
    }

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
