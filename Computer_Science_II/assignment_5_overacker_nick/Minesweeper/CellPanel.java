/**
 * Provides a graphical interface for Minesweeper.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 5.1
 */

import javax.swing.*;
import java.awt.*;

public class CellPanel extends JPanel
{
   private JFrame frame;
   private Minefield field;
   private CellLabel[][] grid;

   /**
    * Constructs a CellPanel object.
    *
    * @param _frame The parent frame.
    */
   public CellPanel(Minefield _field, JFrame _frame)
   {
      super();

      // Set the frame so we can refer to it from lower levels.
      frame = _frame;

      // Set up the minefield.
      field = _field;
      grid = new CellLabel[field.getHeight()][field.getWidth()];
      setLayout(new GridLayout(grid.length, grid[0].length));

      for(int i = 0; i < grid.length; i++)
      {
         for(int j = 0; j < grid[i].length; j++)
         {
            // Note: The grid is built in the traditional (y, x) format, but the
            // cells are retrieved in the (x, y) format. For this reason, i and j
            // are reversed in field.getCellAt().
            grid[i][j] = new CellLabel(field.getCellAt(j, i), field, frame);
            add(grid[i][j]);
         }
      }
   } // end constructor
} // end CellPanel
