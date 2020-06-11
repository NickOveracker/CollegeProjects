/**
 * Approximates pi with a user-specified number of terms
 * in the Gregory series.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 1.4
 */ 

public class Gregory
{
   public static void main(String[] args)
   {
      // Make sure we have input.
      if(args.length > 0)
      {
         // Make sure the input is valid
         if(consistsOfDigits(args[0]))
         {
            // Get the number of terms for the Gregory series
            int terms = Integer.parseInt(args[0]);

            // Get our approximation of pi.
            double gregoryApproximation = computePi(terms);

            System.out.println("Pi according to Gregory series: "
                  + gregoryApproximation);
            System.out.println("This differs from Java's value by "
                  + percentageError(Math.PI, gregoryApproximation)
                  + " percent.");

            // If we don't end here, the error message will print.
            System.exit(0);
         }  // end if
      }  // end if

      // If we get here, that means that the input is invalid.
      System.out.println("USAGE  : java Gregory <positive integer>\n"
                       + "EXAMPLE: java Gregory 10");
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
    * Computes pi using a specified number of terms in the Gregory series.
    *
    * @param terms The number of terms of the Gregory series to use.
    * @return An approximation of pi.
    */
   public static double computePi(int terms)
   {
      double piFourths = 0;

      for(int i = 1; i <= terms; i++)
      {
         piFourths += Math.pow(-1, (i+1))/(2*i-1);
      }

      return piFourths * 4;
   }  // end computePi

   /**
    * Calculates the percentage error given the actual value and the obtained
    * value of some quantity.
    *
    * @param actualValue The known value of a quantity.
    * @param obtainedValue The value obtained by experiment or approximation.
    * @return The percentage error in the obtained value.
    */
   public static double percentageError(double actualValue,
                                        double obtainedValue)
   {
      return Math.abs(actualValue - obtainedValue)
             / Math.abs(actualValue) * 100;
   }

}  // end Gregory.java
