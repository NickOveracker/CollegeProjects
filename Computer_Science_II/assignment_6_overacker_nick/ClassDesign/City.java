/**
 * A class for representing cities.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 6.1
 */

public class City extends Place implements Dimensionless
{
   private double xCoord;
   private double yCoord;
   private Country country = null;

   /**
    * Instantiates a City object and assigns its name and 
    * coordinates.
    *
    * Cities are treated as dimensionless points, which is
    * reasonable for a program dealing with such large-scale
    * objects as Countries.
    *
    * @param _name The name of the city.
    * @param _xCoord The x coordinate of the City, according to
    *                the user's coordinate system.
    * @param _yCoord The y coordinate of the City, according to
    *                the user's coordinate system.
    */
   public City(String _name, double _xCoord, double _yCoord)
   {
      super(_name);
      xCoord = _xCoord;
      yCoord = _yCoord;
   }


   /**
    * Returns this City's x coordinate.
    *
    * @return The x coordinate of this City according to the
    *         user's coordinate system.
    */
   public double getX()
   {
      return xCoord;
   }


   /**
    * Returns this City's y coordinate.
    *
    * @return The y coordinate of this City according to the
    *         user's coordinate system.
    */
   public double getY()
   {
      return yCoord;
   }


   /**
    * Returns the distance between this and another City.
    *
    * @param city The city to which the distance is to be measured.
    * @return The distance between this and another City.
    */
   public double distance(City city)
   {
      return (new LineSegment(this, city)).length();
   }

   
   /**
    * Sets the Country to which this City belongs.
    *
    * @param _country The Country that this City is a part of.
    */
   public void setCountry(Country _country)
   {
      if(country != null)
      {
         country.removeCity(this);
      }

      country = _country;
      country.addCity(this);
   }

   /**
    * Returns the Country that this City is associated with.
    *
    * @return this.country
    */
   public Country getCountry()
   {
      return country;
   }
}
