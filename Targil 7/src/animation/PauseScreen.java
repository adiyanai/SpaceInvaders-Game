package animation;
import java.awt.Color;

import biuoop.DrawSurface;
/**
 * the PauseScreen animation start running when key "p" being pressed, instead of the Game one.
 * @author Adi Yanai
 *
 */
public class PauseScreen implements Animation {
   private boolean stop;
   /**
    * Constructor.
    */
   public PauseScreen() {
      this.stop = false;
   }

   /**
    * draw the pause screen.
    * @param d - the draw surface
    * @param dt - frames per second
    */
   public void doOneFrame(DrawSurface d, double dt) {
      d.setColor(Color.BLACK);
      d.fillRectangle(0, 0, d.getWidth(), d.getWidth());
      d.setColor(Color.WHITE);
      d.drawText(d.getWidth() / 2 - 120, d.getHeight() / 2 - 100, "paused", 70);
      d.drawText(d.getWidth() / 2 - 160, d.getHeight() / 2 , "press space to continue", 32);
   }

   /**
    * change a boolean variable when we want to stop the animation.
    * @return true or false
    */
   public boolean shouldStop() {
       return this.stop;
   }
}