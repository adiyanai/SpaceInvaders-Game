package levels;

import java.awt.Color;
import java.awt.Image;
import biuoop.DrawSurface;
import game.Sprite;

/**
 * Background class.
 * @author Adi Yanai
 *
 */
public class Background implements Sprite {
    private Color color = null;
    private Image image = null;

    /**
     * Constructor.
     * @param color - a color
     */
    public Background(Color color) {
        this.color = color;
    }
    /**
     * Constructor.
     * @param image - an image
     */
    public Background(Image image) {
        this.image = image;
    }

    /**
     * draw the background on the draw surface.
     * @param d - a drawsurface
     */
    public void drawOn(DrawSurface d) {
        // if the background is a color
        if (color != null) {
            d.setColor(color);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        }
        // if the background is an image
        if (image != null) {
            d.drawImage(10, 20, image);
        }
    }

    /**
     * nothing.
     * @param dt - frames per second
     */
    public void timePassed(double dt) {
    }
}
