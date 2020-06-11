/**
 * Calculates the factorial of a user-specified number.
 *
 * Overacker, Nick
 * Computer Science 2
 * Spring 2013
 * Assignment 2.1
 */

import java.math.BigInteger;

public class Factorial
{

   /**
    * Runs tests on the calculate() method.
    */

   public static void main(String[] args)
   {
      BigInteger test0 = calculate(0);
      BigInteger test5 = calculate(5);

      System.out.print("Factorial.calculate(0) returned "
            + test0 + ".  Test");
      
      if(test0.equals(new BigInteger("1")))
      {
         System.out.println(" passed!");
      }
      else
      {
         System.out.println(" failed!");
      }

      System.out.print("Factorial.calculate(5) returned "
            + test5 + ".  Test");

      if(test5.equals(new BigInteger("120")))
      {
         System.out.println(" passed!");
      }
      else
      {
         System.out.println(" failed!");
      }
   } // end main()

   /**
    * Calculates the factorial of any integer.
    *
    * @param n The number for which to calculate the factorial.
    * @return The factorial of n as a BigInteger.
    */
   public static BigInteger calculate(int n)
   {
      BigInteger bigInt = new BigInteger(Integer.toString(n));

      if(n == 0)
      {
         return BigInteger.ONE;
      }
      
      return bigInt.multiply(calculate(n - 1));
   } // end calculate()

} // end Factorial
