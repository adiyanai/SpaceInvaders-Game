package animation;
import java.awt.Color;

import biuoop.DrawSurface;
import game.HighScoresTable;
import game.ScoreInfo;

/**
 * display the scores in the high-scores table, until a specified key is pressed.
 * @author Adi Yanai
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable scores;
    private boolean stop;

    /**
     * Constructor.
     * @param scores - a high score table
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
        this.stop = false;
    }

    /**
     * draw the high score table.
     * @param d - a draw surface
     * @param dt - frames per second
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(new Color(255, 255, 153));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(new Color(0, 128, 255));
        d.drawText(49, 59, "High Scores", 51);
        d.setColor(new Color(102, 178, 255));
        d.drawText(90, 120, "Player Name", 30);
        d.drawText(450, 120, "Score", 30);
        d.setColor(Color.BLACK);
        d.fillRectangle(90, 125, 620, 5);
        d.setColor(new Color(102, 178, 255));
        d.fillRectangle(90, 126, 620, 3);
        d.drawText(250, 550, "Press space to continue", 30);
        d.setColor(Color.BLACK);
        int index = 0;
        // draws all the players and there score
        for (ScoreInfo scoreInfo : this.scores.getHighScores()) {
            d.drawText(90, 160 + index * 40, scoreInfo.getName(), 30);
            d.drawText(450, 160 + index * 40, Integer.toString(scoreInfo.getScore()), 30);
            index++;
        }
    }

    /**
     * change a boolean variable when we want to stop the animation.
     * @return true or false
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
