package geometry;
/**
 * This class present a Point who composed by x and y.
 * @author Adi Yanai
 *
 */
public class Point {
   private double x;
   private double y;

   /**
    * constructor of the point.
    * @param x - a x
    * @param y - a y
    */
   public Point(double x, double y) {
     this.x = x;
     this.y = y;
   }

   /**
    * distance -- return the distance of this point to the other point.
    * @param other - a Point
    * @return dist - the distance between two points
    */
   public double distance(Point other) {
       double dist;
       dist = Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
       return dist;
   }

   /**
    * equals -- return true is the points are equal, false otherwise.
    * @param other - a Point
    * @return false or true
    */
   public boolean equals(Point other) {
       if ((this.x == other.x) && (this.y == other.y)) {
           return true;
       }
       return false;
   }

   /**
    * Return the x value of this point.
    * @return the x value
    */
   public double getX() {
       return this.x;
   }

   /**
    * Return the y value of this point.
    * @return the y value
    */
   public double getY() {
       return this.y;
   }
}