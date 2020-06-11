/**
 * Generates a 2D array of mine cells, and provides the tools to
 * interface with the array.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 3.3
 */

import java.util.Arrays; // used to shuffle (sort) the array.

public class Minefield
{
   // This 2D Cell array will be the playing grid.
   // A Cell may or may not contain a Mine.
   private Cell[][] grid;

   // These will be the dimensions of the grid.
   private int width;
   private int height;

   // Self-explanatory
   private int numberOfMines;
   private int numberOfSafe;
   private int numRevealed;
   private int flags = 0;


   /**
    * Runs a number of tests on the Minefield class.
    */
   public static void main(String[] args)
   {
      System.out.print("Generating 20x20 Minefield with 50 mines... ");
      long startTime = System.currentTimeMillis();
      Minefield field = new Minefield(20, 20, 50);
      long endTime = System.currentTimeMillis();
      System.out.println("Done.\n" + (endTime - startTime) + " ms elapsed.");

      System.out.println(field);

      System.out.print("Flagging cell (4, 12)... ");
      field.getCellAt(4, 12).toggleFlag();
      System.out.println("Done.");
      System.out.print("Revealing cell (8, 19)... ");
      field.getCellAt(8, 19).reveal();
      System.out.println("Done.");

      System.out.println(field);

      System.out.println("Revealing entire field.");
      field.setAllCellsVisible();
      System.out.println(field);

      System.out.print("Generating 100x100 Minefield with 5000 mines... ");
      startTime = System.currentTimeMillis();
      Minefield bigField = new Minefield(100, 100, 5000);
      endTime = System.currentTimeMillis();
      System.out.println("Done.\n" + (endTime - startTime) + " ms elapsed.");
   } // end main


   /**
    * Construct the Minefield.
    *
    * @param _width The width (in cells) of the Minefield.
    * @param _height The height (in cells) of the Minefield.
    * @param _numberOfMines The maximum number of mines in the Minefield.
    */
   public Minefield(int _width, int _height, int _numberOfMines)
   {
      // Make sure that there aren't more mines than cells.
      if(_numberOfMines > _width*_height)
      {
         _numberOfMines = _width*_height;
      }

      width = _width;
      height = _height;
      numberOfMines = _numberOfMines;
      numberOfSafe = width * height - numberOfMines;

      // Create an unshuffled array of Cells.
      // Use one dimension for easier shuffling.
      Cell[] cellArray = new Cell[width*height];

      // Initialize what will be the playing grid.
      grid = new Cell[height][width];
      
      for(int i = 0; i < cellArray.length; i++)
      {
         // First add the specified number of mines.
         if(i < numberOfMines)
         {
            cellArray[i] = new Mine(this);
         }

         // Fill the rest of the array with safe spaces.
         else
         {
            cellArray[i] = new SafeCell(this);
         }
      }

      // Shuffle the cells, using a variable set in their constructor. 
      Arrays.sort(cellArray);

      // This will be the index for the 1D array.
      int index = width*height - 1;

      // Now build the 2D array.
      for(int i = 0; i < height; i++)
      {
         for(int j = 0; j < width; j++)
         {
            grid[i][j] = cellArray[index];
            grid[i][j].setXPos(j);
            grid[i][j].setYPos(i);
            index--;
         }
      }
   } // end constructor


   /**
    * Converts the Minefield to a String for debugging purposes.
    *
    * @return The Minefield layout as a String.
    */
   public String toString()
   {
      String str = "";

      str += "Width: " + width + "\nHeight: " + height
         + "\nMines: " + numberOfMines + "\n";

      for(int i = 0; i < height; i++)
      {
         for(int j = 0; j < width; j++)
         {
            str += grid[i][j].toString() + "  ";
         }
         str += "\n";
      }

      return str;
   } // end toString


   /**
    * Sets all cells visible.
    */
   public void setAllCellsVisible()
   {
      for(int i = 0; i < grid.length; i++)
      {
         for(int j = 0; j < grid[i].length; j++)
         {
            grid[i][j].reveal();
         }
      }
   }



    /**
    * Returns the type of the Mine at a given point in the minefield.
    *
    * @param x The column to check.
    * @param y The row to check.
    * @return Cell.MINE or Cell.SAFE
    * @throws ArrayIndexOutOfBoundsException
    */
   public int getTypeInCell(int x, int y)
   {
      try
      {
         return grid[y][x].getType();
      }
      catch(ArrayIndexOutOfBoundsException e)
      {
         throw e;
      }
   }


  /**
    * Returns the Cell at a given point on the grid.
    *
    * @param x The x position of the cell.
    * @param y The y position of the cell.
    * @return The cell at grid[y][x].
    * @throws ArrayIndexOutOfBoundsException
    */
   public Cell getCellAt(int x, int y)
   {
      try
      {
         return grid[y][x];
      }
      catch(ArrayIndexOutOfBoundsException e)
      {
         throw e;
      }
   }


   /**
    * Returns the number of mines in the field.
    *
    * @return The number of mine in the field.
    */
   public int getNumMines()
   {
      return numberOfMines;
   }


   /**
    * Returns the number of safe cells in the field.
    *
    * @return The number of safe cells.
    */
   public int getNumSafe()
   {
      return numberOfSafe;
   }


   /**
    * Returns the number of flags planted.
    *
    * @return The number of flags planted.
    */
   public int getNumFlags()
   {
      return flags;
   }

   
   /**
    * Increments the flag counter.
    */
   public void addFlag()
   {
      flags++;
   }


   /**
    * Decrements the flag counter.
    */
   public void removeFlag()
   {
      flags--;
   }


   /**
    * Increments the number of revealed cells.
    */
   public void addRevealed()
   {
      numRevealed++;
   }


   /**
    * Returns the number of revealed cells.
    *
    * @return The number of revealed cells.
    */
   public int getNumRevealed()
   {
      return numRevealed;
   }

   
   /**
    * Returns the width of the Minefield.
    *
    * @return this.width
    */
   public int getWidth()
   {
      return width;
   }


   /**
    * Returns the height of the Minefield.
    *
    * @return this.height
    */
   public int getHeight()
   {
      return height;
   }


   /**
    * Returns whether or not the player has won.
    *
    * @return True if the player has won, false otherwise.
    */
   public boolean hasWon()
   {
      return flags - numberOfMines == 0 && numRevealed - numberOfSafe == 0;
   }
} // end Minefield

