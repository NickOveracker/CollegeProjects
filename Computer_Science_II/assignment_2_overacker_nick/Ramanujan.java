/**
 * Approximates pi with a user-specified number of terms
 * in the Ramanujan series.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 2.3
 */ 

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class Ramanujan
{
   public static void main(String[] args)
   {
      // Make sure we have input.
      if(args.length > 0)
      {
         // Parse the input
         try
         {
            // Get the number of terms for the Ramanujan series
            int terms = Integer.parseInt(args[0]);

            // Get our approximation of pi.
            BigDecimal ramanujanApproximation = computePi(terms);

            System.out.println("Pi according to Ramanujan series: "
                  + ramanujanApproximation);
            System.out.println("This differs from Java's value by "
                  + percentageError(Math.PI, ramanujanApproximation.doubleValue())
                  + " percent.");

            // If we don't end here, the error message will print.
            System.exit(0);
         }  // end try
         catch(NumberFormatException e)
         {
            // Do nothing - let this fall through to the error message below.
         }
      }  // end if

      // If we get here, that means that the input is invalid.
      System.out.println("USAGE  : java Ramanujan <positive integer>\n"
                       + "EXAMPLE: java Ramanujan 10");
   }  // end main

   /**
    * Computes pi using a specified number of terms in the Ramanujan series.
    *
    * @param terms The number of terms of the Ramanujan series to use.
    * @return An approximation of pi as a BigDecimal object.
    */
   public static BigDecimal computePi(int terms)
   {
      // I cannot take square roots with BigDecimals, and doubles afford too
      // few significant figures. I will define a constant that offers all
      // the precision I need and more for the square root of two.
      BigDecimal ROOT_TWO =
         new BigDecimal("1.41421356237309504880168872420969807856967187537694807317667973799"
         + "07324784621070388503875343276415727350138462309122970249248360558507372126441214");
      
      // Set our precision to 100 decimal places.
      MathContext mc = new MathContext(150, RoundingMode.HALF_UP);

      // Build our approximation of 1/pi with these objects.
      BigDecimal oneOverPi = new BigDecimal(0, mc);
      BigDecimal augend;
      BigDecimal bdNumerator;
      BigDecimal bdDenominator;
      
      BigInteger numerator;
      BigInteger denominator;

      // Loop until all the terms have been completed.
      for(int k = 0; k < terms; k++)
      {
         // Build the numerator.
         // Pay no attention to the man behind the curtain.
         numerator = Factorial.calculate(4*k);
         numerator = numerator.multiply(BigInteger.valueOf(1103L).add(BigInteger.valueOf(26390L).multiply(BigInteger.valueOf(k))));

         // Build the denominator.
         // I told you to pay no attention to that man behind the curtain!
         denominator = Factorial.calculate(k).pow(4);
         denominator = denominator.multiply(BigInteger.valueOf(396L).pow(4*k));

         // The numerator and denominator must be converted to BigDecimal
         // for division.
         bdNumerator = new BigDecimal(numerator, mc);
         bdDenominator = new BigDecimal(denominator, mc);

         // Add the quotient to our approximation.
         augend = bdNumerator.divide(bdDenominator, mc);
         oneOverPi = oneOverPi.add(augend, mc);
      }

      // Multiply by the constant outside of the sum.
      // I have to do some trickery to make sure that no digits are lost.
      BigDecimal finalConstant = new BigDecimal(2, mc);
      finalConstant = finalConstant.multiply(ROOT_TWO, mc);
      finalConstant = finalConstant.divide(BigDecimal.valueOf(9801L), mc);

      oneOverPi = oneOverPi.multiply(finalConstant, mc);

      return BigDecimal.ONE.divide(oneOverPi, mc);
   }  // end computePi

   /**
    * Calculates the percentage error given the actual value and the obtained
    * value of some quantity.
    *
    * @param referenceValue The known value of a quantity.
    * @param obtainedValue The value obtained by experiment or approximation.
    * @return The percentage error in the obtained value.
    */
   public static double percentageError(double referenceValue,
                                        double obtainedValue)
   {
      return Math.abs(referenceValue - obtainedValue)
             / Math.abs(referenceValue) * 100;
   }

}  // end Ramanujan
