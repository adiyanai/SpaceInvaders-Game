package animation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.Selection;

/**
 * a class that present a menu animation, a screen stating the game name,
 * and a list of several options of what to do next.
 * @author Adi Yanai
 * @param <T>
 */
public class MenuAnimation<T> implements Menu<T> {
    private List<Selection<T>> listSelection;
    private KeyboardSensor keyboard;
    private String title;
    private T status;
    /**
     * constructor.
     * @param keyboard - a keyboard
     * @param title - a title
     */
    public MenuAnimation(KeyboardSensor keyboard, String title) {
        this.keyboard = keyboard;
        this.title = title;
        this.listSelection = new ArrayList<>();
    }

    /**
     * draw the menu animation.
     * @param d - the draw surface
     * @param dt - frames per second
     */
    public void doOneFrame(DrawSurface d, double dt) {
        Color back = new Color(51, 153, 255);
        d.setColor(back);
        d.fillRectangle(0, 0, 800, 600);
        // draw the title
        d.setColor(Color.white);
        d.drawText(45, 70, this.title, 50);
        int index = 1;
        // draw all the selections
        for (Selection<T> selection : this.listSelection) {
            d.drawText(100, 150 + index, "(" + selection.getKey() + ")", 32);
            d.drawText(150, 150 + index, selection.getMessage(), 32);
            if (this.keyboard.isPressed(selection.getKey())) {
                this.status = (T) selection.getReturnVal();
            }
            index += 80;
        }
    }

    /**
     * change a boolean variable when we want to stop the animation.
     * @return true or false
     */
    public boolean shouldStop() {
        return this.status != null;
    }

    /**
     * add selection to the menu.
     * @param key - a key
     * @param message - a message
     * @param returnVal - a value
     */
    public void addSelection(String key, String message, T returnVal) {
        this.listSelection.add(new Selection<T>(key, message, returnVal));
    }

    /**
     * get the status of the selection in the menu.
     * @return the status of the selection in the menu
     */
    public T getStatus() {
        return this.status;
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
    }
}
