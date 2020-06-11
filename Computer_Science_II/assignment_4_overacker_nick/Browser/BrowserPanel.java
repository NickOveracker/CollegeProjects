/**
 * Interprets and displays the contents of an Element object,
 * producing a visual representation of a webpage.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 4.2
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BrowserPanel extends JPanel
{
   private BrowserFrame frame;                     // The parent frame
   private Communicator com = new Communicator();  // Downloads pages
   private Element page;                           // Represents a webpage
   private String pageID = "page";                 // The file to write to

   private JTextArea text;

   /**
    * Create a BrowserPanel and open the default website.
    *
    * @param _frame The frame to which this panel belongs.
    */
   public BrowserPanel(BrowserFrame _frame)
   {
      frame = _frame;

      text = new JTextArea(0, 100);
      text.setLineWrap(true);
      text.setWrapStyleWord(true);
      text.setEditable(false);

      // Open the startup page.
      page = Element.getStartPage();
      getTextElements(page);
      frame.setTitle(page.getTitle().trim() + " - Overacker NickFaux");

      add(text);
   }


   /**
    * Opens a webpage and resets the panel.
    *
    * @param url The webpage to be downloaded.
    */
   public void open(String url)
   {
      text.setText("");

      try
      {
         com.downloadWebPage(url, pageID);
         page = Element.getPage(pageID);
      }
      catch(Exception e)
      {
         page = Element.getFillerPage();
      }
      frame.setTitle(page.getTitle().trim() + " - Overacker NickFaux");
      getTextElements(page);

      // Scroll to the top
      text.setCaretPosition(0);
   }


   /**
    * Creates an ArrayList of JLabels from the plaintext elements of the page.
    */
   private void getTextElements(Element parent)
   {
      // Reality check - be sure that parent isn't null or unsupported.
      // Unsupported-self-closing doesn't need to be checked - they are safe.
      if(parent == null || parent.getType() == HTMLTag.UNSUPPORTED)
      {
         return;
      }

      String elementText = parent.getText();
      String toTextArea = "";
      int childIndex = 0;

      // Start at the body.
      if(parent.getType() == HTMLTag.HTML)
      {
         Element child = parent.getBody();
         
         if(child != null)
         {
            getTextElements(child);
         }

         // If we're here, we don't want to continue.
         return;
      }

      // Handle tag-specific stuff.
      startTag(parent.getType());

      // Recursively add the text to our JTextArea.
      for(int i = 0; i < elementText.length(); i++)
      {
         // If this is just text, add to text.
         if(elementText.charAt(i) != '\0')
         {
            toTextArea += elementText.charAt(i);
         }
         else
         {
            // Add the current string to a JLabel and parse the subelement.
            // When we are done with that, we'll continue with this element.
            toTextArea = sanitize(toTextArea);
            text.append(toTextArea);
            toTextArea = "";

            getTextElements(parent.getElement(childIndex));
            childIndex++;
         }
      } // end for

      // Add the last bit of text.
      toTextArea = sanitize(toTextArea);
      text.append(toTextArea);

      // Handle tag-specific stuff
      endTag(parent.getType());


   } // end getTextElements()


   /**
    * Determines whether any sort of markup should be included before a tag,
    * and adds it if so.
    *
    * @param tag The tag to be handled.
    */
   private void startTag(HTMLTag tag)
   {
      switch(tag)
      {
         case H1:
         case H2:
         case H3:
         case H4:
         case H5:
         case DIV:
         case P:
            text.append("\n");
            break;
      }
   }


   /**
    * Determines whether any sort of markup should be included after a tag,
    * and adds it if so.
    *
    * @param tag The tag to be handled.
    */
   private void endTag(HTMLTag tag)
   {
      switch(tag)
      {
         case H1:
         case H2:
         case H3:
         case H4:
         case H5:
         case DIV:
         case P:
         case BR:
            text.append("\n");
      }
   }


   /**
    * Removes things such as &nbsp from text.
    *
    * @param in The String to be sanitized.
    * @return The sanitized String.
    */
   private String sanitize(String in)
   {
      String out = "";

      int i = 0;
      while(i < in.length())
      {
         if(in.charAt(i) != '&')
         {
            out += in.charAt(i);
            i++;
         }
         else
         {
            int next = in.indexOf(';', i) + 1;

            i = next > 0 ? next : i + 1;
            
            out += " ";
         }
      }

      // Bonus! Remove all leading and trailing whitespace except one space.
      return out.trim() + " ";
   }

} // end BrowserPanel
