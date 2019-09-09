package game;
/**
 * Indicate that objects that implement it send notifications when they are being hit.
 * @author Adi Yanai
 *
 */
public interface HitNotifier {
   /**
    * Add hl as a listener to hit events.
    * @param hl - a HitListener
    */
   void addHitListener(HitListener hl);
   /**
    * Remove hl from the list of listeners to hit events.
    * @param hl - a HitListener
    */
   void removeHitListener(HitListener hl);
}