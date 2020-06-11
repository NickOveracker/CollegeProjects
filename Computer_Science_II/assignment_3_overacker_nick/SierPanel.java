/**
 * Generates a Sierpinski triangle
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 3.2
 */

import javax.swing.*;
import java.awt.*;

public class SierPanel extends JPanel
{
   public void paintComponent(Graphics g)
   {
      // Get our current window size.
      int width = getWidth();
      int height = getHeight();

      // We need a square section of the window.
      // Find out which of the dimensions is smaller,
      // and make our square have that side length.
      int dim = width < height ? width : height;

      // Draw our Sierpinski triangle.
      drawSierTriangle(0, 0, dim, g);
      
   } // end paintComponent


   /**
    * Recursively draws a Sierpinski triangle.
    *
    * @param x The x-coordinate of the top-left corner of the
    * square to be filled.
    * @param y The y-coordinate of teh top-left corner of the
    * square to be filled.
    * @param w The width of the square to be filled.
    * @param g The Graphics object with which to call draw commands.
    */
   public void drawSierTriangle(int x, int y, int dim, Graphics g)
   {
      // Base case: If the dimension is one, draw a one-pixel rectangle.
      if(dim == 1)
      {
         g.drawRect(x, y, dim, dim);
      }

      // All other cases: Break the square into three areas, and fill them.
      else
      {
         // Draw in the top middle area.
         // Same y, but different x and dim.
         drawSierTriangle(x + dim/4, y, dim/2, g);

         // Draw in the bottom left area.
         // Same x, but different y and dim.
         drawSierTriangle(x, y + dim/2, dim/2, g);

         // Draw in the bottom right area.
         // Different x, y, and dim.
         drawSierTriangle(x + dim/2, y + dim/2, dim/2, g);
      }

   } // end drawSierTriangle()

} // end SierPanel
