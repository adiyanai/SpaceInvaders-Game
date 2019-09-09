package animation;
import biuoop.DrawSurface;
/**
 * A template-methods of animation.
 * @author Adi Yanai
 */
public interface Animation {
   /**
    * draw all the objects.
    * @param d - the draw surface
    * @param dt - frames per second
    */
   void doOneFrame(DrawSurface d, double dt);
   /**
    * change a boolean variable when we want to stop the animation.
    * @return true or false
    */
   boolean shouldStop();
}