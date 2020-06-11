/**
 * Provides a general interface for Mines and SafeCells in a Minesweeper field.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 5.1
 */

import java.io.*;

public abstract class Cell implements Comparable, Serializable
{
   // These values are returned by getType()
   public static int MINE = 0;
   public static int SAFE = 1;

   // The "weight" of a Cell is used for shuffling an array of Cells.
   private double weight;

   // The Cell's coordinates. Must be set manually.
   private int xPos = 0;
   private int yPos = 0;

   // Status variables
   private boolean revealed = false;
   private boolean flag = false;
   private int type;

   private Minefield field;


   /**
    * Sets the shuffling weight of a new Cell.
    *
    * @param _type Cell.MINE or Cell.SAFE
    * @param _field The Minefield that this Cell belongs to.
    */
   public Cell(int _type, Minefield _field)
   {
      weight = Math.random();
      type = _type;
      field = _field;
   }


   /**
    * Returns the shuffling weight of a Cell object.
    *
    * @return <code>this.weight</code>
    */
   public double getWeight()
   {
      return weight;
   }


   /**
    * Compares two cells by their weight variables; used for shuffling.
    *
    * @param comparedCell The cell to be compared with <code>this</code>.
    * @return -1, 0, or 1 for less than, equal to, and less than, respectively.
    */
   public int compareTo(Object obj)
   {
      Cell c = (Cell) obj;

      return (int) Math.signum(this.getWeight() - c.getWeight());
   }


   /**
    * Returns the type of a cell.
    *
    * @return Cell.MINE or Cell.SAFE
    */
   public int getType()
   {
      return type;
   }


   /**
    * Sets xPos.
    *
    * @param x The horizontal position.
    */
   public void setXPos(int x)
   {
      xPos = x;
   }


   /**
    * Sets yPos.
    * 
    * @param y The vertical position.
    */
   public void setYPos(int y)
   {
      yPos = y;
   }


   /**
    * Returns the x position of a cell.
    *
    * @return The horizontal position.
    */
   public int getXPos()
   {
      return xPos;
   }


   /**
   * Returns the y position of a cell.
   *
   * @return The vertical position.
   */
   public int getYPos()
   {
     return yPos;
   }


   /**
    * Returns whether or not the cell has been revealed.
    *
    * @return True if the cell has been revealed, false otherwise.
    */
   public boolean isRevealed()
   {
      return revealed;
   }


   /**
    * Returns whether or not the cell has been flagged.
    *
    * @return True if flagged, false if not.
    */
   public boolean isFlagged()
   {
      return flag;
   }


   /**
    * Reveals this cell.
    */
   public void reveal()
   {
      // Reveal and increment the revealed cell counter in field.
      revealed = true;
      field.addRevealed();

      // We can't let it be flagged and revealed at the same time.
      if(flag)
      {
         flag = false;
         field.removeFlag();
      }
   }


   /**
    * Toggles the Cell's flagged status - for marking or unmarking mines.
    */
   public void toggleFlag()
   {
      // Don't flag if already revealed.
      if(!revealed)
      {
         // Only flag if the player hasn't used up all his/her flags.
         // The number of available flags is equal to the number of mines.
         if(!flag && field.getNumMines() > field.getNumFlags())
         {
            flag = true;
            field.addFlag();
         }
         // Unflag if already flagged.
         else if(flag)
         {
            flag = false;
            field.removeFlag();
         }
      }
   }


   /**
    * Returns the Cell as a String for debugging purposes.
    *
    * @return The Cell as a String.
    */
   public String toString()
   {
      if(flag)
      {
         return "F";
      }
      return "?";
   }


   /**
    * Returns the Minefield object.
    *
    * @return The Minefield object.
    */
   public Minefield getField()
   {
      return field;
   }

} // end Cell
