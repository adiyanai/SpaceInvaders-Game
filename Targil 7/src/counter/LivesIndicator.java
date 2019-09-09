package counter;
import java.awt.Color;
import biuoop.DrawSurface;
import game.GameLevel;
import game.Sprite;
/**
 * In charge of displaying the current lives.
 * @author Adi Yanai
 *
 */
public class LivesIndicator implements Sprite {
    private Counter lives;
    /**
     * Constructor.
     * @param lives - a number of lives
     */
    public LivesIndicator(Counter lives) {
        this.lives = lives;
    }
    /**
     * draw the number of lives on the screen.
     * @param d - the draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(80, 17, "Lives:" + this.lives.getValue(), 17);
    }

    /**
     * notify the sprite that time has passed.
     * @param dt - frames per second
     */
    public void timePassed(double dt) {
    }

    /**
     * add the LivesIndicator to the game.
     * @param g - the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
