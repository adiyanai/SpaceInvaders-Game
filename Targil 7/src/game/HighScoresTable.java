package game;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * Manage a table of size high-scores.
 * The class has methods that will allow storing the table
 * to file and loading it back again.
 * @author Adi Yanai
 *
 */
public class HighScoresTable implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<ScoreInfo> scoreInfo;
    private int size;

    /**
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     * @param size - the size of the table score
     */
    public HighScoresTable(int size) {
        this.scoreInfo = new ArrayList<ScoreInfo>();
        this.size = size;
    }

    /**
     * Add a high-score.
     * @param score - a score
     */
    public void add(ScoreInfo score) {
        // if there is no more place to add another score
        if (this.getRank(score.getScore()) == -1) {
            return;
        }
        this.scoreInfo.add(this.getRank(score.getScore()) - 1, score);

        // if the size of the list is now bigger than the 'size', delete the last score in the list
        if (this.scoreInfo.size() > this.size) {
            this.scoreInfo.remove(this.size);
        }
    }

    /**
     * Return table size.
     * @return - table size.
     */
    public int size() {
        return this.size;
    }

    /**
     * Return the current high scores.
     * The list is sorted such that the highest scores come first.
     * @return - the current high scores
     */
    public List<ScoreInfo> getHighScores() {
        return this.scoreInfo;
    }

    /**
     * return the rank of the current score: where will it be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not be added to the list.
     * @param score - a score
     * @return - the rank of the current score
     */
    public int getRank(int score) {
        int rank = -1;
        for (int i = 0; i < this.scoreInfo.size(); i++) {
            if (this.scoreInfo.get(i).getScore() <= score) {
                rank = i + 1;
                break;
            }
        }

        // if there score is the lowest in the list and he's rank is not bigger than 'size'
        if (rank == -1 && this.size != this.scoreInfo.size()) {
            rank = this.scoreInfo.size() + 1;
        }
        // return the rank
        return rank;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.scoreInfo.clear();
    }

    /**
     * Load table data from file.
     * Current table data is cleared.
     * @param filename - a file
     * @throws IOException - "Failed reading object"
     */
    public void load(File filename) throws IOException {
        if (loadFromFile(filename) != null) {
            this.scoreInfo = loadFromFile(filename).getHighScores();
        } else {
            throw new IOException("Failed reading object");
        }
    }

    /**
     * Save table data to the specified file.
     * @param filename - a file
     * @throws IOException - "Failed saving object"
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(this);
        } catch (IOException e) {
            System.err.println("Failed saving object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }

    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     * @param filename - a file
     * @return a high score table
     */
    public static HighScoresTable loadFromFile(File filename) {
        ObjectInputStream in = null;
        HighScoresTable highScoreTable = null;
        try {
            in = new ObjectInputStream(new FileInputStream(filename));
            highScoreTable = (HighScoresTable) in.readObject();
        } catch (FileNotFoundException e) { // Can't find file to open
            System.err.println("Unable to find file: " + filename);
            return null;
        } catch (ClassNotFoundException e) { // The class in the stream is unknown to the JVM
            System.err.println("Unable to find class for object in file: " + filename);
            return null;
        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
        return highScoreTable;
    }

    /**
     * print the score table.
     */
    public void printTable() {
        int i = 1;
        for (ScoreInfo score: this.scoreInfo) {
            System.out.println("RANK(" + i + ") - name:" + score.getName() + "   score:" + score.getScore());
            i++;
        }
    }
}