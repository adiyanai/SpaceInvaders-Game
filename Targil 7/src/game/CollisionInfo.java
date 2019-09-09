package game;
import geometry.Point;
/**
 * The CollisionInfo holds a collision point and the object that belong to her.
 * @author Adi Yanai
 *
 */
public class CollisionInfo {
   private Point collisionPoint;
   private Collidable collisionObject;

   /**
    * constructor.
    * @param collisionPoint - the point at which the collision occurs.
    * @param collisionObject - the collidable object involved in the collision.
    */
   public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
       this.collisionPoint = collisionPoint;
       this.collisionObject = collisionObject;
   }

   /**
    * the point at which the collision occurs.
    * @return the collision Point
    */
   public Point collisionPoint() {
       return this.collisionPoint;
   }

   /**
    * the collidable object involved in the collision.
    * @return the collidable object
    */
   public Collidable collisionObject() {
       return this.collisionObject;
   }
}