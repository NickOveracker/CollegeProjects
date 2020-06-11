/**
 * This panel keeps track of the number of mines remaining to be flagged.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 5.1
 */

import javax.swing.*;
import java.awt.*;

public class MineCounterPanel extends JPanel
{
   Minefield field;
   JLabel count;

   /**
    * Constructs a MineCounterPanel object.
    *
    * @param _field The Minefield object whose mines are to be counted.
    */
   public MineCounterPanel(Minefield _field)
   {
      field = _field;

      count = new JLabel("" + (_field.getNumMines() - _field.getNumFlags()));
      add(count);
   }


   /**
    * Updates the counter display.
    */
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);

      count.setText("" + (field.getNumMines() - field.getNumFlags()));
   }


   /**
    * Returns the current count.
    *
    * @return The difference beween the number of mines and flags.
    */
   public int getCount()
   {
      return Integer.parseInt(count.getText());
   }
} // end MineCounterPanel
