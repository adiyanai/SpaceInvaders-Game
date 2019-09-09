package graphic;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;

/**
 * This class read a block information from a file.
 * @author Adi Yanai
 *
 */
public class BlocksDefinitionReader {

    /**
     * Read the block file information and return maps that holds a block
     * information and spacers information.
     * @param reader - a file reader
     * @return maps that holds a block information and spacers information
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        Map<String, String> tempDefault = new TreeMap<String, String>();
        Map<String, String> tempMapBlock = new TreeMap<String, String>();
        Map<String, Integer> tempSpacers = new TreeMap<String, Integer>();
        Map<String, BlockCreator> blockCreators = new TreeMap<String, BlockCreator>();
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            // get current line
            String currentLine = bufferedReader.readLine();
            while (currentLine != null) {
                // check if the line doesn't empty and doesn't a note
                if (!currentLine.isEmpty() && !currentLine.startsWith("#")) {
                    String[] splitSpaces = currentLine.split(" ");
                    for (int i = 1; i < splitSpaces.length; i++) {
                        String[] splitColon = splitSpaces[i].split(":");
                        // check if the line is a default information and put it in this map
                        if (splitSpaces[0].equals("default")) {
                            tempDefault.put(splitColon[0], splitColon[1]);
                        }
                        // check if the line is a block information and put it in this map
                        if (splitSpaces[0].equals("bdef")) {
                            tempMapBlock.put(splitColon[0], splitColon[1]);
                        }
                        // check if the line is a spacers information and put it in this map
                        if (splitSpaces[0].equals("sdef")) {
                            int s = Integer.parseInt(splitSpaces[2].split(":")[1]);
                            tempSpacers.put(splitColon[1], s);
                        }
                    }
                    // if it is a block information create a block type with all his information and put it in the map
                    if (splitSpaces[0].equals("bdef")) {
                        BlockCreator block = setBlocks(tempMapBlock, tempDefault);
                        blockCreators.put(splitSpaces[1].split(":")[1], block);
                        tempMapBlock = new TreeMap<String, String>();
                    }
                }
                // read the next line
                currentLine = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                ex.getStackTrace();
            }
        }
        // return the maps that holds a block information and spacers information
        return new BlocksFromSymbolsFactory(tempSpacers, blockCreators);
    }

    /**
     * Create a block type.
     * @param tempMapBlock - map with the block information
     * @param tempDefault - map with the default information
     * @return - a block type
     */
    public static BlockCreator setBlocks(Map<String, String> tempMapBlock, Map<String, String> tempDefault) {
        int height, width, hitPoints;
        Map<String, Color> fillC = new TreeMap<String, Color>();
        Map<String, Image> fillI = new TreeMap<String, Image>();
        height = 0;
        width = 0;
        hitPoints = 1;
        Color stroke = null;
        // set all the block information to variables, by use the block map or the default one
        for (Map.Entry<String, String> entry : tempMapBlock.entrySet()) {
            if (tempMapBlock.containsKey("height")) {
                height = Integer.parseInt(tempMapBlock.get("height"));
            } else {
                height = Integer.parseInt(tempDefault.get("height"));
            }
            if (tempMapBlock.containsKey("width")) {
                width = Integer.parseInt(tempMapBlock.get("width"));
            } else {
                width = Integer.parseInt(tempDefault.get("width"));
            }
            if (tempMapBlock.containsKey("hit_points")) {
                hitPoints = Integer.parseInt(tempMapBlock.get("hit_points"));
            } else {
                hitPoints = Integer.parseInt(tempDefault.get("hit_points"));
            }
            ColorsParser colorsParser = new ColorsParser();
            if (tempMapBlock.containsKey("stroke")) {
                stroke = colorsParser.colorFromString(tempMapBlock.get("stroke"));
            } else if (tempDefault.containsKey("stroke")) {
                stroke = colorsParser.colorFromString(tempDefault.get("stroke"));
            } else {
                stroke = null;
            }
            if (entry.getKey().contains("fill")) {
                String fill = entry.getValue();
                if (fill.contains("image")) {
                    Image img = null;
                    try {
                        String[] temp = fill.split("\\(");
                        temp = temp[temp.length - 1].split("\\)");
                        img = ImageIO
                                .read(ClassLoader.getSystemClassLoader().getResourceAsStream(temp[0].split("/")[1]));
                        fillI.put(entry.getKey(), img);
                    } catch (IOException e) {
                        e.getStackTrace();
                    }
                } else {
                    fillC.put(entry.getKey(), colorsParser.colorFromString(entry.getValue()));
                }
            }
        }

        ColorsParser colorsParser = new ColorsParser();
        for (Map.Entry<String, String> entry : tempDefault.entrySet()) {
            if (entry.getKey().contains("fill")) {
                String fill = entry.getValue();
                if (fill.contains("image")) {
                    Image img = null;
                    try {
                        String[] temp = fill.split("\\(");
                        temp = temp[temp.length - 1].split("\\)");
                        img = ImageIO
                                .read(ClassLoader.getSystemClassLoader().getResourceAsStream(temp[0].split("/")[1]));
                        fillI.put(entry.getKey(), img);
                    } catch (IOException e) {
                        e.getStackTrace();
                    }
                } else {
                    fillC.put(entry.getKey(), colorsParser.colorFromString(entry.getValue()));
                }
            }
        }
        // create new block type and return him
        SetBlocks blockType = new SetBlocks(width, height, hitPoints, fillC, fillI, stroke);
        return blockType;
    }
}