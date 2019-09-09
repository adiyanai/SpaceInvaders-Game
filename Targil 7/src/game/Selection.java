package game;
/**
 * present a selection that contain a key, a message and a task.
 * @author Adi Yanai
 * @param <T>
 */
public class Selection<T> {
    private String key;
    private String message;
    private T returnVal;
    /**
     * constructor.
     * @param key - a key
     * @param message - a message
     * @param returnVal - a task
     */
    public Selection(String key, String message, T returnVal) {
        this.key = key;
        this.message = message;
        this.returnVal = returnVal;
    }
    /**
     * return the key.
     * @return the key
     */
    public String getKey() {
        return this.key;
    }
    /**
     * return the message.
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }
    /**
     * return the task.
     * @return the task
     */
    public T getReturnVal() {
        return this.returnVal;
    }
}
