package graphic;

import java.awt.Color;
import java.awt.Image;
import java.util.Map;
import geometry.Point;
import geometry.Rectangle;
/**
 * set the blocks.
 * @author Adi Yanai
 *
 */
public class SetBlocks implements BlockCreator {
    private int width;
    private int height;
    private int hitPoints;
    private Map<String, Color> fillC;
    private Map<String, Image> fillI;
    private Color stroke;
    /**
     * constructor.
     * @param width - the block width
     * @param height - the block height
     * @param hitPoints - the number of hits of the block
     * @param fillC - a map of the block colors
     * @param fillI - a map of the block images
     * @param stroke - the block stroke color
     */
    public SetBlocks(int width, int height, int hitPoints, Map<String, Color> fillC, Map<String, Image> fillI,
            Color stroke) {
        this.width = width;
        this.height = height;
        this.hitPoints = hitPoints;
        this.fillC = fillC;
        this.fillI = fillI;
        this.stroke = stroke;
    }

    /**
     * get the width of the block.
     * @return the width of the block
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Create a block at the specified location.
     * @param xpos - the x of the upper left of the block
     * @param ypos - the y of the upper left of the block
     * @return a block
     */
    public Block create(int xpos, int ypos) {
        Point upperLeft = new Point(xpos, ypos);
        Rectangle r = new Rectangle(upperLeft, this.width, this.height, this.fillC, this.fillI, this.stroke);
        Block b = new Block(r, this.hitPoints, true);
        return b;
    }
}