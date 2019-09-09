package animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
/**
 * wrap an existing animation and add a "waiting-for-key" behavior to it.
 * @author Adi Yanai
 *
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean isAlreadyPressed;
    private boolean stop;
    /**
     * Constructor.
     * @param sensor - a keyboard sensor
     * @param key - a key
     * @param animation - an animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    /**
     * draw the animation and add a "waiting-for-key" behavior to it.
     * @param d - a draw surface
     * @param dt - frames per second
     */
    public void doOneFrame(DrawSurface d, double dt) {
        animation.doOneFrame(d, dt);
        if (this.sensor.isPressed(this.key) && !this.isAlreadyPressed) {
            this.stop = true;
        }

        if (!this.sensor.isPressed(this.key)) {
            this.isAlreadyPressed = false;
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