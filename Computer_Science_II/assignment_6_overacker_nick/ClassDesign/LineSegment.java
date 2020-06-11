/**
 * A general class for representing objects that are essentially
 * line segments, such as a BoundarySegment.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 6.1
 */

public class LineSegment
{
   private Dimensionless point1, point2;


   /**
    * Instantiates a LineSegment by defining its coordinates.
    *
    * @param x1 The x coordinate of one endpoint.
    * @param y1 The y coordinate of one endpoint.
    * @param x2 The x coordinate of the other endpoint.
    * @param y2 The y coordinate of the other endpoint.
    */
   public LineSegment(double x1, double y1, double x2, double y2)
   {
      point1 = new Point(x1,y1);
      point2 = new Point(x2,y2);
   }


   /**
    * Instantiates a LineSegment by defining its endpoints.
    *
    * @param _point1 One of the endpoints.
    * @param _point2 The other endpoint.
    */
   public LineSegment(Dimensionless _point1, Dimensionless _point2)
   {
      point1 = _point1;
      point2 = _point2;
   }


   /**
    * Calculates and returns the length of a LineSegment.
    *
    * @return The length of this LineSegment.
    */
   public double length()
   {
      double a = point2.getX() - point1.getX();
      double b = point2.getY() - point1.getY();
      return Math.sqrt(a*a + b*b);
   }


   /**
    * Returns the first endpoint of this segment.
    *
    * @return The first endpoint of this segment.
    */
   public Dimensionless getPoint1()
   {
      return point1;
   }


   /**
    * Returns the second endpoint of this segment.
    *
    * @return The second coordinate of the first end.
    */
   public Dimensionless getPoint2()
   {
      return point2;
   }
}
