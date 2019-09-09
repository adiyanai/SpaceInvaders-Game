package levels;
import java.util.List;
import game.Sprite;
import geometry.Velocity;
import graphic.Block;
/**
 * specifies the information required to fully describe a level.
 * @author Adi Yanai
 *
 */
public interface LevelInformation {
   /**
    * return the number of balls.
    * @return the number of balls
    */
   int numberOfBalls();
   /**
    * The initial velocity of each ball.
    * @return a list of velocities of all the balls
    */
   List<Velocity> initialBallVelocities();
   /**
    * return the paddle speed.
    * @return the paddle speed
    */
   int paddleSpeed();
   /**
    * return the paddle width.
    * @return the paddle width
    */
   int paddleWidth();
   /**
    * return the level name.
    * @return the level name
    */
   String levelName();
   /**
    * Returns a sprite with the background of the level.
    * @return a sprite with the background of the level
    */
   Sprite getBackground();
   /**
    * The Blocks that make up this level.
    * @return the Blocks that make up this level
    */
   List<Block> blocks();
   /**
    * the number of blocks to remove(blocks().size()).
    * @return the number of blocks to remove
    */
   int numberOfBlocksToRemove();
}