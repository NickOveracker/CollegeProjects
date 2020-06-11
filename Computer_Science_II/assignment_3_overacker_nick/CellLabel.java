/**
 * Adds a few Minesweepery components to JLabels.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 3.3
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CellLabel extends JLabel
{
   private Cell cell;
   private JFrame frame;

   // This will determine the color of revealed cells numbering 0-8.
   Color[] color =
   {
      // The first one is used to draw the border around revealed cells.
      Color.LIGHT_GRAY,
      Color.BLUE,
      Color.GREEN,
      Color.RED,
      Color.ORANGE,
      Color.BLACK,
      Color.MAGENTA,
      Color.CYAN,
      Color.DARK_GRAY,
   };

   /**
    * Constructs a CellLabel object.
    *
    * @param _cell The cell that is represented by this CellLabel.
    * @param _frame The parent frame.
    */
   public CellLabel(Cell _cell, JFrame _frame)
   {
      super("", SwingConstants.CENTER);
      cell = _cell;
      frame = _frame;
      setOpaque(true);
      addMouseListener(new MouseHandler());
   }


   /**
    * Sets the correct display for this cell.
    */
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      
      // Make sure we stay in our bounds.
      Dimension size = getSize();

      
      // Case one: Cell is hidden. 
      if(!cell.isRevealed())
      {
         g.drawRect(0, 0, size.width-1, size.height-1);
         
         if(cell.isFlagged())
         {
            drawFlag(g, size);
         }
         return;
      }

      // Case 2: Cell is revealed and safe.
      else if(cell.getType() == Cell.SAFE)
      {
         int num = ((SafeCell)cell).numAdjacentMines();

         setBackground(Color.WHITE);
         g.setColor(color[0]);
         g.drawRect(0, 0, size.width-1, size.height-1);

         // Show nothing inside 0 cells.
         if(num == 0)
         {
            setText("");
            return;
         }

         setForeground(color[((SafeCell)cell).numAdjacentMines()]);

         setText(cell.toString());
      }

      // Case 3: Cell is revealed and is a mine.
      else if(cell.getType() == Cell.MINE)
      {
         drawMine(g, size);
      }
   }


   /**
    * Draws a mine in this cell.
    */
   public void drawMine(Graphics g, Dimension size)
   {
      // I refuse to comment this.
      setBackground(Color.RED);
      g.drawRect(0, 0, size.width-1, size.height-1);
      g.setColor(Color.BLACK);
      g.drawLine(size.width/4, size.height/4, 3*size.width/4, 3*size.height/4);
      g.drawLine(size.width/4, 3*size.height/4, 3*size.width/4, size.height/4);
      g.drawLine(size.width/4, size.height/2, 3*size.width/4, size.height/2);
      g.drawLine(size.width/2, size.height/4, size.width/2, 3*size.height/4);
      g.fillOval(size.width/4, size.height/4, size.width/2, size.height/2);
      g.setColor(Color.MAGENTA);
      g.fillOval(7*size.width/16, 7*size.height/16, size.width/8, size.height/8);
   }


   /**
    * Draws a flag in this cell.
    */
   public void drawFlag(Graphics g, Dimension size)
   {
      // Flagpole
      g.setColor(Color.BLACK);
      g.drawLine(2*size.width/3, size.height/6, 2*size.width/3, 5*size.height/6);

      // Flag
      g.setColor(Color.RED);
      g.fillRect(size.width/6, size.height/6, size.width/2, size.height/3);
   }

   /**
    * Handles mouse events and updates the entire frame.
    */
   private class MouseHandler extends MouseAdapter
   {
      /**
       * Plants a flag if right clicked, reveals a cell if left clicked.
       */
      public void mouseClicked(MouseEvent e)
      {
         switch(e.getButton())
         {
            // Left click.
            case MouseEvent.BUTTON1:
               // Losing condition checked - if this is a mine, the game ends.
               if(cell.getType() == Cell.MINE && !cell.isRevealed())
               {
                  cell.reveal();
                  ((Minesweeper)frame).lose();
               }
               else
               {
                  cell.reveal();
               }
               break;

            // Right click.
            case MouseEvent.BUTTON3:
               cell.toggleFlag();
               break;
         }

         // Good. Repaint EVERYTHING.
         frame.repaint();
      }
   } // end MouseHandler
} // end CellLabel
