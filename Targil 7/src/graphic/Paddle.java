package graphic;
import java.awt.Color;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.Collidable;
import game.GameLevel;
import game.Sprite;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
/**
 * The Paddle is the player in the game.
 * It is a rectangle that is controlled by the arrow keys, and moves according to the player key presses.
 * @author Adi Yanai
 *
 */
public class Paddle implements Sprite, Collidable {
   // the width of the left&right block boundaries and the height of the up&down block boundaries
   public static final int BOUNDARIES_SIZE = 10;
   private int paddleSpeed;
   private Rectangle rec;
   private double width;
   private biuoop.KeyboardSensor keyboard;
   private GameLevel game;
   private boolean paddleHit;

   /**
    * Constructor of the paddle.
    * @param paddleSpeed - the speed of the paddle
    * @param rec - the rectangle of the paddle
    * @param width - the width of the screen
    * @param keyboard - the keyboard of the game
    * @param game - a game level
    */
   public Paddle(Rectangle rec, double width, int paddleSpeed, biuoop.KeyboardSensor keyboard, GameLevel game) {
       this.rec = rec;
       this.width = width;
       this.paddleSpeed = paddleSpeed;
       this.keyboard = keyboard;
       this.game = game;
       this.paddleHit = false;
   }

   /**
    * return the paddleHit.
    * if the paddle hitted its true, else its false.
    * @return the paddleHit
    */
   public boolean getPaddleHit() {
       return this.paddleHit;
   }

   /**
    * move the paddle to the left.
    * @param dt - frames per second
    */
   public void moveLeft(double dt) {
       // check that the paddle doesn't pass the left limit of the screen
       if (this.rec.getUpperLeft().getX() - BOUNDARIES_SIZE - dt * (double) this.paddleSpeed >= 0) {
           // move the paddle one step to the left
           Point newP = new Point(this.rec.getUpperLeft().getX() - dt * (double) this.paddleSpeed,
                   this.rec.getUpperLeft().getY());
           this.rec.setUpperLeft(newP);
       }
   }

   /**
    * move the paddle to the right.
    * @param dt - frames per second
    */
   public void moveRight(double dt) {
       // check that the paddle doesn't pass the right limit of the screen
       if (this.rec.getUpperLeft().getX() + this.rec.getWidth() + dt * (double) this.paddleSpeed  <= this.width) {
           // move the paddle one step to the right
           Point newP = new Point(this.rec.getUpperLeft().getX() + dt * (double) this.paddleSpeed,
                   this.rec.getUpperLeft().getY());
           this.rec.setUpperLeft(newP);
       }
   }

   /**
    * call the methods that move the paddle.
    * @param dt - frames per second
    */
   public void timePassed(double dt) {
       // check if the player presses the left key
       if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
           moveLeft(dt);
       }
       // check if the player presses the right key
       if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
          moveRight(dt);
       }
   }

   /**
    * draw the paddle on the draw surface.
    * @param d - the draw surface
    */
   public void drawOn(DrawSurface d) {
       int x, y, w, h;
       // the upper left point of the rectangle
       x = (int) this.rec.getUpperLeft().getX();
       y = (int) this.rec.getUpperLeft().getY();
       // the width of the rectangle
       w = (int) this.rec.getWidth();
       // the height of the rectangle
       h = (int) this.rec.getHeight();
       // draw the rectangle
       d.setColor(this.rec.getColors().get("0"));
       d.fillRectangle(x, y, w, h);
       d.setColor(Color.BLACK);
       d.drawRectangle(x, y, w, h);
   }

   /**
    * return the rectangle of the paddle.
    * @return the collision rectangle
    */
   public Rectangle getCollisionRectangle() {
       return this.rec;
   }

   /**
    * The paddle having 5 equally-spaced regions, each region has different return angle.
    * The behavior of the ball's bounce depends on where it hits the paddle.
    * (changes his velocity according to the new angle that every region has).
    * @param collisionPoint - the collision point of the ball with the paddle
    * @param currentVelocity - the current velocity of the ball
    * @param hitter - the ball that hits the block
    * @return the new velocity of the ball
    */
   public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
       this.paddleHit = true;
       this.removeFromGame(this.game);
       hitter.removeFromGame(this.game);
       return currentVelocity;
   }

   /**
    * Add this paddle to the game.
    * @param g - the game
    */
   public void addToGame(GameLevel g) {
       g.addSprite(this);
       g.addCollidable(this);
   }

   /**
    * remove the paddle from the game.
    * @param g - a game level
    */
   public void removeFromGame(GameLevel g) {
       g.removeCollidable(this);
       g.removeSprite(this);
   }
}