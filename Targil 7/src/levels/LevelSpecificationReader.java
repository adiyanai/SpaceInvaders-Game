package levels;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import geometry.Velocity;
import graphic.Block;
import graphic.BlocksDefinitionReader;
import graphic.BlocksFromSymbolsFactory;
import graphic.ColorsParser;

/**
 * Read a file of level information and convert it to a level.
 * @author Adi Yanai
 *
 */
public class LevelSpecificationReader {
    private boolean inLevel = false;
    private boolean inBlocks = false;

    /**
     * Read a file of level information and convert it to a level.
     * @param reader - the file of the levels information.
     * @return a list of levels information
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        List<LevelInformation> levelInformationList = new ArrayList<>();
        Map<String, String> levelInformation = new TreeMap<String, String>();
        LevelInformation newLevel = null;
        List<String> blocksRows = new ArrayList<String>();
        BufferedReader newLine = new BufferedReader(reader);
        try {
            String currentLine = newLine.readLine();
            // read the file while we don't in the end
            while (currentLine != null) {
                // if the line doesn't empty and doesn't a note
                if (!currentLine.isEmpty() && !currentLine.startsWith("#")) {
                    // in the level information but not in the blocks information
                    if (inTheLevel(currentLine) && !inTheBlocks(currentLine) && !currentLine.equals("END_BLOCKS")) {
                        if (currentLine.equals("START_LEVEL")) {
                            currentLine = newLine.readLine();
                        }
                        String[] splitLine = currentLine.split(":");
                        levelInformation.put(splitLine[0], splitLine[1]);
                        // set the blocks when we in the "START_BLOCKS"
                    } else if (inTheLevel(currentLine) && inTheBlocks(currentLine)) {
                        if (currentLine.equals("START_BLOCKS")) {
                            currentLine = newLine.readLine();
                        }
                        blocksRows.add(currentLine);
                        // create level when we end to read the file/level information
                    } else if (!inTheLevel(currentLine) && !inTheBlocks(currentLine)) {
                        newLevel = createLevel(levelInformation, blocksRows);
                    }
                    // if we have a new level, ad him to the list
                    if (newLevel != null) {
                        levelInformationList.add(newLevel);
                        this.inLevel = false;
                        this.inBlocks = false;
                        newLevel = null;
                        levelInformation = new TreeMap<String, String>();
                        blocksRows = new ArrayList<String>();
                    }
                }
                // read the next line
                currentLine = newLine.readLine();
            }

        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            try {
                newLine.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        // return a list of levels information
        return levelInformationList;
    }

    /**
     * check if we already in the "START_LEVEL", or that we got to the "END_LEVEL".
     * @param currentLine - the current line
     * @return true if we already in the "START_LEVEL", false if we got to the "END_LEVEL"
     */
    private boolean inTheLevel(String currentLine) {
        if (currentLine.equals("START_LEVEL")) {
            this.inLevel = true;
        } else if (currentLine.equals("END_LEVEL")) {
            this.inLevel = false;
        }
        return this.inLevel;
    }

    /**
     * check if we already in the "START_BLOCKS", or that we got to the "END_BLOCKS".
     * @param currentLine - the current line
     * @return true if we already in the "START_BLOCKS", false if we got to the "END_BLOCKS"
     */
    private boolean inTheBlocks(String currentLine) {
        if (currentLine.equals("START_BLOCKS")) {
            this.inBlocks = true;
        } else if (currentLine.equals("END_BLOCKS")) {
            this.inBlocks = false;
        }
        return this.inBlocks;
    }

    /**
     * check if we have all the information of the level.
     * @param levelInformation - a map with the level information
     * @return true if we have all the information of the level, else false
     */
    public boolean checkIfLevelFull(Map<String, String> levelInformation) {
        if (levelInformation.size() == 10) {
            return true;
        }
        return false;
    }

    /**
     * Create level.
     * @param levelInformation - a map with levels information
     * @param blocksRows - list of the blocks we need to draw
     * @return - a level
     */
    private LevelInformation createLevel(Map<String, String> levelInformation, List<String> blocksRows) {
        SetLevelInfo newLevel = null;
        // level name
        String levelName = levelInformation.get("level_name");
        // balls velocity
        String ballsVelocity = levelInformation.get("ball_velocities");
        List<Velocity> listVelocity = new ArrayList<Velocity>();
        String[] splitSpace = ballsVelocity.split(" ");
        String[] splitHint;
        for (int i = 0; i < splitSpace.length; i++) {
            splitHint = splitSpace[i].split(",");
            listVelocity
                    .add(Velocity.fromAngleAndSpeed(Integer.parseInt(splitHint[0]), Integer.parseInt(splitHint[1])));
        }
        // level background
        Background s;
        Color color = null;
        Image image = null;
        String background = levelInformation.get("background");
        ColorsParser colorsParser = new ColorsParser();
        if (background.contains("image")) {
            Image img = null;
            try {
                String[] temp = background.split("\\(");
                temp = temp[temp.length - 1].split("\\)");
                img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(temp[0]));
                image = img;
            } catch (IOException e) {
                e.getStackTrace();
            }
        } else {
            color = colorsParser.colorFromString(background);
        }
        // if the background is a color
        if (color != null) {
            s = new Background(color);
            // if the background is an image
        } else {
            s = new Background(image);
        }

        // paddle information
        int paddleSpeed = Integer.parseInt(levelInformation.get("paddle_speed"));
        int paddleWidth = Integer.parseInt(levelInformation.get("paddle_width"));

        // block definitions
        String blockDefinitions = levelInformation.get("block_definitions");
        BlocksFromSymbolsFactory blocks = null;
        InputStreamReader blockReader = new InputStreamReader(
                ClassLoader.getSystemClassLoader().getResourceAsStream(blockDefinitions.split("/")[1]));
        blocks = BlocksDefinitionReader.fromReader(blockReader);

        // blocks start
        int blocksStartX = Integer.parseInt(levelInformation.get("blocks_start_x"));
        int blocksStartY = Integer.parseInt(levelInformation.get("blocks_start_y"));

        // row height
        int rowHeight = Integer.parseInt(levelInformation.get("row_height"));

        // the number of blocks that need to be destroyed in order to pass this level
        int numBlocks = Integer.parseInt(levelInformation.get("num_blocks"));

        // list of the level blocks
        List<Block> blockList = setBlocks(blocks, blocksRows, blocksStartX, blocksStartY, rowHeight);

        if (checkIfLevelFull(levelInformation)) {
            // create new level
            newLevel = new SetLevelInfo(listVelocity, paddleSpeed, paddleWidth, levelName, s, blockList, numBlocks);
            return newLevel;
        }
        return null;
    }

    /**
     * Create list of blocks.
     * @param blocks - contain map of block information and map of spacers information
     * @param blocksRows - the lines with the blocks we need to create
     * @param blocksStartX - the x of the blocks start
     * @param blocksStartY - the y of the blocks start
     * @param rowHeight - the row height
     * @return - list of blocks
     */
    private List<Block> setBlocks(BlocksFromSymbolsFactory blocks, List<String> blocksRows, int blocksStartX,
            int blocksStartY, int rowHeight) {
        List<Block> blocksList = new ArrayList<Block>();
        Block currentBlock = null;
        String b, s;
        int x;
        x = blocksStartX;
        for (int i = 0; i < blocksRows.size(); i++) {
            // increase the y by the row height if the line is a spacer symbol
            if (blocks.isSpaceSymbol(blocksRows.get(i))) {
                blocksStartY += rowHeight;
            } else if (!blocksRows.isEmpty()) {
                // get one block row
                b = blocksRows.get(i);
                // goes over all the blocks and the spacers in this row
                for (int j = 0; j < b.length(); j++) {
                    s = Character.toString(b.charAt(j));
                    // the symbol is a spacer one
                    if (blocks.isSpaceSymbol(s)) {
                        x += blocks.getSpaceWidth(s);
                    }
                    // the symbol is a block one
                    if (blocks.isBlockSymbol(s)) {
                        currentBlock = blocks.getBlock(s, x, blocksStartY);
                        blocksList.add(currentBlock);
                        x += blocks.getBlockCreators(s).getWidth();
                    }
                }
                blocksStartY += rowHeight;
                x = blocksStartX;
            }
        }
        // return a list of blocks
        return blocksList;
    }
}