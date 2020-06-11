/**
 * A simple GUI-based game.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 3.3
 */

import javax.swing.*;
import java.awt.*;

public class Minesweeper extends JFrame
{
   MineCounterPanel counter;
   Minefield field;

   public static void main(String[] args)
   {
      Minesweeper game = new Minesweeper();
   }


   /**
    * Initializes the game.
    */
   public Minesweeper()
   {
      setTitle("Minesweeper");

      // This will hold the width, the height, and the number of mines,
      // in that order.
      int[] widthHeightMines;

      // Query the player for desired settings.
      widthHeightMines = getSettings();
      int width = widthHeightMines[0];
      int height = widthHeightMines[1];
      int mines = widthHeightMines[2];

      // Build the model.
      field = new Minefield(width, height, mines);

      // Get a nice square-shape screen that will fit in the display.
      Toolkit kit = Toolkit.getDefaultToolkit();
      Dimension scrn = kit.getScreenSize();
      int dim = scrn.height < scrn.height ? scrn.height/2 : scrn.width/2;
      setSize(dim, dim);

      // Add our GUI elements.
      Container contentPane = getContentPane();
      contentPane.setLayout(new BorderLayout());
      counter = new MineCounterPanel(field);
      contentPane.add(counter, BorderLayout.NORTH);
      contentPane.add(new CellPanel(field, this), BorderLayout.CENTER);

      // Finish up.
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   }


   /**
    * Queries the player for game settings.
    *
    * @return An array with the desired width at arr[0], the height at arr[1],
    *         and the number of mines at arr[2].
    */
   private int[] getSettings()
   {
      int[] arr = new int[3];
      boolean valid = false;
      String input;

      do
      {
          input = JOptionPane.showInputDialog("How many cells wide should "
              + "the field be? (Enter an integer)");
         try
         {
            arr[0] = Integer.parseInt(input);
            valid = true;
         }
         catch(NumberFormatException e)
         {
         }
      }
      while(!valid);

      valid = false;
      do
      {
         input = JOptionPane.showInputDialog("How many cells high should "
               + "the field be? (Enter an integer)");
         try
         {
            arr[1] = Integer.parseInt(input);
            valid = true;
         }
         catch(NumberFormatException e)
         {
         }
      }
      while(!valid);

      valid = false;
      do
      {
         input = JOptionPane.showInputDialog("How many mines should be "
               + "generated? (Enter an integer)");
         try
         {
            arr[2] = Integer.parseInt(input);
            valid = true;
         }
         catch(NumberFormatException e)
         {
         }
      }
      while(!valid);

      return arr;
   }

  
   /**
    * Handles the likely event that the player will lose.
    */
   public void lose()
   {
      field.setAllCellsVisible();
      repaint();
      JOptionPane.showMessageDialog(this,
                                   "You lose :(",
                                   "I'm sorry...",
                                   JOptionPane.INFORMATION_MESSAGE);
      System.exit(0);
   }


   /**
    * Handles the vague possiblility that the player will win.
    */
   public void win()
   {
      JOptionPane.showMessageDialog(this,
                                   "You win!",
                                   "Congratulations!",
                                   JOptionPane.INFORMATION_MESSAGE);
          System.exit(0);
   }


   /**
    * Updates the field and checks whether or not the player has won;
    * Losing conditions are handled separately by the CellLabel class
    * when the player clicks on a mine.
    */
   public void repaint()
   {
       super.repaint();
      
       if(field.hasWon())
       {
          win();
       }
    }
} // end Minesweeper
