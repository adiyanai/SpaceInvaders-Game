package graphic;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import biuoop.DrawSurface;
import game.Collidable;
import game.GameLevel;
import game.HitListener;
import game.HitNotifier;
import game.Sprite;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
/**
 * This class present a block who composed by rectangle and number who presents number of hits.
 * @author Adi Yanai
 *
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle block;
    private int numOfHits;
    private boolean check;
    private java.lang.String stringHits;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();

    /**
     * constructor of the block.
     * @param block - the "collision shape" of the object
     * @param numOfHits - the number of the hits that left to the block
     * @param check - a check of boolean type
     */
    public Block(Rectangle block, int numOfHits, boolean check) {
        this.block = block;
        this.numOfHits = numOfHits;
        this.check = check;
    }

    /**
     * return the check, that represent if the block type is a remove one.
     * @return the check
     */
    public boolean getCheckBlock() {
        return this.check;
    }

    /**
     * Return the "collision shape" of the object.
     * @return the block
     */
    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    /**
     * return the number of the hits that left to the block.
     * @return numOfHits - the number of the hits that left to the block
     */
    public int getNumOfHits() {
        return this.numOfHits;
    }

    /**
     * set the stringHits.
     */
    public void setStringHits() {
        if (this.getNumOfHits() == 0) {
            this.stringHits = "X";
        } else {
            this.stringHits = String.valueOf(this.getNumOfHits());
        }
    }

    /**
     * return the stringHits.
     * @return the stringHits
     */
    public java.lang.String getStringHits() {
        return this.stringHits;
    }

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     * @param collisionPoint - a collision point
     * @param currentVelocity - the current velocity of the ball
     * @param hitter - the ball that hit the block
     * @return - the new velocity expected after the hit
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double xStart, yStart, xEnd, yEnd, dx, dy;
        Point upperLeftP, upperRightP, lowerLeftP, lowerRightP;
        Line upperL, lowerL, rightL, leftL;

        // create the minimal and maximal x & y of the block
        xStart = this.block.getUpperLeft().getX();
        xEnd = xStart + this.block.getWidth();
        yStart = this.block.getUpperLeft().getY();
        yEnd = yStart + this.block.getHeight();
        // create the 4 vertices points of the block
        upperLeftP = new Point(xStart, yStart);
        upperRightP = new Point(xEnd, yStart);
        lowerLeftP = new Point(xStart, yEnd);
        lowerRightP = new Point(xEnd, yEnd);
        // create the 4 sides of the block
        upperL = new Line(upperLeftP, upperRightP);
        lowerL = new Line(lowerLeftP, lowerRightP);
        rightL = new Line(upperRightP, lowerRightP);
        leftL = new Line(upperLeftP, lowerLeftP);
        // the current dx and dy of the velocity
        dx = currentVelocity.getDx();
        dy = currentVelocity.getDy();

        // reduce the number of the hits of the block by 1 (if numOfHits bigger than 1)
        if (this.numOfHits > 0) {
            this.numOfHits = this.numOfHits - 1;
            this.setStringHits();
        }

        // check if the collision point instructed with the upper or the lower line of the block, and change the dy
        if (upperL.belongToTheLine(collisionPoint) || lowerL.belongToTheLine(collisionPoint)) {
            dy = -dy;
        }
        // check if the collision point instructed with the right or the left line of the block, and change the dx
        if (rightL.belongToTheLine(collisionPoint) || leftL.belongToTheLine(collisionPoint)) {
            dx = -dx;
        }

        this.notifyHit(hitter);
        // return the new velocity
        return new Velocity(dx, dy);
    }

    /**
     * draw rectangle on the draw surface.
     * @param d - the draw surface
     */
    public void drawOn(DrawSurface d) {
        boolean draw = false;
        Image image = null;
        int x, y, w, h;
        // the upper left point of the rectangle
        x = (int) this.block.getUpperLeft().getX();
        y = (int) this.block.getUpperLeft().getY();
        // the width of the rectangle
        w = (int) this.block.getWidth();
        // the height of the rectangle
        h = (int) this.block.getHeight();

        // set the block color according to the colors map
        if (!this.block.getColors().isEmpty()) {
            for (Map.Entry<String, Color> entry : this.block.getColors().entrySet()) {
                if (entry.getKey().contains(Integer.toString(this.numOfHits))) {
                    d.setColor(entry.getValue());
                    // draw the rectangle
                    d.fillRectangle(x, y, w, h);
                    draw = true;
                    break;
                }
            }
        }

        // set the block image according to the images map
        if (!this.block.getImages().isEmpty()) {
            for (Map.Entry<String, Image> entry : this.block.getImages().entrySet()) {
                if (entry.getKey().contains(Integer.toString(this.numOfHits))) {
                    image = entry.getValue();
                    d.drawImage(x, y, image);
                    draw = true;
                    break;
                }
            }
        }

        // if in the fill maps there is no fill that fits to the number of hits, take the regular fill
        if (!draw) {
            if (this.block.getColors().containsKey("fill")) {
                d.setColor(this.block.getColors().get("fill"));
                // draw the rectangle
                d.fillRectangle(x, y, w, h);
            } else {
                image = this.block.getImages().get("fill");
                d.drawImage(x, y, image);
            }
        }

        // set the stroke of the block (if exist)
        if (this.block.getStroke() != null) {
             d.setColor(this.block.getStroke());
             d.drawRectangle(x, y, w, h);
        }
    }

    /**
     * nothing.
     * @param dt - frames per second
     */
    public void timePassed(double dt) {
    }

    /**
     * add the block to the game.
     * @param g - the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Remove the block from the game.
     * @param game - the game
     */
    public void removeFromGame(GameLevel game) {
        // remove this block from the GameEnvironment
        game.removeCollidable(this);
        // remove this block from the SpriteCollection
        game.removeSprite(this);
    }

    /**
     * Add hl as a listener to hit events.
     * @param hl - a HitListener
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of listeners to hit events.
     * @param hl - a HitListener
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * notifiers all of the registered HitListener objects by calling their hitEvent method.
     * @param hitter - a ball that hit an object
     */
    private void notifyHit(Ball hitter) {
         // Make a copy of the hitListeners bsefore iterating over them.
         List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
         // Notify all listeners about a hit event:
         for (HitListener hl : listeners) {
             hl.hitEvent(this, hitter);
         }
    }

    /**
     * return a list of hit listeners.
     * @return a list of hit listeners
     */
    public List<HitListener> getHitListeners() {
        return this.hitListeners;
    }
}