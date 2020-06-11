/**
 * Produces an editable address bar for web browsing.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 4.2
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddressBar extends JTextField implements ActionListener
{
   private BrowserFrame frame;

   /**
    * Constructs an AddressBar object.
    *
    * @param _frame The parent frame.
    */
   public AddressBar(BrowserFrame _frame, int width)
   {
      super(width);
      frame = _frame;
      setText("Enter a URL here to browse the interwebz");
      addActionListener(this);
   }


   /**
    * Handles submission by passing the page request to the
    * frame, which will pass it to the BrowserPanel.
    */
   public void actionPerformed(ActionEvent e)
   {
      frame.openNewPage(getText());
   }
}
