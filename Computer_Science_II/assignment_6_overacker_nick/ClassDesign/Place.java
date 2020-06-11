/**
 * Provides general methods that are common to all classes representing
 * geographical locations.
 *
 * The coordinate system to be used is up to the user, but is limited
 * to two dimensions.
 *
 * This uses planar geometry and is not intended to represent an entire planet.
 *
 * The chosen axes must be used consistently for ALL Place objects.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 6.1
 */

import java.util.ArrayList;

public class Place
{
   // Instance variable declarations
   private String name;

   private ArrayList<BoundarySegment> borders
            = new ArrayList<BoundarySegment>();

   private ArrayList<Place> neighbors
            = new ArrayList<Place>();
   
   /**
    * Instatiates a Place and initializes the name.
    *
    * @param _name The name of the place.
    */
   public Place(String _name)
   {
      name = _name;
   }


   /**
    * Returns the name of this Place.
    *
    * @return this.name
    */
   public String name()
   {
      return name;
   }


   /**
    * Adds a BoundarySegment linked to this Place and a geographical neighbor.
    *
    * @param neighbor The geographical neighbor that shares this border.
    * @param x1 The distance of one end of the border from some y-axis.
    * @param y1 The distance of one end of the border from some x-axis.
    * @param x2 The distance of the other end of the border from some y-axis.
    * @param y2 The distance of the other end of the border from some x-axis.
    */
   public void addBorder(Place neighbor, double x1, double y1,
                                         double x2, double y2)
   {
      // Create the border, add it to both this and neighbor,
      // and add a reference to neighbor here.
      BoundarySegment bound
         = new BoundarySegment(this, neighbor, x1, y1, x2, y2);
      borders.add(bound);
      neighbor.borders.add(bound);
      neighbors.add(neighbor);
   }


   /**
    * Returns an ArrayList containing all geographical neighbors to this Place.
    *
    * @return this.neighbors
    */
   public ArrayList<Place> neighbors()
   {
      return neighbors;
   }


   /**
    * Returns the planar area covered by the Place.
    *
    * @return this.area
    */
   public double area()
   {
      return Geometry.area(borders);
   }


   /**
    * Returns the total length of the boundaries.
    *
    * @return The total length of the boundaries.
    */
   public double boundaryLength()
   {
      double length = 0;

      for(BoundarySegment s : borders)
      {
         length += s.length();
      }

      return length;
   }
}
