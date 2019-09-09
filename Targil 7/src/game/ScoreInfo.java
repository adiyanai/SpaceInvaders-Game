package game;

import java.io.Serializable;

/**
 * A score information that holds the player's name and his score.
 * @author Adi Yanai
 */
public class ScoreInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int score;

    /**
     * Constructor.
     * @param name - the player's name
     * @param score - the score of this player
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * get the name of the player.
     * @return the name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * get the score of the player.
     * @return the score of the player
     */
    public int getScore() {
        return this.score;
    }
}