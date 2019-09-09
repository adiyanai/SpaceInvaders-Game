package game;

/**
 * An interface that present something that needs to happen, or something that
 * we can run() and return a value.
 * @author Adi Yanai
 * @param <T>
 */
public interface Task<T> {
    /**
     * run something that could run.
     * @return a value
     */
    T run();
}
