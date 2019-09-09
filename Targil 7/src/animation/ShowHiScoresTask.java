package animation;
import game.Task;

/**
 * show the highscore animation.
 * @author Adi Yanai
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;
    /**
     * constructor.
     * @param runner - an animation runner
     * @param highScoresAnimation - a high score animation
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }
    /**
     * run the high score animation.
     * @return null
     */
    public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
    }
}