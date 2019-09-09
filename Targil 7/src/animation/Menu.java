package animation;

/**
 * Menu.
 * @author Adi Yanai
 * @param <T>
 */
public interface Menu<T> extends Animation {
    /**
     * add selection to the menu.
     * @param key - a key
     * @param message - a message
     * @param returnVal - a value
     */
    void addSelection(String key, String message, T returnVal);
    /**
     * get the status of the selection in the menu.
     * @return the status of the selection in the menu
     */
    T getStatus();
    /**
     * add sub menu.
     * @param key - a key
     * @param message - a message
     * @param subMenu - a sub menu
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}