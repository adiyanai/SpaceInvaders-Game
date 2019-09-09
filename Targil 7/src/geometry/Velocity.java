package geometry;
/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 * @author Adi Yanai
 *
 */
public class Velocity {
   private double dx;
   private double dy;

   /**
    * constructor of the velocity.
    * @param dx - the change in the 'x' axes
    * @param dy - the change in the 'y' axes
    */
   public Velocity(double dx, double dy) {
       this.dx = dx;
       this.dy = dy;
   }

   /**
    * Get an angle and speed, calculate new dx & dy and return a new Velocity.
    * @param angle - a angle
    * @param speed - the speed of the ball
    * @return a new velocity
    */
   public static Velocity fromAngleAndSpeed(double angle, double speed) {
          double dx = speed * Math.sin(Math.toRadians(angle));
          double dy = speed * -Math.cos(Math.toRadians(angle));
          return new Velocity(dx, dy);
   }

   /**
    * Return the change in the 'x' axes of the ball.
    * @return the change in the 'x' axes
    */
   public double getDx() {
       return this.dx;
   }

   /**
    * Return the change in the 'y' axes of the ball.
    * @return the change in the 'y' axes
    */
   public double getDy() {
       return this.dy;
   }

   /**
    * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
    * @param p - the center point of the ball
    * @return the new position of the ball
    */
   public Point applyToPoint(Point p) {
       Point newP;
       newP = new Point(p.getX() + this.dx, p.getY() + this.dy);
       return newP;
   }
}