package counter;
import game.GameLevel;
import game.HitListener;
import graphic.Ball;
import graphic.Block;
/**
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 * @author Adi Yanai
 *
 */
public class BlockRemover implements HitListener {
   private GameLevel game;
   private Counter remainingBlocks;

   /**
    * Constructor.
    * @param game - a game
    * @param removedBlocks - the number of the blocks that in the game
    */
   public BlockRemover(GameLevel game, Counter removedBlocks) {
       this.game = game;
       this.remainingBlocks = removedBlocks;
   }

   /**
    * Blocks that are hit and reach 0 hit-points should be removed
    * from the game. Remember to remove this listener from the block
    * that is being removed from the game.
    * @param beingHit - a block that hit
    * @param hitter - the ball that doing the hitting
    */
   public void hitEvent(Block beingHit, Ball hitter) {
       // check if the number of hits that the block has is 0
       if (beingHit.getNumOfHits() == 0) {
           if ((beingHit.getCheckBlock() && hitter.checkBall())) {
               // remove this block from the game
               beingHit.removeFromGame(this.game);
               this.game.getBlocks().remove(beingHit);
               this.remainingBlocks.decrease(1);
           }

           if (!beingHit.getCheckBlock()) {
               beingHit.removeFromGame(this.game);
           }
       }
   }
}