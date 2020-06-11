/**
 * Allows the user to specify the IP and port to connect to.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 4.3
 */

import javax.swing.*;
import java.awt.event.*;

public class OptionPanel extends JPanel implements ActionListener
{
   private JTextField IPField;
   private JTextField portField;
   private Controller controller;

   
   /**
    * Constructs an IPField object.
    *
    * @param c The Controller object associated with the robot.
    */
   public OptionPanel(Controller c)
   {
      super();

      controller = c;

      IPField = new JTextField("139.78.141.250");
      portField = new JTextField("9090");
      JButton connect  = new JButton("Connect");
      JButton halp = new JButton("Halp!");

      connect.addActionListener(this);
      halp.addActionListener(this);

      add(new JLabel("IP: "));
      add(IPField);
      add(new JLabel(" Port: "));
      add(portField);
      add(connect);
      add(halp);
   }


   /**
    * Handles changes in IP and port.
    */
   public void actionPerformed(ActionEvent e)
   {
      if(e.getActionCommand().equals("Connect"))
      {
         int port;

         // The port is a String. Users are stupid, so we need
         // to make sure the port is usable.
         try
         {
            port = Integer.parseInt(portField.getText());
         }
         catch(Exception x)
         {
            return;
         }

         // Try to open the new Socket. If this fails, throw up
         // an error message.
         if(!controller.openNewSocket(IPField.getText(), port))
         {
            JOptionPane.showMessageDialog(null, "Unable to connect.");
         }
      }
      else
      {
         JOptionPane.showMessageDialog(null, "To begin, press \"Connect\"."
               + "\n\nTo increase the robot's speed in any direction,\n"
               + "simply press the button repeatedly."
               + "\n\nJust like when your computer is going slow\n"
               + "and you are trying to get it to go faster.");
      }
   }
}
