/**
 * A simple web-browsing application.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 4.2
 */

import javax.swing.*;
import java.awt.*;

public class BrowserFrame extends JFrame
{
   BrowserPanel panel;
   /**
    * Sets up the frame and starts the program.
    */
   public BrowserFrame()
   {
      // Set the frame settings
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      Toolkit kit = Toolkit.getDefaultToolkit();
      Dimension screen = kit.getScreenSize();
      setSize(screen.width/2, screen.height/2);

      // Get the content pane, set the layout
      Container pane = getContentPane();
      pane.setLayout(new BorderLayout());

      // Add the AddressBar
      pane.add(new AddressBar(this, getWidth()),
                              BorderLayout.NORTH);
      // Add the BrowserPanel
      panel = new BrowserPanel(this);
      JScrollPane scroll = new JScrollPane(panel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      pane.add(scroll, BorderLayout.CENTER);
      setVisible(true);
   }


   /**
    * Tells the BrowserPanel to open a new page.
    *
    * @param url The URL to be opened.
    */
   public void openNewPage(String url)
   {
      panel.open(url);
      repaint();
   }
}
