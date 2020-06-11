/**
 * Initializes the GUI settings and provides a graphical interface.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 4.3
 */

import javax.swing.*;
import java.awt.*;

public class ControllerFrame extends JFrame
{
   public ControllerFrame()
   {
      setTitle("Parrot ARDrone Controller");

      // Set the size
      Toolkit kit = Toolkit.getDefaultToolkit();
      Dimension screen = kit.getScreenSize();
      setSize(screen.width/2, screen.height/2);

      // Set the layout
      Container pane = getContentPane();
      pane.setLayout(new BorderLayout());

      // This is the model object.
      Controller c = new Controller("139.78.141.250", 9090);

      // Add an IP and port selector
      pane.add(new OptionPanel(c), BorderLayout.NORTH);

      // Add the controller itself.
      pane.add(new ControllerPanel(c), BorderLayout.CENTER);

      // Last stuff
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   }
}
