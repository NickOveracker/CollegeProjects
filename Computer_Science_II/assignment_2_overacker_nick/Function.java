/**
 * An abstract class that allows us to treat
 * various functions the same way.
 *
 * Overacker, Nick
 * Computer Science 2
 * Spring 2013
 * Assignment 2.6
 */

public abstract class Function
{
   /**
    * Tests the Function class.
    */
   public static void main(String[] args)
   {
      double epsilon = 10E-8;

      SinFunc sin = new SinFunc();
      CosFunc cos = new CosFunc();

      int[] a1 = {-3, 0, 1};
      int[] a2 = {-2, -1, 1};
      PolyFunc poly1 = new PolyFunc(a1);
      PolyFunc poly2 = new PolyFunc(a2);

      System.out.println("Sine root between 3 and 4: "
            + sin.findRoot(3, 4, epsilon));
      System.out.println("Cosine root between 1 and 3: "
            + cos.findRoot(1, 3, epsilon));
      System.out.println("Positive root of " + poly1 + ": "
            + poly1.findRoot(0, 5, epsilon));
      System.out.println("Positive root of " + poly2 + ": "
            + poly2.findRoot(0, 5, epsilon));
      
   } // end main()
   

   public abstract double evaluate(double x);

   /**
    * Finds the root between two points in a Function.
    * May return erroneous results if there is no zero or
    * if there are multiple zeroes between a and b.
    *
    * @param a An arbitrary x value on one side of the root.
    * @param b An arbitrary x value on the other side of the root.
    * @param epsilon The tolerated error.
    *
    * @return The approximate x value at the root.
    */
   public double findRoot(double a, double b, double epsilon)
   {
      // Find the midpoint between a and b.
      double x = (a + b)/2;

      // If x is almost equal to a, then return x.
      if(Math.abs(a - x) < epsilon)
      {
         return x;
      }

      // If evaluate(x) and evaluate(a) have the the same sign,
      // the the zero is between those two x and b.
      // Otherwise, it is between x and a.
      if(Math.signum(evaluate(x)) == Math.signum(evaluate(a)))
      {
         return findRoot(x, b, epsilon);
      }
      else
      {
         return findRoot(a, x, epsilon);
      }

   } // end findRoot()

} // end class Function
