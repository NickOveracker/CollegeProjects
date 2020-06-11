/**
 * Provides the necessary methods and interface for safe cells in Minesweeper.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 5.1
 */

import java.io.*;

public class SafeCell extends Cell implements Serializable
{
   /**
    * Calls the superconstructor with type information.
    */
   public SafeCell(Minefield m)
   {
      super(SAFE, m);
   }
   

   /**
    * Converts the SafeCell to a String for debugging purposes.
    *
    * @return The appropriate String representation of a SafeCell. 
    */
   public String toString()
   {
      if(isRevealed())
      {
         return "" + numAdjacentMines();
      }
      return super.toString();
   }


   /**
    * Counts the nearby Mines.
    *
    * @return The number of adjacent Mines.
    */
   public int numAdjacentMines()
   {
      // Check the surrounding cells for Mines.
      int x = getXPos();
      int y = getYPos();
      int mines = 0;

      // Checks a 3x3 grid with this SafeCell at the center for mines.
      for(int i = -1; i <= 1; i++)
      {
         for(int j = -1; j <= 1; j++)
         {
            try
            {
               if(getField().getTypeInCell(x + i, y + j) == MINE)
               {
                  mines++;
               }
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
            }
         }
      } // end of check loop

      return mines;
   }


   /**
    * Reveals the SafeCell; if it is 0, then all adjacent cells
    * are revealed as well.
    */
   public void reveal()
   {
      if(isRevealed())
      {
         return;
      }

      super.reveal();

      if(numAdjacentMines() == 0)
      {
         int x = getXPos();
         int y = getYPos();

         // Loop through adjacent cells, but skip
         // the current cell or else we'll enter 
         // an infinite loop.
         for(int i = -1; i <= 1; i++)
         {
            for(int j = -1; j <= 1; j++)
            {
               if(!(i == 0 && j == 0))
               {
                  try
                  {
                     Cell c = getField().getCellAt(i + x, j + y);
                     c.reveal();
                  }
                  catch(ArrayIndexOutOfBoundsException e)
                  {
                  }
               }
            }
         }
      } // end if(numAdjacentMines == 0)
   } // end reveal()

} // end SafeCell
