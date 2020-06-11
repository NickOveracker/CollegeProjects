/**
 * Tests a recursive method for finding the root between
 * two points in sin.
 *
 * Overacker, Nick
 * Computer Science 2
 * Spring 2013
 * Assignment 2.4
 */

public class FunctionTest
{
   // Make sure we print a warning in findRoot at most once.
   private static boolean warningShown = false;

   /**
    * Tests the findRoot method.
    */
   public static void main(String[] args)
   {
      final double A = 3;
      final double B = 4;
      final double EPSILON = 10E-8;
      
      double result = findRoot(A, B, EPSILON);

      System.out.println("A root exists at x = " + result);

   } // end main()

   /**
    * Finds the root between two points in a sine function.
    *
    * @param a An arbitrary x value on one side of the root.
    * @param b An arbitrary x value on the other side of the root.
    * @param epsilon The tolerated error.
    *
    * @return The approximate x value at the root.
    */
   public static double findRoot(double a, double b, double epsilon)
   {
      // Make sure sin(a) and sin(b) have opposite signs.
      if(Math.signum(Math.sin(a)) == Math.signum(Math.sin(b)) && !warningShown)
      {
         System.out.println("Warning! There may be more than one root or no"
               + " root at all in the chosen bounds. Results may be"
               + " erroneous.");
         
         warningShown = true;
      }

      // Find the midpoint between a and b.
      double x = (a + b)/2;

      // If x is almost equal to a, then return x.
      if(Math.abs(a - x) < epsilon)
      {
         return x;
      }

      // If sin(x) and sin(a) have the the same sign,
      // the the zero is between those two x and b.
      // Otherwise, it is between x and a.
      if(Math.signum(Math.sin(x)) == Math.signum(Math.sin(a)))
      {
         return findRoot(x, b, epsilon);
      }
      else
      {
         return findRoot(a, x, epsilon);
      }

   } // end findRoot()

} // end FunctionTest
