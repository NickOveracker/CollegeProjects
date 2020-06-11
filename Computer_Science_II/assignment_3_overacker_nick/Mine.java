/**
 * Provides the necessary methods and interface for mines in Minesweeper.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 3.3
 */

public class Mine extends Cell
{

   /**
    * Calls the superconstructor with type information.
    */
   public Mine(Minefield m)
   {
      super(MINE, m);
   }


   /**
    * Converts the Mine to a String for debugging purposes.
    *
    * @return "X"
    */
   public String toString()
   {
      if(isRevealed())
      {
         return "X";
      }

      return super.toString();
   }

} // end Mine
