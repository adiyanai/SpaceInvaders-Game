package animation;
import biuoop.DrawSurface;
import biuoop.GUI;
/**
 * Run the animation.
 * @author Adi Yanai
 */
public class AnimationRunner {
   private GUI gui;
   private int framesPerSecond;
   private biuoop.Sleeper sleeper = new biuoop.Sleeper();

   /**
    * Constructor.
    * @param gui - a GUI
    * @param framesPerSecond - frames per second
    */
   public AnimationRunner(GUI gui, int framesPerSecond) {
       this.gui = gui;
       this.framesPerSecond = framesPerSecond;
   }

   /**
    * return the gui.
    * @return - the gui
    */
   public GUI getGui() {
       return this.gui;
   }

   /**
    * run an animation.
    * @param animation - a animation
    */
   public void run(Animation animation) {
      int millisecondsPerFrame = 1000 / this.framesPerSecond;
      double dt = 1.0 / 60.0;
      while (!animation.shouldStop()) {
         long startTime = System.currentTimeMillis(); // timing
         DrawSurface d = gui.getDrawSurface();

         animation.doOneFrame(d, dt);

         gui.show(d);
         long usedTime = System.currentTimeMillis() - startTime;
         long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
         if (milliSecondLeftToSleep > 0) {
             this.sleeper.sleepFor(milliSecondLeftToSleep);
         }
      }
   }
}