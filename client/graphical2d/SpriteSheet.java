package client.graphical2d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;


public class SpriteSheet {

    public static int UP = 0;
    public static int RIGHT = 1;
    public static int DOWN = 2;
    public static int LEFT = 3;


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

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
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
