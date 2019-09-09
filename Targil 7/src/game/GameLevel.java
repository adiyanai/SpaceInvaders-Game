package game;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import animation.KeyPressStoppableAnimation;
import animation.PauseScreen;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import counter.BallRemover;
import counter.BlockRemover;
import counter.Counter;
import counter.LivesIndicator;
import counter.ScoreIndicator;
import counter.ScoreTrackingListener;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import graphic.Aliens;
import graphic.Ball;
import graphic.Block;
import graphic.Paddle;
import levels.LevelInformation;
import levels.LevelName;
/**
 * Game holds the sprites and the collidables, and will be in charge of the animation.
 * @author Adi Yanai
 *
 */
public class GameLevel implements Animation {
   // the width of the left&right block boundaries and the height of the up&down block boundaries
   public static final int BOUNDARIES_SIZE = 10;
   public static final int BALLS_RADIUS = 4; // the balls radius
   public static final int PADDLE_HEIGHT = 20; // the height of the paddle
   private List<Block> allTheBlocks;
   private Aliens aliens;
   private List<Block> aliensBlocks;
   private List<Ball> balls;
   private LevelInformation level;
   private SpriteCollection sprites;
   private GameEnvironment environment;
   private BlockRemover blockListener;
   private BallRemover ballListener;
   private ScoreTrackingListener scoreListener;
   private ScoreIndicator scoreIndicator;
   private LivesIndicator liveIndicator;
   private LevelName levelName;
   private Paddle paddle;
   private Counter score;
   private Counter remainedBlocks;
   private Counter remainedBalls = new Counter(0);
   private Counter numOfLives;
   private AnimationRunner runner;
   private boolean running = true;
   private KeyboardSensor keyboard;
   private int countPaddleShoot;
   private int countShootBalls;
   private double gameSpeed;
   private String numberOfLevel;

   /**
    * constructor of the game.
    * @param s - a sprites collection
    * @param e - the game environment
    * @param l - the level information
    * @param keyboard - the keyboard
    * @param score - the score of the player
    * @param numOfLives - the number of lives that the player has
    * @param runner - an animation runner
    */
   public GameLevel(SpriteCollection s, GameEnvironment e, LevelInformation l, KeyboardSensor keyboard,
                    Counter score, Counter numOfLives, AnimationRunner runner) {
       this.sprites = s;
       this.environment = e;
       this.level = l;
       this.keyboard = keyboard;
       this.score = score;
       this.numOfLives = numOfLives;
       this.runner = runner;
       this.countPaddleShoot = 35;
       this.countShootBalls = 50;
       this.balls = new ArrayList<Ball>();
   }

   /**
    * get the paddle of the game.
    * @return the paddle of the game
    */
   public Paddle getPaddle() {
       return this.paddle;
   }

   /**
    * get the aliens - the enemies.
    * @return the aliens
    */
   public Aliens getAliens() {
       return this.aliens;
   }

   /**
    * set the game speed.
    * @param speed - the game speed
    */
   public void setGameSpeed(double speed) {
       this.gameSpeed = speed;
   }

   /**
    * set the level number.
    * @param number - the level number
    */
   public void setNumberOfLevel(int number) {
       this.numberOfLevel = String.valueOf(number);
   }

   /**
    * return the block list.
    * @return the block list
    */
   public List<Block> getBlocks() {
       return this.aliensBlocks;
   }
   /**
    * get the remained blocks.
    * @return - the remained blocks
    */
   public Counter getRemainedBlocks() {
       return this.remainedBlocks;
   }
   /**
    * get the number of lives.
    * @return - the number of lives
    */
   public Counter getNumOfLives() {
       return this.numOfLives;
   }
   /**
    * get the number of balls.
    * @return - the number of balls
    */
   public Counter getNumOfBalls() {
       return this.remainedBalls;
   }
   /**
    * add the given collidable to the environment.
    * @param c - a collidable
    */
   public void addCollidable(Collidable c) {
       this.environment.addCollidable(c);
   }

   /**
    * add new sprite to the list sprite collection.
    * @param s - a sprite
    */
   public void addSprite(Sprite s) {
       this.sprites.addSprite(s);
   }

   /**
    * return the sprite collection.
    * @return the sprite collection
    */
   public SpriteCollection getSprites() {
       return this.sprites;
   }

   /**
    * remove a block(collidable) from the GameEnvironment.
    * @param c - a collidable
    */
   public void removeCollidable(Collidable c) {
       List<Collidable> newL = this.environment.getList();
       // remove this collidable from the environment
       newL.remove(c);
       // change the game environment to the new list of the collidables
       this.environment.setGameEnvironment(newL);
   }

   /**
    * remove a block(sprite) from the SpriteCollection.
    * @param s - a sprite
    */
   public void removeSprite(Sprite s) {
       List<Sprite> newSprite = this.sprites.getSpriteCollection();
       // remove this sprite from the spriteCollection
       newSprite.remove(s);
       // change the spriteCollection to the new list of sprites
       this.sprites.setSpriteCollection(newSprite);
   }

   /**
    * Create the boundaries blocks.
    * @return - a list with the boundaries blocks
    */
   public List<Block> createBoundariesBlocks() {
       List<Block> blocks = new ArrayList<Block>();
       Block rightBlock, lowBlock, leftBlock, highBlock;
       int width, height;
       width = this.runner.getGui().getDrawSurface().getWidth();
       height = this.runner.getGui().getDrawSurface().getHeight();
       Map<String, Color> colorBoundaries = new TreeMap<String, Color>();
       Map<String, Image> imageBoundaries = new TreeMap<String, Image>();
       colorBoundaries.put("0", Color.DARK_GRAY);
       // create the blocks in the boundaries of the screen
       rightBlock = new Block(new Rectangle(new Point(width, 0), BOUNDARIES_SIZE,
               height, colorBoundaries, imageBoundaries, Color.DARK_GRAY), 0, false);
       lowBlock = new Block(new Rectangle(new Point(0, height), width, BOUNDARIES_SIZE,
               colorBoundaries, imageBoundaries, Color.DARK_GRAY), 0, false);
       leftBlock = new Block(new Rectangle(new Point(-10, 0), BOUNDARIES_SIZE, height,
               colorBoundaries, imageBoundaries, Color.DARK_GRAY), 0, false);
       highBlock = new Block(new Rectangle(new Point(0, 10), width, BOUNDARIES_SIZE,
               colorBoundaries, imageBoundaries, Color.DARK_GRAY), 0, false);

       Map<String, Color> colorInfo = new TreeMap<String, Color>();
       colorInfo.put("0", Color.LIGHT_GRAY);
       //game information block
       Block gameInformation = new Block(new Rectangle(new Point(0, 0), width, 20, colorInfo,
               imageBoundaries, Color.LIGHT_GRAY), 0, false);
       rightBlock.setStringHits();
       lowBlock.setStringHits();
       leftBlock.setStringHits();
       highBlock.setStringHits();
       blocks.add(rightBlock);
       blocks.add(lowBlock);
       blocks.add(leftBlock);
       blocks.add(highBlock);
       blocks.add(gameInformation);
       return blocks;
   }

   /**
    * create the shields blocks.
    * @return a list of the shields blocks
    */
   public List<Block> createShieldsBlocks() {
       List<Block> s = new ArrayList<Block>();
       Map<String, Color> colors = new TreeMap<String, Color>();
       Map<String, Image> images = new TreeMap<String, Image>();
       Rectangle r;
       int x, y;
       Block b;
       colors.put("1", Color.yellow);
       x = 225;
       y = 500;
       for (int i = 0; i < 3; i++) {
           for (int j = 0; j < 120; j++) {
               if (j % 30 == 0) {
                   y += 5;
                   x -= 150;
               }
               r = new Rectangle(new Point(x, y), 5, 5, colors, images, null);
               b = new Block(r, 1, false);
               b.addHitListener(this.blockListener);
               s.add(b);
               x += 5;
           }
           y = 500;
           x += 250;
       }
       return s;
   }

   /**
    * Initialize a new game: create the Blocks and Ball (and Paddle) and add them to the game.
    */
   public void initialize() {
       this.sprites.addSprite(this.level.getBackground());
       this.ballListener = new BallRemover(this, remainedBalls);
       // print the score on the screen
       this.scoreIndicator = new ScoreIndicator(this.score);
       // print the lives on the screen
       this.liveIndicator = new LivesIndicator(this.numOfLives);
       allTheBlocks = new ArrayList<Block>();
       this.aliensBlocks = new ArrayList<Block>();

       this.remainedBlocks = new Counter(this.level.numberOfBlocksToRemove());
       this.blockListener = new BlockRemover(this, this.remainedBlocks);
       this.scoreListener = new ScoreTrackingListener(this.score);

       // add the aliens to a list
       this.aliensBlocks.addAll(this.level.blocks());
       for (int i = 0; i < this.aliensBlocks.size(); i++) {
           this.aliensBlocks.get(i).addHitListener(this.blockListener);
           this.aliensBlocks.get(i).addHitListener(this.scoreListener);
           this.aliensBlocks.get(i).addHitListener(this.ballListener);
           this.addCollidable(this.aliensBlocks.get(i));
           this.addSprite(this.aliensBlocks.get(i));
       }
       // create a variable of Aliens type
       aliens = new Aliens(this.aliensBlocks, this, this.gameSpeed);
       // add the aliens to the game
       aliens.addToGame(this);

       // add boundaries blocks
       allTheBlocks.addAll(createBoundariesBlocks());

       // add the shields blocks
       allTheBlocks.addAll(createShieldsBlocks());

       // add all the blocks to the game
       for (int i = 0; i < allTheBlocks.size(); i++) {
           this.addSprite(allTheBlocks.get(i));
           this.addCollidable(allTheBlocks.get(i));
           allTheBlocks.get(i).addHitListener(this.ballListener);
       }

       // add the ScoreIndicator to the game
       this.scoreIndicator.addToGame(this);
       // add the LivesIndicator to the game
       this.liveIndicator.addToGame(this);
       this.levelName = new LevelName(this.level.levelName() + this.numberOfLevel);
       this.levelName.addToGame(this);
   }

   /**
    * create ball that the alien shoot.
    * @param alien - a block of alien
    */
   public void createShootBall(Block alien) {
       double x, y, halfWidth, height;
       Ball newBall;
       halfWidth = alien.getCollisionRectangle().getWidth() / 2;
       height = alien.getCollisionRectangle().getHeight() + 1;
       if (this.countShootBalls > 50) {
           x = alien.getCollisionRectangle().getUpperLeft().getX() + halfWidth;
           y = alien.getCollisionRectangle().getUpperLeft().getY() + height + 1;
              newBall = new Ball(new Point(x, y), BALLS_RADIUS, Color.RED, false);
           newBall.setVelocity(Velocity.fromAngleAndSpeed(180, 500));
           newBall.addToGame(this);
           newBall.setEnvironment(this.environment);
           this.balls.add(newBall);
           this.countShootBalls = 0;
       }
       this.countShootBalls++;
   }

   /**
    * create the paddle.
    */
   public void createPaddle() {
       int width, height;
       width = this.runner.getGui().getDrawSurface().getWidth();
       height = this.runner.getGui().getDrawSurface().getHeight();

       Point upperLeft;
       // create the paddle
       Map<String, Color> colorPaddle = new TreeMap<String, Color>();
       Map<String, Image> imagePaddle = new TreeMap<String, Image>();
       colorPaddle.put("0", new Color(255, 255, 153));
       upperLeft = new Point(width / 2 - (this.level.paddleWidth() / 2), height - BOUNDARIES_SIZE - PADDLE_HEIGHT);
       Rectangle rec = new Rectangle(upperLeft, this.level.paddleWidth(), PADDLE_HEIGHT, colorPaddle,
               imagePaddle, Color.BLACK);
       this.paddle = new Paddle(rec, width - BOUNDARIES_SIZE, this.level.paddleSpeed(), this.keyboard, this);
       // add the paddle to the game
       paddle.addToGame(this);
   }

   /**
    * Run the game -- start the animation loop.
    */
   public void playOneTurn() {
       for (int i = 0; i < this.balls.size(); i++) {
           this.balls.get(i).removeFromGame(this);
       }

       // create the paddle
       this.createPaddle();
       this.remainedBalls.increase(1);
       this.ballListener = new BallRemover(this, this.remainedBalls);

       // countdown before turn starts
       this.runner.run(new CountdownAnimation(2, 3, this.sprites));
       this.running = true;
       this.runner.run(this);
       // remove the paddle from the game
       this.removeCollidable(paddle);
       this.removeSprite(paddle);
    }

    /**
     * draw all the sprites.
     * the game stop when there are no more blocks or the paddle hitted
     * or the aliens arrived to the block shields.
     * @param d - the draw surface
     * @param dt - frames per second
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.aliens.setAliens(this.aliensBlocks);
        // draw all the sprites
        this.level.getBackground().drawOn(d);
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);

        // when there are no more blocks the player gets another 100 points and the game will stop
        if (this.remainedBlocks.getValue() == 0) {
              this.score.increase(100);
              // remove all the blocks from the game and their listeners
              for (int i = 0; i < this.allTheBlocks.size(); i++) {
                  this.allTheBlocks.get(i).removeFromGame(this);
                  this.allTheBlocks.get(i).removeHitListener(blockListener);
                  this.allTheBlocks.get(i).removeHitListener(ballListener);
                  this.allTheBlocks.get(i).removeHitListener(scoreListener);
              }
              this.running = false;
        }
        // the game stop when the paddle hitted or the aliens arrived to the block shields.
        if (this.paddle.getPaddleHit() || this.aliens.getCheckLose()) {
            this.running = false;
            this.paddle.removeFromGame(this);
            this.aliens.moveToTheStart();
        }

        // pause the game when pressing the p key
        if (this.keyboard.isPressed("p")) {
            //this.runner.run(new PauseScreen(this.keyboard));
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard,
                    KeyboardSensor.SPACE_KEY, new PauseScreen()));
        }

        Ball newBall;
        double x, y, halfWidth, height;
        halfWidth = this.paddle.getCollisionRectangle().getWidth() / 2;
        height = this.paddle.getCollisionRectangle().getHeight() + 1;
        // every time that the space key is pressed a ball shoot ball from the paddle (every 35 seconds)
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY) && countPaddleShoot > 35) {
            x = this.paddle.getCollisionRectangle().getUpperLeft().getX() + halfWidth;
            y = this.paddle.getCollisionRectangle().getUpperLeft().getY() - height;
            newBall = new Ball(new Point(x, y), BALLS_RADIUS, Color.WHITE, true);
            newBall.setVelocity(Velocity.fromAngleAndSpeed(0, 500));
            // add the ball to the game
            newBall.addToGame(this);
            newBall.setEnvironment(this.environment);
            this.balls.add(newBall);
            countPaddleShoot = 0;
        }
        countPaddleShoot++;
    }

    /**
     * change a boolean variable when we want to stop the animation.
     * @return true or false
     */
    public boolean shouldStop() {
        return !this.running;
    }
}