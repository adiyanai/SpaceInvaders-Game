package animation;
import java.awt.Color;
import java.util.Random;

import biuoop.DrawSurface;
import counter.Counter;
/**
 * the end screen display the message "Game Over", your score is X" (X being the final score),
 * if the player losing all his lives.
 * @author Adi Yanai
 *
 */
public class EndScreen implements Animation {
    private boolean stop;
    private Counter finalScore;

    /**
     * Constructor.
     * @param finalScore - the final score of the player
     */
    public EndScreen(Counter finalScore) {
        this.stop = false;
        this.finalScore = finalScore;
    }

    /**
     * draw the end screen.
     * @param d - the draw surface
     * @param dt - frames per second
     */
    public void doOneFrame(DrawSurface d, double dt) {
        Random rand = new Random();
        //draw black background
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        // draw random blocks
        for (int i = 0; i < 15; i++) {
            d.setColor(new Color(rand.nextInt(250), rand.nextInt(250) , rand.nextInt(250)));
            d.fillRectangle(rand.nextInt(748), rand.nextInt(575), 52, 25);
        }
        // draw the "GAME OVER" massage
        d.setColor(Color.WHITE);
        d.drawText(d.getWidth() / 2 - 210, d.getHeight() / 2 - 100, "GAME OVER", 70);
        d.drawText(d.getWidth() / 2 - 120, d.getHeight() / 2 , "Your score is: " + this.finalScore.getValue(), 32);
    }

    /**
     * change a boolean variable when we want to stop the animation.
     * @return true or false
     */
    public boolean shouldStop() {
        return this.stop;
    }
}