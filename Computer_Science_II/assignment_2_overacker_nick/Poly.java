/**
 * A class for representing polynomials as objects.
 *
 * Overacker, Nick
 * Computer Science 2
 * Spring 2013
 * Assignment 2.5
 */

public class Poly
{
   // An array of coefficients, in which coefficients[n] is the coefficient
   // of x^n. For example, 2x^5+3x^4-8x^2+4 would be represented by the array
   // [4, 0, -8, 0, 3, 2].
   private int[] coefficients;

   /**
    * Runs a series of tests on the Poly class.
    */
   public static void main(String[] args)
   {
      System.out.print("Constructing polynomial p1 = 2x^5+3x^4-8x^2+4... ");
      int[] a1 = {4, 0, -8, 0, 3, 2};
      Poly p1 = new Poly(a1);
      System.out.println("Done!");

      System.out.print("Constructing polynomial p2 = x^3+4x^2-2x... ");
      int[] a2 = {0, -2, 4, 1};
      Poly p2 = new Poly(a2);
      System.out.println("Done!");
      
      System.out.print("Testing p1.toString()... ");
      String s1 = p1.toString();
      if(s1.equals("2x^5+3x^4-8x^2+4"))
      {
         System.out.println("Passed!");
      }
      else
      {
         System.out.println("Failed!");
      }

      System.out.print("Testing p2.toString()... ");
      String s2 = p2.toString();
      if(s2.equals("x^3+4x^2-2x"))
      {
         System.out.println("Passed!");
      }
      else
      {
         System.out.println("Failed!");
      }

      System.out.print("Testing p1.evaluate(2.0)... ");
      if(p1.evaluate(2.0) == 84)
      {
         System.out.println("Passed!");
      }
      else
      {
         System.out.println("Failed!");
      }

      System.out.print("Testing p1.add(p2)... ");
      Poly p3 = p1.add(p2);
      if(p3.toString().equals("2x^5+3x^4+x^3-4x^2-2x+4"))
      {
         System.out.println("Passed!");
      }
      else
      {
         System.out.println("Failed!");
      }

      System.out.print("Testing p2.add(p1)... ");
      Poly p4 = p2.add(p1);
      if(p4.toString().equals("2x^5+3x^4+x^3-4x^2-2x+4"))
      {
         System.out.println("Passed!");
      }
      else
      {
         System.out.println("Failed!");
      }

   } // end main()


   /**
    * Construct a Poly object to represent a polynomial.
    *
    * @param _coefficients An array of coefficients in which c[n] is the
    *                      coefficient of x^n.
    */
   public Poly(int[] _coefficients)
   {
      coefficients = _coefficients;
   } // end constructor


   /**
    * Returns the degree of the highest non-zero term in the polynomial.
    *
    * @return The degree of the highest non-zero term in the polynomial.
    */
   public int degree()
   {
      for(int i = coefficients.length - 1; i >= 0; i--)
      {
         if(coefficients[i] != 0)
         {
            return i;
         }
      }

      // Return zero if all coefficients are zero.
      return 0;
   } // end degree()


   /**
    * Returns a String representing the Poly object as a polynomial.
    *
    * @return The polynomial represented by <code>this</code> as a String.
    */
   public String toString()
   {
      String polynomial = "";

      for(int i = coefficients.length - 1; i >= 0; i--)
      {
         if(coefficients[i] != 0)
         {
            // If this is not the first term, and if it is positive,
            // add a + sign.
            if(i != degree() && coefficients[i] > 0)
            {
               polynomial += "+";
            }

            // Add the coefficient if it is not 1.
            if(coefficients[i] != 1)
            {
               // Special case: If it is -1, we should just get the - sign.
               if(coefficients[i] == -1)
               {
                   polynomial += "-";
               }
               else
               {
                   polynomial += coefficients[i];
               }
            }

            // Add x if the exponent is greater than 0.
            if(i > 0)
            {
               polynomial += "x";
            }

            // Add the exponent if it is greater than 1.
            if(i > 1)
            {
               polynomial += "^" + i;
            }
         } // end if
      } // end for

      return polynomial;
   } // end toString()

   
   /**
    * Adds two Poly objects with regular polynomial addition.
    *
    * @param a The Poly object to add to <code>this</code>.
    * @return The sum of <code>this</code> and <code>a</code>.
    */
   public Poly add(Poly a)
   {
      // To simplify the addition loop, I would like to assume that
      // this.degree() >= a.degree(). If not, call this method recursively
      // with the parameters reversed.
      if(a.degree() > this.degree())
      {
         return a.add(this);
      }

      // Build the new array here.
      int[] sum = new int[coefficients.length];

      // Add the coefficients, starting at the end.
      for(int i = sum.length - 1; i >= 0; i--)
      {
         if(i < a.coefficients.length)
         {
            sum[i] = coefficients[i] + a.coefficients[i];
         }
         else
         {
            sum[i] = coefficients[i];
         }
      }

      return new Poly(sum);
   } // end add()

   
   /**
    * Evaluates the polynomial at point x.
    *
    * @param x The point at which to evaluate the polynomial.
    * @return The polynomial evaluated at point x.
    */
   public double evaluate(double x)
   {
      double y = 0;

      for(int i = 0; i < coefficients.length; i++)
      {
         y += coefficients[i] * Math.pow(x, i);
      }

      return y;
   } // end evaluate()

} // end class Poly
