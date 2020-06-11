/**
 * BoundarySegments connect two Places - every BoundarySegment has
 * a length and two associated places.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 6.1
 */

public class BoundarySegment extends LineSegment
{
   private Place placeOne;
   private Place placeTwo;

   /**
    * Instantiates a BoundarySegment with all variables set.
    *
    * @param _placeOne One of the places having this segment as a border.
    * @param _placeTwo The second place having this segment as a border.
    * @param x1 The x coordinate of one end.
    * @param y1 The y coordinate of one end.
    * @param x2 The x coordinate of the other end.
    * @param y2 The y coordinate of the other end.
    */
   public BoundarySegment(Place _placeOne, Place _placeTwo,
                                    double x1, double y1,
                                    double x2, double y2)
   {
      super(x1, y1, x2, y2);
      placeOne = _placeOne;
      placeTwo = _placeTwo;
   }

   /**
    * Returns a two-element array containing the two Places
    * separated by this BoundarySegment.
    *
    * @return An array containing two Places associated with
    *         this BoundarySegment.
    */
   public Place[] borderOf()
   {
      Place[] arr = {placeOne, placeTwo};
      return arr;
   }
}

