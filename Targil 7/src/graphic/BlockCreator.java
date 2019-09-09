package graphic;
/**
 * Interface to create a block.
 * @author Adi Yanai
 *
 */
public interface BlockCreator {
       /**
        * Create a block at the specified location.
        * @param xpos - the x of the upper left of the block
        * @param ypos - the y of the upper left of the block
        * @return a block
        */
       Block create(int xpos, int ypos);
       /**
        * get the width.
        * @return the width of the block
        */
       int getWidth();
}
