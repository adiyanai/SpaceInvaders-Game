package geometry;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class present a rectangle who composed by upper left point, width, height and color.
 * @author Adi Yanai
 *
 */
public class Rectangle {
   private Point upperLeft; // the upper left point of the rectangle
   private Point saveStartPoint;
   private double width; // the width of the rectangle
   private double height; // the height of the rectangle
   private Map<String, Color> colors;
   private Map<String, Image> images;
   private Color stroke;

   /**
    * Create a new rectangle with location and width/height.
    * @param upperLeft - the upper left point of the rectangle
    * @param width - the width of the rectangle
    * @param height - the height of the rectangle
    * @param fillC - a map of colors
    * @param fillI - a map of images
    * @param stroke - a stroke color
    */
   public Rectangle(Point upperLeft, double width, double height, Map<String, Color> fillC,
           Map<String, Image> fillI, Color stroke) {
       this.upperLeft = upperLeft;
       this.saveStartPoint = upperLeft;
       this.width = width;
       this.height = height;
       this.colors = fillC;
       this.images = fillI;
       this.stroke = stroke;
   }

   /**
    * Return the first start point of the rectangle.
    * @return the first start point of the rectangle.
    */
   public Point getSaveStartPoint() {
       return this.saveStartPoint;
   }

   /**
    * Return the width of the rectangle.
    * @return the width of the rectangle
    */
   public double getWidth() {
       return this.width;
   }

   /**
    * Return the height of the rectangle.
    * @return the height of the rectangle
    */
   public double getHeight() {
       return this.height;
   }

   /**
    * Returns the upper-left point of the rectangle.
    * @return the upper-left point of the rectangle
    */
   public Point getUpperLeft() {
       return this.upperLeft;
   }

   /**
    * set the upper left of the rectangle.
    * @param p - the point of the upper left of the rectangle
    */
   public void setUpperLeft(Point p) {
       this.upperLeft = p;
   }

   /**
    * Returns the colors of the rectangle.
    * @return - the colors of the rectangle
    */
   public Map<String, Color> getColors() {
       return this.colors;
   }

   /**
    * Returns the images of the rectangle.
    * @return - the images of the rectangle
    */
   public Map<String, Image> getImages() {
       return this.images;
   }

   /**
    * returns the stroke color.
    * @return the stroke color
    */
   public Color getStroke() {
       return this.stroke;
   }

   /**
    * Return a (possibly empty) List of intersection points with the specified line.
    * @param line - a line to check intersection with the rectangle
    * @return - a List of intersection points with the specified line
    */
   public java.util.List<Point> intersectionPoints(Line line) {
       List<Point> intersectionP = new ArrayList<Point>();
       // create all the endpoints of the rectangle
       Point upperRight = new Point(this.getUpperLeft().getX() + this.getWidth(), this.getUpperLeft().getY());
       Point lowerLeft = new Point(this.getUpperLeft().getX(), this.getUpperLeft().getY() + this.getHeight());
       Point lowerRight = new Point(this.getUpperLeft().getX() + this.getWidth(),
                                    this.getUpperLeft().getY() + this.getHeight());
       // create all the lines of the rectangle
       Line upperLine = new Line(this.getUpperLeft(), upperRight);
       Line lowerLine = new Line(lowerLeft, lowerRight);
       Line leftLine = new Line(this.getUpperLeft(), lowerLeft);
       Line rightLine = new Line(upperRight, lowerRight);

       // check if the line intersects with the upper line of the rectangle
       if (upperLine.isIntersecting(line)) {
           intersectionP.add(upperLine.intersectionWith(line));
       }
       // check if the line intersects with the lower line of the rectangle
       if (lowerLine.isIntersecting(line)) {
           intersectionP.add(lowerLine.intersectionWith(line));
       }
       // check if the line intersects with the left line of the rectangle
       if (leftLine.isIntersecting(line)) {
           intersectionP.add(leftLine.intersectionWith(line));
       }
       // check if the line intersects with the right line of the rectangle
       if (rightLine.isIntersecting(line)) {
           intersectionP.add(rightLine.intersectionWith(line));
       }

       return intersectionP;
   }

   /**
    * check if the ball is inside the block.
    * @param center - the center point of the ball
    * @return - true if the ball inside the block, otherwise false
    */
   public boolean insideTheRectangle(Point center) {
       // check if the center point of the ball in the block
       if ((center.getX() > this.getUpperLeft().getX()) && (center.getX() < this.getUpperLeft().getX() + this.width)
            && (center.getY() >= this.getUpperLeft().getY())
            && (center.getY() <= this.getUpperLeft().getY() + this.height)) {
            return true;
       }
       return false;
   }
}