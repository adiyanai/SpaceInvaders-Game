package geometry;
import java.util.ArrayList;
import java.util.List;
/**
 * This class present a Line who composed by two points, start point & end point.
 * @author Adi Yanai
 *
 */
public class Line {
    private Point start;
    private Point end;

    /**
     * constructor of the line.
     * create the start & end points of the line
     * @param start - the start point of the line
     * @param end - the end point of the line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * constructor of the start & end points of the line.
     * @param x1 - the x of the start point of the line
     * @param y1 - the y of the start point of the line
     * @param x2 - the x of the end point of the line
     * @param y2 - the y of the end point of the line
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Return the length of the line.
     * @return the length of the line
     */
    public double length() {
        return this.start.distance(end);
    }

    /**
     * Returns the middle point of the line.
     * @return mid - the middle point of the line
     */
    public Point middle() {
        Point mid = new Point((this.start.getX() + this.end.getX()) / 2, (this.start.getY() + this.end.getY()) / 2);
        return mid;
    }

    /**
     * Returns the start point of the line.
     * @return the start point of the line
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     * @return the end point of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * Returns true if the lines intersect, false otherwise.
     * @param other - a line
     * @return true or false
     */
    public boolean isIntersecting(Line other) {
         if (this.intersectionWith(other) == null) {
            return false;
        }
        return true;
    }

    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     * @param other - a line
     * @return the intersection point if the lines intersect, and null otherwise
     */
    public Point intersectionWith(Line other) {
        Point intersectP;
        double a1, a2, b1, b2, x1, x11, x2, x22, y1, y11, y2, y22;
        double xNew, yNew;
        x1 = this.start().getX(); //get x of the start point of line 1
        y1 = this.start().getY(); //get y of the start point of line 1
        x2 = other.start().getX(); //get x of the start point of line 2
        y2 = other.start().getY(); //get y of the start point of line 2
        x11 = this.end().getX(); //get x of the end point of line 1
        y11 = this.end().getY(); //get y of the end point of line 1
        x22 = other.end().getX(); //get x of the end point of line 2
        y22 = other.end().getY(); //get y of the end point of line 2

        // if the line doesn't equal to the other line
        if (!this.equals(other)) {
            // if the inclines of the two lines equal
            if ((y11 - y1) * (x22 - x2) == (y22 - y2) * (x11 - x1)) {
                // if the start point of one line equals to the end point of the other line
                if (this.start() == other.end()) {
                    return this.start();
                }
                if (this.end() == other.start()) {
                    return this.end();
                }
                // if the start point of one line equals to the start point of the other line
                if (this.start() == other.start()) {
                    return this.start();
                }
                // if the end point of one line equals to the end point of the other line
                if (this.end() == other.end()) {
                    return this.end();
                }
                return null;
              // if the inclines of the two lines don't equal
            } else {
                // if the start and the end points (of line 1) are equal
                if (this.start().getX() == this.end().getX()) {
                    // the x of the intersection point
                    xNew = this.start().getX();
                    // the incline
                    a2 = incline(other.start(), other.end());
                    b2 = y2 - (a2 * x2);
                    // the y of the intersection point
                    yNew = a2 * (xNew) + b2;
                  // if the start and the end points (of line 2) are equal
                } else if (other.start().getX() == other.end().getX()) {
                    // the x of the intersection point
                    xNew = other.start().getX();
                    // the incline
                    a1 = incline(this.start(), this.end());
                    b1 = y1 - (a1 * x1);
                    // the y of the intersection point
                    yNew = a1 * (xNew) + b1;
                } else {
                    // the incline of line 1
                    a1 = incline(this.start(), this.end());
                    // the incline of line 2
                    a2 = incline(other.start(), other.end());
                    b1 = y1 - (a1 * x1);
                    b2 = y2 - (a2 * x2);
                    // the x of the intersection point
                    xNew = (b2 - b1) / (a1 - a2);
                    // the y of the intersection point
                    yNew = a1 * (xNew) + b1;
                }

                // check if the intersection point on the lines (check x)
                if (((xNew >= x1 && xNew <= x11) || (xNew >= x11 && xNew <= x1))
                    && ((xNew >= x2 && xNew <= x22) || (xNew >= x22 && xNew <= x2))) {
                    // check if the intersection point on the lines (check y)
                    if (((yNew >= y1 && yNew <= y11) || (yNew >= y11 && yNew <= y1))
                       && ((yNew >= y2 && yNew <= y22) || (yNew >= y22 && yNew <= y2))) {
                        // the intersection point
                        intersectP = new Point(xNew, yNew);
                        return intersectP;
                    }
                }
            }
        }
        return null;
    }

    /**
     * equals -- return true is the lines are equal, false otherwise.
     * @param other - a line
     * @return true or false
     */
    public boolean equals(Line other) {
        // if the lines are equal
        if ((this.start.equals(other.start) && this.end.equals(other.end))
            || (this.start.equals(other.end) && this.end.equals(other.start))) {
            return true;
        }
        // if the lines are not equal
        return false;
    }

    /**
     * incline -- return the incline of the line.
     * @param startP - the start point of the line
     * @param endP - the end point of the line
     * @return - the incline of the line
     */
    public double incline(Point startP, Point endP) {
        // calculates the incline of the line
        double m = (startP.getY() - endP.getY()) / (startP.getX() - endP.getX());
        return m;
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of the line.
     * @param rect - a rectangle
     * @return null if the line does not intersect with the rectangle,
     *         otherwise return the closest intersection point to the start of the line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // list of the intersection points of the line with the rectangle
        List<Point> listP = new ArrayList<Point>(rect.intersectionPoints(this));
        Point closestPoint;
        double dis;

        // check if this line does not intersect with the rectangle
        if (listP.isEmpty()) {
             return null;
        }
        // the distance between the start point of the line to the first point in the list of intersection points
        dis = this.start().distance(listP.get(0));
        // the first point in the list of intersection points
        closestPoint = listP.get(0);
        // find the closest intersection point to the start of the line
        for (int i = 1; i < listP.size(); i++) {
            if (dis > this.start().distance(listP.get(i))) {
                dis = this.start().distance(listP.get(i));
                closestPoint = listP.get(i);
            }
        }
        return closestPoint;
    }

    /**
     * checks if the point that the method gets belong to this line.
     * @param p - a point
     * @return true if the point that the method gets belong to this line, else false
     */
    public boolean belongToTheLine(Point p) {
        double xStart, yStart, xEnd, yEnd;
        xStart = this.start.getX();
        xEnd = this.end.getX();
        yStart = this.start.getY();
        yEnd = this.end.getY();
        // check if the x of the point in the line
        if ((p.getX() >= xStart && p.getX() <= xEnd) || (p.getX() <= xStart && p.getX() >= xEnd)) {
            // check if the y of the point in the line
            if ((p.getY() >= yStart && p.getY() <= yEnd) || (p.getY() <= yStart && p.getY() >= yEnd)) {
                return true;
            }
        }
        return false;
    }
}