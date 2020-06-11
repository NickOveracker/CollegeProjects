/**
 * A class to represent a mathematical point.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 6.1
 */

public class Point implements Dimensionless
{
   double x;
   double y;

   /**
    * Instantiate a point and set its coordinates.
    *
    * @param _x The x coordinate of this Point.
    * @param _y The y coordinate of this Point.
    */
   public Point(double _x, double _y)
   {
      x = _x;
      y = _y;
   }


   /**
    * Returns the x coordinate of this Point.
    *
    * @return this.x
    */
   public double getX()
   {
      return x;
   }


   /**
    * Returns the y coordinate of this Point.
    *
    * @return this.x
    */
   public double getY()
   {
      return y;
   }
}
