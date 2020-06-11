/**
 * Provides the necessary methods for describing countries as
 * Places divided into one or more other Places (states, cities, etc).
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 6.1
 */

import java.util.ArrayList;

public class Country extends Place implements State
{
   private Place capital = null;
   private ArrayList<City> cities = new ArrayList<City>();

   /**
    * Instantiates a Country object with a given name.
    *
    * @param name The name of the country.
    */
   public Country(String name)
   {
      super(name);
   }


   /**
    * Sets the capital of this Country to a given Place.
    *
    * @param _capital The Place to set as this Country's capital.
    */
   public void setCapital(Place _capital)
   {
      capital = _capital;
   }


   /**
    * Returns the capital of the country.
    *
    * @return this.capital
    */
   public Place capital()
   {
      return capital;
   }


   /**
    * Adds a City to this Country.
    *
    * @param city The City to add to this Country.
    */
   public void addCity(City city)
   {
      if(!cities.contains(city))
      {
         cities.add(city);
         city.setCountry(this);
      }
   }


   /**
    * Removes a City from this Country.
    *
    * @param city The City to add to this Country.
    */
   public void removeCity(City city)
   {
      cities.remove(city);
   }


   /**
    * Returns the ArrayList of all Cities associated with
    * this Country.
    *
    * @return An ArrayList including all Cities added to this
    *         Country.
    */
   public ArrayList<City> getCities()
   {
      return cities;
   }
}
