/**
 * Draws a message in a bottle.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 3.1
 */

import javax.swing.*;
import java.awt.*;

public class MessagePanel extends JPanel
{
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);

      // Draw a bottle. One bloody line at a time.
      // Or circle.
      // Or oval.
      // I hate this.
      // Not really :P
      g.drawOval(225, 50, 50, 20);     // drinking-hole-thing
      g.drawLine(225, 60, 225, 150);   // spout, left side
      g.drawLine(275, 60, 275, 150);   // spout, right side
      g.drawLine(225, 150, 200, 175);  // a not-very-smooth transition to the body
      g.drawLine(275, 150, 300, 175);  // right side of same
      g.drawLine(200, 175, 200, 400);  // left side of body
      g.drawLine(300, 175, 300, 400);  // right side of body
      g.drawLine(200, 400, 300, 400);  // bottom
      g.drawString("MS PAINT", 220, 250);
      g.drawString("IS EASIER!", 218, 270);
   } // end paintComponent()

} // end MessagePanel
