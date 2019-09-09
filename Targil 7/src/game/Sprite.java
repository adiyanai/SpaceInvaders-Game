package game;
import biuoop.DrawSurface;
/**
 * A game object that can be drawn to the screen.
 * @author Adi Yanai
 *
 */
public interface Sprite {
   /**
    * draw the sprite to the screen.
    * @param d - the draw surface
    */
   void drawOn(DrawSurface d);
   /**
    * notify the sprite that time has passed.
    * @param dt - frames per second
    */
   void timePassed(double dt);
}