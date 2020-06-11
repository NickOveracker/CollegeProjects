/**
 * An interface for all Places that represent some sort of state,
 * which can be a country or a province.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 6.1
 */

import java.util.ArrayList;

public interface State
{
   public Place capital();
   public ArrayList<City> getCities();
}
