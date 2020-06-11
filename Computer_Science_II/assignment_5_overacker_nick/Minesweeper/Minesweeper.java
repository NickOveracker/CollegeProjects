/**
 * A simple GUI-based game.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 5.1
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

      // Build the model.
      field = new Minefield(20, 20, 50);

      // Get a nice square-shape screen that will fit in the display.
      Toolkit kit = Toolkit.getDefaultToolkit();
      Dimension scrn = kit.getScreenSize();
      int dim = scrn.height < scrn.height ? scrn.height/2 : scrn.width/2;
      setSize(dim, dim);

      // Add our GUI elements.
      Container contentPane = getContentPane();
      contentPane.setLayout(new BorderLayout());
      contentPane.add(new MineCounterPanel(field), BorderLayout.PAGE_START);
      contentPane.add(new CellPanel(field, this), BorderLayout.CENTER);
      setJMenuBar(new MSMenuBar(field, this));

      // Finish up.
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   }


   /**
    * Loads a saved game by removing all components and
    * reinitializing them with the loaded Minefield object.
    *
    * @param _field The loaded model object.
    */
   public void loadGame(Minefield _field)
   {
      field = _field;

      // First remove the current components
      Container contentPane = getContentPane();
      contentPane.removeAll();

      // Now add the new ones
      contentPane.add(new MineCounterPanel(field), BorderLayout.PAGE_START);
      contentPane.add(new CellPanel(field, this), BorderLayout.CENTER);

      // Done
      setVisible(true);
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
                                   JOptionPane.PLAIN_MESSAGE);
   }


   /**
    * Handles the vague possiblility that the player will win.
    */
   public void win()
   {
      JOptionPane.showMessageDialog(this,
                                   "You win!",
                                   "Congratulations!",
                                   JOptionPane.PLAIN_MESSAGE);
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
