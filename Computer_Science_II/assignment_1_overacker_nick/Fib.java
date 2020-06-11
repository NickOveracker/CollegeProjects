/**
 * Prints the nth Fibonacci number for any integer n.
 *
 * Some people consider 0 to be a Fibonacci number, so
 * I have included 0 as the zeroth Fibonacci number.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 1.3
 */

public class Fib
{
   public static void main(String[] args)
   {
      int n;

      // Check the command line argument for input.
      if(args.length > 0 && consistsOfDigits(args[0]))
      {
         n = Integer.parseInt(args[0]);

         System.out.println(fibonacciNumber(n));
      }
      else
      {
         System.out.println("USAGE  : java Fib <positive integer>\n"
                          + "EXAMPLE: java Fib 10");
      }

   }  // end main

   /**
    * Checks whether or not an input String consists of digits.
    *
    * @param input String to be tested.
    * @return true if input is all digits, false if not.
    */
   public static boolean consistsOfDigits(String input)
   {
      boolean isDigits = true;

      for(int i = 0; i < input.length(); i++)
      {
         if(!Character.isDigit(input.charAt(i)))
         {
            isDigits = false;
            break;
         }
      }

      return isDigits;
   }  // end consistsOfDigits

   /**
    * Computes the nth Fibonacci number.
    *
    * @param n The position of the Fibonacci number to be computed.
    * @return  The nth Fibonacci number.
    */
   public static long fibonacciNumber(int n)
   {
      long numOne    = 1;
      long numTwo    = 1;
      long fibNumber = 0;

      // Boundary case check
      if(n == 0)
      {
         fibNumber = 0;
      }
      if(n == 1 || n == 2)
      {
         fibNumber = 1;
      }

      // Overflow check
      // Cannot calculate numbers beyond Fibonacci(92).
      // I will just give the user a warning beyond that point.
      if(n > 92)
      {
         System.out.println("OVERFLOW WARNING");
      }

      // Here we compute all others. We start at i=3 since the first and
      // second numbers are accounted for.
      for(int i = 3; i <= n; i++)
      {
         // Actual computation
         fibNumber =  numOne + numTwo;

         // Bookkeeping - prepare numOne and numTwo for the next number.
         numOne = numTwo;
         numTwo = fibNumber;
      }

      return fibNumber;
   }  // end fibonacciNumber

}  // end Fib.java
