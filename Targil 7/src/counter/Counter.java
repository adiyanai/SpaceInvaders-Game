package counter;
/**
 * Count numbers, by increase or decrease them.
 * @author Adi Yanai
 *
 */
public class Counter {
   private int numCount;

   /**
    * Constructor.
    * @param numCounter - a number
    */
   public Counter(int numCounter) {
       this.numCount = numCounter;
   }

   /**
    * add number to current count.
    * @param number - a number that added to the current number
    */
   public void increase(int number) {
       this.numCount += number;
   }

   /**
    * subtract number from current count.
    * @param number - a number that decreased from the current number
    */
   public void decrease(int number) {
       if (this.numCount != 0) {
           this.numCount -= number;
       }
   }

   /**
    * get current count.
    * @return current number
    */
   public int getValue() {
       return this.numCount;
   }
}