package game;
import java.util.List;
import geometry.Line;
import geometry.Point;
/**
 * GameEnvironment is a collection of many objects a Ball can collide with.
 * @author Adi Yanai
 *
 */
public class GameEnvironment {
    public static final int WIDTH_DRAW_SURFACE = 800; // the width of the drawSurface
    public static final int HEIGHT_DRAW_SURFACE = 600; // the height of the drawSurface
    private List<Collidable> environment;

    /**
     * add the given collidable to the environment.
     * @param c - a collidable
     */
    public void addCollidable(Collidable c) {
        this.environment.add(c);
    }

    /**
     * constructor of game environment.
     * @param e - list of collidables
     */
    public GameEnvironment(List<Collidable> e) {
        this.environment = e;
    }

    /**
     * Set the game environment with new list of collidables.
     * @param newL - a new list of collidables
     */
    public void setGameEnvironment(List<Collidable> newL) {
        this.environment = newL;
    }

    /**
     * return the environment (list of collidables).
     * @return the environment (list of collidables).
     */
    public List<Collidable> getList() {
        return this.environment;
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     * @param trajectory - the line that the object moving on
     * @return null if this object will not collide with any of the collidables in this collection.
     *         else, return the information about the closest collision that is going to occur.
     */
     public CollisionInfo getClosestCollision(Line trajectory) {
         boolean flag = false;
         CollisionInfo c;
         Collidable closestRec;
         Point closestPoint, temp;
         // closest collision Point that is going to occur
         closestPoint = new Point(WIDTH_DRAW_SURFACE, HEIGHT_DRAW_SURFACE);
         // check if there is no collidables at all
         if (this.environment.isEmpty()) {
              return null;
         }

         closestRec = this.environment.get(0);
         // goes over all the collidables in the environment
         for (int i = 0; i < this.environment.size(); i++) {
             // temporary closest point
             temp = trajectory.closestIntersectionToStartOfLine(this.environment.get(i).getCollisionRectangle());
             // if there is a collision point
             if (temp != null) {
                 // if the temporary closest point closest than the current point
                 if (temp.distance(trajectory.start()) <= closestPoint.distance(trajectory.start())) {
                     // mark that there is at list one intersecting point
                     flag = true;
                     // put the temp point to be the current
                     closestPoint = temp;
                     // save the rectangle that belong to that point
                     closestRec = this.environment.get(i);
                 }
             }
         }
         // check if there is no intersecting points at all
         if (!flag) {
             return null;
         }
         // the information about the closest collision that is going to occur
         c = new CollisionInfo(closestPoint, closestRec);
         return c;
     }
}