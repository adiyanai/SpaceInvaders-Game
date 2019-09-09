package levels;
import java.awt.Color;
import biuoop.DrawSurface;
import game.GameLevel;
import game.Sprite;
/**
 * In charge of displaying the current level name.
 * @author Adi Yanai
 *
 */
public class LevelName  implements Sprite {
    private String levelName;
    /**
     * Constructor.
     * @param n - a string of a level name
     */
    public LevelName(String n) {
        this.levelName = n;
    }

    /**
     * draw the level name on the draw surface.
     * @param d - a draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(550, 17, "Level Name: " + this.levelName, 17);
    }

    /**
     * nothing.
     * @param dt - frames per second
     */
    public void timePassed(double dt) {
    }

    /**
     * add the LevelName to the game.
     * @param g - the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
