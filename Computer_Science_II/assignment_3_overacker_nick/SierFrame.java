/**
 * Provides the graphical framework to generate a Sierpinski triangle.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 3.2
 */

import javax.swing.*;
import java.awt.*;

public class SierFrame extends JFrame
{
   public SierFrame()
   {
      setTitle("Sierpinski Triangles");

      // Size the window
      Toolkit kit = Toolkit.getDefaultToolkit();
      Dimension screen = kit.getScreenSize();
      setSize(screen.width/3, screen.height/2);

      // Set up our panel
      Container contentPane = getContentPane();
      SierPanel sier = new SierPanel();
      contentPane.add(sier);

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   } // end constructor
} // end SierFrame
