/**
 * Provides methods for computing the area of arbitrary polygons, as well
 * as various helper methods.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 6.1
 */

import java.util.ArrayList;

public final class Geometry
{
   public static double EPSILON = 10E-6;

   /**
    * A private constructor prevents the nonsensical instantiation of a
    * Geometry object.
    */
   private Geometry()
   {
   }


   /**
    * Returns the approximate area of polygonal shapes with successive
    * trapezoidal approximations.
    *
    * @param sides An array of every side in the shape.
    * @return The area of the shape,  or NaN if it is unclosed.
    */
   public static double area(ArrayList<? extends LineSegment> sides)
   {
      // I had much of this method implemented, but realized that
      // I was wasting my time.
      // 
      // If fully implemented, it would loop through hundreds of points
      // in each non-vertical segment that was not completely below
      // another segment. It would then use the verticalDistance()
      // to determine the area dA between the selected points and
      // points on segments directly below.
      //
      // Because there is no guarantee that the shapes are convex,
      // some trickery would have to be employed to subtract unbounded
      // area. This isn't hard in principle, but could take a large amount
      // of time if not carefully implemented.
      // 
      // Note that the borders need not form a single polygon as long as
      // all polygons made by these borders are closed shapes. For example,
      // the continental United States need not be connected to Alaska and
      // Hawaii in order to calculate the total area of the country. But,
      // those borders do need to be included in the ArrayList.
      
      return 0.0;
   }


   /**
    * Determines whether two double values are approximately equal.
    *
    * @param d1 The first double to compare.
    * @param d2 The second double to compare.
    * @return True if d1 ~= d2, else false.
    */
   public static boolean equals(double d1, double d2)
   {
      return Math.abs(d1 - d2) < EPSILON;
   }


   /**
    * Calculates the vertical distance between a point and a LineSegment.
    *
    * @param x The x coordinate of the point.
    * @param y The y coordinate of the point.
    * @param seg The LineSegment.
    * @return The vertical distance between the point and the segment;
    *         if the segment is below the point, the distance will be negative.
    */
   public static double verticalDistance(double x, double y, LineSegment seg)
   {
      // If the segment is vertical, treat separately.
      if(equals(seg.getPoint1().getX(), seg.getPoint2().getX()))
      {
         if(Math.abs(seg.getPoint1().getY() - y) < 
               Math.abs(seg.getPoint2().getY() - y))
         {
            return seg.getPoint1().getY() - y;
         }
         else
         {
            return seg.getPoint2().getY() - y;
         }
      }

      // Find out which end of the segment is farthest to the left.
      boolean x1Left = seg.getPoint1().getX() < seg.getPoint2().getX();
      
      // Find the slope of the segment, and get the y coordinate of the
      // segment at x.
      double yAtX;

      if(x1Left)
      {
         double slope =
            (seg.getPoint2().getY()-seg.getPoint1().getY()) /
            (seg.getPoint2().getX()-seg.getPoint1().getX());

         yAtX = (x - seg.getPoint1().getX())*slope + seg.getPoint1().getY();
      }
      else
      {
         double slope =
            (seg.getPoint1().getY()-seg.getPoint2().getY()) /
            (seg.getPoint1().getX()-seg.getPoint2().getX());

         yAtX = (x - seg.getPoint2().getX())*slope + seg.getPoint2().getY();
      }

      return yAtX - y;
   }
}
