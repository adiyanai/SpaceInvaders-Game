package graphic;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;
import game.CollisionInfo;
import game.GameEnvironment;
import game.GameLevel;
import game.HitListener;
import game.HitNotifier;
import game.Sprite;
import geometry.Line;
import geometry.Point;
import geometry.Velocity;
/**
 * This class present a ball who composed by center point,
 * radius, color, velocity, screen boundaries and environment.
 * @author Adi Yanai
 *
 */
public class Ball implements Sprite, HitNotifier {
   private Point center;
   private int size;
   private java.awt.Color color;
   private Velocity v;
   private Point leftEdge;
   private Point rightEdge;
   private GameEnvironment environment;
   private List<HitListener> hitListeners = new ArrayList<>();
   private boolean check;

   /**
    * constructor.
    * @param center - the center point of the ball
    * @param r - the radius of the ball
    * @param color - the color of the ball
    * @param check - a check of boolean type
    */
   public Ball(Point center, int r, java.awt.Color color, boolean check) {
       this.center = center;
       this.size = r;
       this.color = color;
       this.check = check;
   }

   /**
    * return check, that represent if the ball is a type who kill alien.
    * @return the check
    */
   public boolean checkBall() {
       return this.check;
   }

   /**
    * Return the x of the center point of the ball.
    * @return the x of the center point of the ball
    */
   public int getX() {
       return (int) this.center.getX();
   }

   /**
    * Return the y of the center point of the ball.
    * @return the y of the center point of the ball
    */
   public int getY() {
       return (int) this.center.getY();
   }

   /**
    * Return the radius of the ball.
    * @return the radius of the ball
    */
   public int getSize() {
       return this.size;
   }

   /**
    * Return the color of the ball.
    * @return the color of the ball
    */
   public java.awt.Color getColor() {
       return this.color;
   }

   /**
    * constructor.
    * @param pL - the left edge point
    */
   public void setLeftEdge(Point pL) {
       this.leftEdge = pL;
   }

   /**
    * constructor.
    * @param pR - the right edge point
    */
   public void setRightEdge(Point pR) {
       this.rightEdge = pR;
   }

   /**
    * Return the left edge point.
    * @return the left edge point
    */
   public Point getLeftEdge() {
       return this.leftEdge;
   }

   /**
    * Return the right edge point.
    * @return the right edge point
    */
   public Point getRightEdge() {
       return this.rightEdge;
   }

   /**
    * draw the ball on the given DrawSurface.
    * @param surface - the draw surface
    */
   public void drawOn(DrawSurface surface) {
       surface.setColor(this.getColor());
       surface.fillCircle((int) this.getX(), (int) this.getY(), this.getSize());
       surface.setColor(Color.BLACK);
       surface.drawCircle((int) this.getX(), (int) this.getY(), this.getSize());
   }

   /**
    * constructor.
    * @param velocity - a velocity
    */
   public void setVelocity(Velocity velocity) {
       this.v = velocity;
   }

   /**
    * constructor.
    * @param dx - the angle - the direction of the ball
    * @param dy - the speed of the ball
    */
   public void setVelocity(double dx, double dy) {
       this.v = new Velocity(dx, dy);
   }

   /**
    * Return the velocity of the ball.
    * @return the velocity of the ball
    */
   public Velocity getVelocity() {
       return this.v;
   }

   /**
    * constructor.
    * @param e - an array of collidables
    */
   public void setEnvironment(GameEnvironment e) {
       this.environment = e;
   }

   /**
    * moveOneStep -- move the ball one step every time,
    * and keep the ball does not go outside of the screen.
    * @param dt - frames per second
    */
   public void moveOneStep(double dt) {
       Line trajectory;
       Velocity newV, dtVelocity;
       CollisionInfo c;
       double xNew, yNew;
       Point pStart, pEnd;
       // the center point of the ball
       pStart = this.center;
       dtVelocity = new Velocity(this.v.getDx() * dt, this.v.getDy() * dt);
       // the new point of the center of the ball
       xNew = dtVelocity.applyToPoint(this.center).getX();
       yNew = dtVelocity.applyToPoint(this.center).getY();
       pEnd = new Point(xNew, yNew);
       // the trajectory line
       trajectory = new Line(pStart, pEnd);
       c = this.environment.getClosestCollision(trajectory);

       // check if moving on this trajectory will hit anything
       if (c == null) {
           // move the ball to the end of the trajectory
           this.center = dtVelocity.applyToPoint(this.center);
       } else {
           // the information about the collidable object involved in the collision
           c = this.environment.getClosestCollision(trajectory);
           // check if the ball inside the block, and get him out the block
           if (c.collisionObject().getCollisionRectangle().insideTheRectangle(this.center)) {
               this.center = new Point(this.center.getX(),
                             this.center.getY() - c.collisionObject().getCollisionRectangle().getHeight());
               return;
           }
           // move the ball to be very close to the collision point
           this.center = new Velocity(c.collisionPoint().getX() - this.center.getX() - this.v.getDx() / 1000,
                         c.collisionPoint().getY() - this.center.getY() - this.v.getDy() / 1000)
                         .applyToPoint(this.center);
           // the new velocity of the ball
           newV = c.collisionObject().hit(this, c.collisionPoint(), this.v);
           this.setVelocity(newV);
       }
   }

   /**
    * the ball move one step.
    * @param dt - frames per second
    */
   public void timePassed(double dt) {
       this.moveOneStep(dt);
   }

   /**
    * add the ball to the game.
    * @param g - the game
    */
   public void addToGame(GameLevel g) {
       g.addSprite(this);
   }

   /**
    * remove this ball from the game.
    * @param g - the game
    */
   public void removeFromGame(GameLevel g) {
       g.removeSprite(this);
   }

   /**
    * Add hl as a listener to hit events.
    * @param hl - a HitListener
    */
   public void addHitListener(HitListener hl) {
       this.hitListeners.add(hl);
   }

   /**
    * Remove hl from the list of listeners to hit events.
    * @param hl - a HitListener
    */
   public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
   }
}
