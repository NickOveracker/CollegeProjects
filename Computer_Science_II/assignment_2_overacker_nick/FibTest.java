/**
 * Tests two methods of obtaining Fibonacci numbers.
 *
 * Overacker, Nick
 * Computer Science 2
 * Spring 2013
 * Assignment 2.2
 */

public class FibTest
{
   public static void main(String[] args)
   {
      int test = fibIter(3);

      if(test == 2)
      {
         System.out.println("fibIter: Test one passed!");
      }
      else
      {
         System.out.println("fibIter: Test one failed!");
      }

      test = fibIter(5);

      if(test == 5)
      {
         System.out.println("fibIter: Test two passed!");
      }
      else
      {
         System.out.println("fibIter: Test two failed!");
      }

      test = fibRecur(3);

      if(test == 2)
      {
         System.out.println("fibRecur: Test one passed!");
      }
      else
      {
         System.out.println("fibRecur: Test one failed!");
      }

      test = fibRecur(5);

      if(test == 5)
      {
         System.out.println("fibRecur: Test two passed!");
      }
      else
      {
         System.out.println("fibRecur: Test two failed!");
      }

      if(fibIter(40) == fibRecur(40))
      {
         System.out.println("Consistency check passed!");
      }
      else
      {
         System.out.println("Consistency check failed!");
      }

      long startTime = System.currentTimeMillis();
      fibIter(40);
      long endTime = System.currentTimeMillis();

      System.out.println("It took " + (endTime - startTime)
            + " milliseconds to find the fortieth Fibonacci number"
            + " iteratively.");

      startTime = System.currentTimeMillis();
      fibRecur(40);
      endTime = System.currentTimeMillis();

      System.out.println("It took " + (endTime - startTime)
            + " milliseconds to find the fortieth Fibonacci number"
            + " recursively.");
   } // end main

   /**
    * Calculates the nth Fibonacci number with an iterative loop.
    *
    * @param n The Fibonacci number to calculate (first, second, etc).
    * @return The nth Fibonacci number.
    */
   public static int fibIter(int n)
   {
      if(n == 1 || n == 2)
      {
         return 1;
      }

      int fibNMinusTwo = 1;
      int fibNMinusOne = 1;
      int fibN         = 1; // the loop assumes that the last calculated
                            // Fibonacci number was 1.

      // Start the loop with i = 3, since the cases of the first
      // and second Fibonacci numbers have been accounted for.
      for(int i = 3; i <= n; i++)
      {
         int temp = fibN;
         fibNMinusTwo = fibNMinusOne;
         fibNMinusOne = temp;
         fibN = fibNMinusOne + fibNMinusTwo;
      }

      return fibN;
   } // end fibIter()

   /**
    * Calculates the nth Fibonacci recursively.
    *
    * @param n The Fibonacci number to calculate (first, second, etc).
    * @return The nth Fibonacci number.
    */
   public static int fibRecur(int n)
   {
      if(n == 1 || n == 2)
      {
         return 1;
      }

      return fibRecur(n-1) + fibRecur(n-2);
   }

} // end FibTest
