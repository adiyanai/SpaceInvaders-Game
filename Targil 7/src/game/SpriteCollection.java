package game;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;
/**
 * hold a collection of sprites.
 * @author Adi Yanai
 *
 */
public class SpriteCollection {
    private List<Sprite> sprite;

    /**
     * constructor of sprite collection.
     * @param sprite - a sprites list
     */
    public SpriteCollection(List<Sprite> sprite) {
        this.sprite = sprite;
    }

    /**
     * Set the game environment with new list of sprites.
     * @param newSprite - a new list of sprites
     */
    public void setSpriteCollection(List<Sprite> newSprite) {
        this.sprite = newSprite;
    }

    /**
     * return a list of sprites.
     * @return a list of sprites
     */
    public List<Sprite> getSpriteCollection() {
        return this.sprite;
    }

    /**
     * add new sprite to the list.
     * @param s - a sprite
     */
    public void addSprite(Sprite s) {
        sprite.add(s);
    }

    /**
     * call timePassed() on all sprites.
     * @param dt - frames per second
     */
    public void notifyAllTimePassed(double dt) {
        List<Sprite> copySprite = new ArrayList<>(this.sprite);
        // check if the list of sprites is not empty
        if (!copySprite.isEmpty()) {
            // goes over all the sprites in the list
            for (int i = 0; i < copySprite.size(); i++) {
                copySprite.get(i).timePassed(dt);
            }
        }
    }

    /**
     * call drawOn(d) on all sprites.
     * @param d - a draw surface
     */
    public void drawAllOn(DrawSurface d) {
        List<Sprite> copySprite = new ArrayList<>(this.sprite);
        // check if the list of sprites is not empty
        if (!copySprite.isEmpty()) {
            // goes over all the sprites in the list
            for (int i = 0; i < copySprite.size(); i++) {
                copySprite.get(i).drawOn(d);
            }
        }
    }
}