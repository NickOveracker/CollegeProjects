/**
 * Contains the various buttons needed to operated the robot.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 4.3
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ControllerPanel extends JPanel implements ActionListener
{
   private Controller controller;

   /**
    * Constructs a panel containing several buttons
    * for controlling the Parrot ARDrone.
    *
    * @param c The Controller object associated with the robot.
    */
   public ControllerPanel(Controller c)
   {
      super();
      controller = c;
      setLayout(new GridLayout(0,5));
      addButtons();
   }

   
   /**
    * Handles the dirty work of adding buttons to the panel.
    */
   public void addButtons()
   {
      // Set up the controller buttons.
      // I hate making these an array, but it's
      // most conveneient this way. I will put the
      // buttons in the order that they are used.
      // ANY CHANGES HERE MUST BE REFLECTED IN ACTIONPERFORMED
      JButton[] buttons = {
         new JButton("Takeoff"),
         new JButton("Up"),
         new JButton("Land"),
         new JButton("Down"),
         new JButton("Forward-Left"),
         new JButton("Forward"),
         new JButton("Forward-Right"),
         new JButton("Rotate CCW"),
         new JButton("Left"),
         new JButton("Stop"),
         new JButton("Right"),
         new JButton("Rotate CW"),
         new JButton("Reverse-Left"),
         new JButton("Reverse"),
         new JButton("Reverse-Right")
      };

      // Here's where the array is nice.
      for(int i = 0; i < buttons.length; i++)
      {
         buttons[i].addActionListener(this);
      }

      // Set the buttons in an accessible layout.
      // Unfortunately, this means placing a lot of
      // dummy JLabels just to fill unused cells.
      // 
      // Don't reorder the buttons here - reorder them
      // in the array itself.
      int i = 0;
      add(buttons[i++]);   // Row 1: Takeoff _ _ _ Up
      add(new JLabel());
      add(new JLabel());
      add(new JLabel());
      add(buttons[i++]);
      add(buttons[i++]);   // Row 2: Land _ _  _ Down
      add(new JLabel());
      add(new JLabel());
      add(new JLabel());
      add(buttons[i++]);
      add(new JLabel());   // Row 3: Blank
      add(new JLabel());
      add(new JLabel());
      add(new JLabel());
      add(new JLabel());
      add(new JLabel());   // Row 4: _ NW N NE _
      add(buttons[i++]);
      add(buttons[i++]);
      add(buttons[i++]);
      add(new JLabel());
      add(buttons[i++]);   // Row 5: CW W STOP E CCW
      add(buttons[i++]);
      add(buttons[i++]);
      add(buttons[i++]);
      add(buttons[i++]);
      add(new JLabel());   // Row 6: _ SW S SE _
      add(buttons[i++]);
      add(buttons[i++]);
      add(buttons[i++]);
      add(new JLabel());
   }


   /**
    * This does the somewhat dirty work of deciding what button does what.
    */
   public void actionPerformed(ActionEvent e)
   {
      String ac = e.getActionCommand();

      if(ac.equals("Takeoff"))
      {
         controller.takeoff();
      }
      else if(ac.equals("Land"))
      {
         controller.land();
      }
      else if(ac.equals("Forward-Left"))
      {
         controller.setVelocity(0.05, 0.05, 0, 0);
      }
      else if(ac.equals("Forward"))
      {
         controller.setVelocity(0.05, 0, 0, 0);
      }
      else if(ac.equals("Forward-Right"))
      {
         controller.setVelocity(0.05, -0.05, 0, 0);
      }
      else if(ac.equals("Left"))
      {
         controller.setVelocity(0, 0.05, 0, 0);
      }
      else if(ac.equals("Right"))
      {
         controller.setVelocity(0, -0.05, 0, 0);
      }
      else if(ac.equals("Reverse-Left"))
      {
         controller.setVelocity(-0.05, 0.05, 0, 0);
      }
      else if(ac.equals("Reverse"))
      {
         controller.setVelocity(-0.05, 0, 0, 0);
      }
      else if(ac.equals("Reverse-Right"))
      {
         controller.setVelocity(-0.05, -0.05, 0, 0);
      }
      else if(ac.equals("Up"))
      {
         controller.setVelocity(0, 0, 0.05, 0);
      }
      else if(ac.equals("Down"))
      {
         controller.setVelocity(0, 0, -0.05, 0);
      }
      else if(ac.equals("Rotate CCW"))
      {
         controller.setVelocity(0, 0, 0, 0.2);
      }
      else if(ac.equals("Rotate CW"))
      {
         controller.setVelocity(0, 0, 0, -0.2);
      }
      else if(ac.equals("Stop"))
      {
         controller.stop();
      }
   }
}
