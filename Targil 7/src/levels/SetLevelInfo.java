package levels;

import java.util.List;

import game.Sprite;
import geometry.Velocity;
import graphic.Block;

/**
 * set the level information.
 * @author Adi Yanai
 */
public class SetLevelInfo implements LevelInformation {
    private List<Velocity> v;
    private int pSpeed;
    private int pWidth;
    private String nameLevel;
    private Sprite background;
    private List<Block> blocksList;
    private int removeBlocks;
    private int numOfBalls;

    /**
     * constructor.
     * @param v - list of the balls velocity
     * @param pSpeed - the paddle speed
     * @param pWidth - the paddle width
     * @param nameLevel - the level name
     * @param background - the background of the level
     * @param blocksList - a list of blocks
     * @param removeBlocks - the number of the blocks we need to remove to pass the level
     */
    public SetLevelInfo(List<Velocity> v, int pSpeed, int pWidth, String nameLevel, Sprite background,
            List<Block> blocksList, int removeBlocks) {
        this.v = v;
        this.pSpeed = pSpeed;
        this.pWidth = pWidth;
        this.nameLevel = nameLevel;
        this.background = background;
        this.blocksList = blocksList;
        this.removeBlocks = removeBlocks;
        this.numOfBalls = v.size();
    }

    /**
     * return the number of balls.
     * @return the number of balls
     */
    public int numberOfBalls() {
        return this.numOfBalls;
    }

    /**
     * The initial velocity of each ball.
     * @return a list of velocities of all the balls
     */
    public List<Velocity> initialBallVelocities() {
        return this.v;
    }

    /**
     * return the paddle speed.
     * @return the paddle speed
     */
    public int paddleSpeed() {
        return this.pSpeed;
    }

    /**
     * return the paddle width.
     * @return the paddle width.
     */
    public int paddleWidth() {
        return this.pWidth;
    }

    /**
     * return the level name.
     * @return the level name
     */
    public String levelName() {
        return this.nameLevel;
    }

    /**
     * Returns a sprite with the background of the level.
     * @return a sprite with the background of the level
     */
    public Sprite getBackground() {
        return this.background;
    }

    /**
     * The Blocks that make up this level.
     * @return the Blocks that make up this level
     */
    public List<Block> blocks() {
        return this.blocksList;
    }

    /**
     * the number of blocks to remov.
     * @return the number of blocks to remove
     */
    public int numberOfBlocksToRemove() {
        return this.removeBlocks;
    }
}