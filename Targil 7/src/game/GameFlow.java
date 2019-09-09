package game;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import animation.AnimationRunner;
import animation.EndScreen;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import counter.Counter;
import levels.LevelInformation;
import levels.LevelSpecificationReader;
/**
 * This class is in charge of creating the different levels,
 * and moving from one level to the next.
 * @author Adi Yanai
 *
 */
public class GameFlow {
   private AnimationRunner animationRunner;
   private KeyboardSensor keyboardSensor;
   private GameEnvironment e;
   private SpriteCollection s;
   private Counter score = new Counter(0); // the start score is 0
   private Counter lives = new Counter(3); // the start number of lives is 3
   private File fileTable;
   private HighScoresTable table;
   private double speed;
   private int numberOfLevel;

   /**
    * Constructor.
    * @param ar - an animation runner
    * @param ks - a keyboard
    * @param s - the sprite collection
    * @param e - the game environment
    * @param filename - a file
    * @param table - a high score table
    */
   public GameFlow(AnimationRunner ar, KeyboardSensor ks, SpriteCollection s, GameEnvironment e,
           File filename, HighScoresTable table) {
      this.animationRunner = ar;
      this.keyboardSensor = ks;
      this.s = s;
      this.e = e;
      this.fileTable = filename;
      this.table = table;
      if (this.fileTable.exists()) {
          try {
              this.table.load(filename);
          } catch (Exception ex) {
              System.out.println(ex.getMessage());
          }
      }
      this.speed = 0.5;
      this.numberOfLevel = 1;
   }

   /**
    * run all the levels and keep the score and lives across levels, throughout the entire game.
    * @param levels - a list of all the levels
    */
   public void runLevels(List<LevelInformation> levels) {
      this.score = new Counter(0);
      this.lives = new Counter(3);
      this.numberOfLevel = 1;
      // run the levels
      while (this.lives.getValue() != 0) {
          this.s = new SpriteCollection(new ArrayList<Sprite>());
          this.e = new GameEnvironment(new ArrayList<Collidable>());
          Reader reader;
          try {
              InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("alien_level_definitions.txt");
              reader = new InputStreamReader(is);
              LevelSpecificationReader levelsSpecificationReader = new LevelSpecificationReader();
              levels = levelsSpecificationReader.fromReader(reader);
          } catch (Exception ex) {
              ex.printStackTrace();
          }

          // create new GameLevel
          GameLevel level = new GameLevel(s, e, levels.get(0), this.keyboardSensor, this.score,
                                          this.lives, this.animationRunner);
          // set the speed of the game
          level.setGameSpeed(this.speed);
          // set the level number
          level.setNumberOfLevel(this.numberOfLevel);

          // initialize the level
          level.initialize();

          // while level has more blocks and player has more lives run the level
          while (level.getRemainedBlocks().getValue() != 0 && this.lives.getValue() > 0) {
              level.playOneTurn();
              // if the puddle hitted or the aliens arrived to the shields
              if (level.getPaddle().getPaddleHit() || level.getAliens().getCheckLose()) {
                  // decrease the lives by 1
                  this.lives.decrease(1);
              }
          }
          // when we move to the next level the speed increase
          this.speed *= 1.5;
          // increase the level number
          this.numberOfLevel++;
      }
      this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor,
              KeyboardSensor.SPACE_KEY, new EndScreen(this.score)));
      putScore();
      return;
   }

   /**
    * add a new score to the table score.
    */
   public void putScore() {
          if (this.table.getRank(this.score.getValue()) != -1) {
           // ask the player for his name to put in the table score
           DialogManager dialog = this.animationRunner.getGui().getDialogManager();
           String name = dialog.showQuestionDialog("Name", "What is your name?", "");
           ScoreInfo newScore = new ScoreInfo(name, this.score.getValue());
           // add new score
           this.table.add(newScore);
           try {
               this.table.save(this.fileTable);
              } catch (IOException ex) {
               ex.printStackTrace();
              }
           // show the high score table
           this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor,
                   KeyboardSensor.SPACE_KEY, new HighScoresAnimation(table)));
          }
   }
}