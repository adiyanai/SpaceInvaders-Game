package graphic;
import java.util.Map;

/**
 * Create a block by using the block information map.
 * @author Adi Yanai
 *
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Constructor.
     * @param spacerWidths - map of spacers information
     * @param blockCreators - map of blocks information
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * returns true if 's' is a valid space symbol.
     * @param s - a valid space symbol
     * @return - a valid space symbol
     */
    public boolean isSpaceSymbol(String s) {
        return (spacerWidths.containsKey(s));
    }

    /**
     * returns true if 's' is a valid block symbol.
     * @param s - a valid block symbol
     * @return a valid block symbol
     */
    public boolean isBlockSymbol(String s) {
        return (blockCreators.containsKey(s));
    }

    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     * @param s - a spacer-symbol
     * @return  the width in pixels associated with the given spacer-symbol
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * Returns the block definitions associated with symbol s.
     * @param s - a block symbol
     * @return the block information with the given block-symbol
     */
    public BlockCreator getBlockCreators(String s) {
        return this.blockCreators.get(s);
    }

    /**
     * Return a block according to the definitions associated
     * with symbol s. The block will be located at position (xpos, ypos).
     * @param s - a block symbol
     * @param x - the x of the upper left of the block
     * @param y - the y of the upper left of the block
     * @return a block according to the definitions associated with symbol s
     */
    public Block getBlock(String s, int x, int y) {
        return this.blockCreators.get(s).create(x, y);
    }
}
