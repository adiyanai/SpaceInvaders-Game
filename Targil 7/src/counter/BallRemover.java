package counter;
import game.GameLevel;
import game.HitListener;
import graphic.Ball;
import graphic.Block;
/**
 * charge of removing balls, and updating an available-balls counter.
 * @author Adi Yanai
 *
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * Constructor.
     * @param game - the game
     * @param removedBalls - the number of balls in the level
     */
    public BallRemover(GameLevel game, Counter removedBalls) {
        this.game = game;
        this.remainingBalls = removedBalls;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     * @param beingHit - a block that hit
     * @param hitter - the ball that doing the hitting
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        // remove the ball from the game
        hitter.removeFromGame(game);
        // decrease the number of balls by 1
        this.remainingBalls.decrease(1);
    }
}