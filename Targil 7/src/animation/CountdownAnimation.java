package animation;
import java.awt.Color;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import game.SpriteCollection;
/**
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) seconds, before
 * it is replaced with the next one.
 * @author Adi Yanai
 *
 */
public class CountdownAnimation implements Animation {
   private Sleeper sleeper = new Sleeper();
   private double numOfSeconds;
   private int countFrom;
   private int numCount;
   private SpriteCollection gameScreen;

   /**
    * Constructor.
    * @param numOfSeconds - the number of seconds the number will appear on the screen
    * @param countFrom - the number we count down from back to 1
    * @param gameScreen - a game screen
    */
   public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
       this.numOfSeconds = numOfSeconds;
       this.countFrom = countFrom;
       this.gameScreen = gameScreen;
       /*
        *  countFrom is a variable that will change so that is why we save the number of countFrom
        *  in another variable so we could compare to his start value later.
        */
       this.numCount = countFrom;
   }

   /**
    * draw all the sprites and the count down.
    * @param d - a draw surface
    * @param dt - frames per second
    */
   public void doOneFrame(DrawSurface d, double dt) {
       int n = 1000 * (int) this.numOfSeconds;
       // draw the game screen
       this.gameScreen.drawAllOn(d);

       // if the countFrom bigger than -1
       if (!this.shouldStop()) {
           // print "GO" after the count down finished
           if (this.countFrom == 0) {
               d.setColor(new Color(153, 153, 255));
               d.drawText((d.getWidth() / 2) - 70, 350, "GO", 100);
           } else {
               if (this.countFrom != -1) {
                   d.setColor(new Color(153, 153, 255));
                   d.drawText((d.getWidth() / 2) - 20, 350, String.valueOf(this.countFrom), 100);
               }
           }

           // check if its the first time we enter to the function
           if (this.countFrom != numCount) {
               this.sleeper.sleepFor(n);
           }
           // decrease the countFrom by 1
           this.countFrom--;
       }
   }

   /**
    * return false if the countFrom bigger than -1 else, return true (and stop the countDown).
    * @return false if the countFrom bigger than -1 else, return true
    */
   public boolean shouldStop() {
       if (this.countFrom < -1) {
           return true;
       }
       return false;
   }
}