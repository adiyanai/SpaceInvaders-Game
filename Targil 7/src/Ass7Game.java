import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import animation.Animation;
import animation.AnimationRunner;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import animation.Menu;
import animation.MenuAnimation;
import animation.ShowHiScoresTask;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.Collidable;
import game.GameEnvironment;
import game.GameFlow;
import game.HighScoresTable;
import game.Sprite;
import game.SpriteCollection;
import game.Task;
import levels.LevelInformation;
import levels.LevelSpecificationReader;

/**
 * accept a single commandline parameter. This parameter is a relative path to a
 * file containing level-sets information. This path will be relative to the
 * class-path. It reads the information in from this file and start the game
 * accordingly.
 * @author Adi Yanai
 *
 */
public class Ass7Game {
    public static final int WIDTH_DRAW_SURFACE = 800; // the width of the drawSurface
    public static final int HEIGHT_DRAW_SURFACE = 600; // the height of the drawSurface

    /**
     * accept a single commandline parameter. This parameter is a relative path to a
     * file containing level-sets information. This path will be relative to the
     * class-path. It reads the information in from this file and start the game
     * accordingly.
     *
     * @param args - nothing or a relative path to a file containing level-sets information
     */
    public static void main(String[] args) {
        GUI gui = new GUI("Space Invaders", WIDTH_DRAW_SURFACE, HEIGHT_DRAW_SURFACE);
        List<Collidable> collidable = new ArrayList<Collidable>();
        List<Sprite> sprite = new ArrayList<Sprite>();
        GameEnvironment environment = new GameEnvironment(collidable);
        SpriteCollection sprites = new SpriteCollection(sprite);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(gui, 60);

        File filename = new File("highscores.ser");
        HighScoresTable table = new HighScoresTable(7);

        GameFlow game = new GameFlow(runner, keyboard, sprites, environment, filename, table);
        // run the game
        while (true) {
            // scores animation
            Animation scores = new HighScoresAnimation(table);
            Animation scoresAnimation = new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY, scores);
            ShowHiScoresTask highScores = new ShowHiScoresTask(runner, scoresAnimation);
            String title = "Arkanoid";
            Menu<Task<Void>> menu = new MenuAnimation<>(keyboard, title);
            // a start task
            Task<Void> start = new Task<Void>() {
                public Void run() {
                    List<LevelInformation> levels = null;
                    Reader reader;
                    try {
                        InputStream is = ClassLoader.getSystemClassLoader().
                                getResourceAsStream("alien_level_definitions.txt");
                        reader = new InputStreamReader(is);
                        LevelSpecificationReader levelsSpecificationReader = new LevelSpecificationReader();
                        levels = levelsSpecificationReader.fromReader(reader);
                        game.runLevels(levels);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            // an exit task
            Task<Void> exit = new Task<Void>() {
                public Void run() {
                    runner.getGui().close();
                    System.exit(0);
                    return null;
                }
            };
            // create the menu
            menu.addSelection("s", "Start Game", start);
            menu.addSelection("h", "High Scores", highScores);
            menu.addSelection("e", "Exit", exit);

            runner.run(menu);
            // wait for user selection
            Task<Void> task = menu.getStatus();
            task.run();
        }
    }
}