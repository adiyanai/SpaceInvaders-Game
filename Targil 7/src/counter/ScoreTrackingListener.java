package counter;
import game.HitListener;
import graphic.Ball;
import graphic.Block;
/**
 * update this counter when blocks are being hit and removed.
 * @author Adi Yanai
 *
 */
public class ScoreTrackingListener implements HitListener {
   private Counter currentScore;
   /**
    * Constructor.
    * @param scoreCounter - a score
    */
   public ScoreTrackingListener(Counter scoreCounter) {
      this.currentScore = scoreCounter;
   }
   /**
    * This method is called whenever the beingHit object is hit, and add 5 points to the currentScore.
    * if the block that being hit has no more hits - the current score increase by another 10 points.
    * The hitter parameter is the Ball that's doing the hitting.
    * @param beingHit - the block that being hit
    * @param hitter - the ball that hit the block
    */
   public void hitEvent(Block beingHit, Ball hitter) {
       if (beingHit.getNumOfHits() == 0) {
           this.currentScore.increase(100);
       }
   }
}