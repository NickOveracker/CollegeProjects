/**
 * A frame containing a message in a bottle.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 3.1
 */

import javax.swing.*;
import java.awt.*;

public class MessageFrame extends JFrame
{
   public MessageFrame()
   {
      setTitle("Message in a Bottle");
      setSize(500, 500);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Add the message in the bottle
      Container contentPane = getContentPane();
      MessagePanel messagePanel = new MessagePanel();
      contentPane.add(messagePanel);

      setVisible(true);
   } // end constructor
} // end MessageFrame
