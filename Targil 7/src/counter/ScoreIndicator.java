package counter;
import java.awt.Color;
import biuoop.DrawSurface;
import game.GameLevel;
import game.Sprite;
/**
 * In charge of displaying the current score.
 * @author Adi Yanai
 *
 */
public class ScoreIndicator implements Sprite {
    private Counter score;
    /**
     * Constructor.
     * @param score - a score
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }
    /**
     * draw the score to the screen.
     * @param d - the draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(350, 17, "Score:" + this.score.getValue(), 17);
    }

    /**
     * notify the sprite that time has passed.
     * @param dt - frames per second
     */
    public void timePassed(double dt) {
    }

    /**
     * add the ScoreIndicator to the game.
     * @param g - the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}