/**
 * This enumeration will give us more power
 * and flexibility over how to handle HTML tags.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 4.2
 */

import java.io.*;
import java.util.ArrayList;

public class Element
{
   // This will be used as a placeholder for subelements
   // in the text representation of an Element.
   public static char SUB_ELEMENT = '\0';

   // Contains all nontext subelements of this element,
   // such as <p> tags.
   private ArrayList<Element> subElements = new ArrayList<Element>();

   // Contains the text associated with this element.
   private String text = "";

   // This is true only if the closing tag is processed.
   private boolean isClosed = false;

   // This is used only while building the element.
   // It tells which index of subElements we are working with.
   // If -1, then we are working with this element itself.
   private int currentIndex = -1;

   // We need a way to get to the parent of any given element.
   // If there is no parent, this will be null.
   private Element parent = null;

   // This element's tag.
   private HTMLTag type;

  
   /**
    * Constructs a page element.
    *
    * @param _tag The type of element, such as "BODY" or "TITLE".
    */
   public Element(String _type)
   {
      type = HTMLTag.toHTMLTag(_type);

      // Some tags are self-closing.
      // Handle them.
      isClosed = type.isSelfClosing();

      // These need some special text representation.
      if(type == HTMLTag.BR || type == HTMLTag.HR)
      {
         text = "\n";
      }
   }


   /**
    * Parses a downloaded HTML page as an Element hierarchy.
    *
    * @return The webpage as an Element.
    */
   public static Element getPage(String infile)
   {
      BufferedReader in;
      String source = "";
      String inputLine;
      
      try
      {
         in = new BufferedReader(new FileReader(infile));
      }
      catch(FileNotFoundException e)
      {
         // Build a filler page in the event that no
         // page is downloaded.
         return getFillerPage();
      }

      // Read in the source and build an Element hierarchy.
      Element webPage = null;
      int lastIndex = 0;
      int nextIndex = 0;

      try
      {
         while((inputLine = in.readLine()) != null)
         {
            source += inputLine;
         }
         
         // Find the HTML tag, and start from there.
         lastIndex = source.toUpperCase().indexOf("<HTML");
         int nextSpace = source.indexOf(' ', lastIndex);
         int nextBrace = source.indexOf('>', lastIndex);
      
         // Make sure we start at the right place.
         if(nextSpace < nextBrace && nextSpace > 0)
         {
            nextIndex = nextSpace;
         }
         else
         {
            nextIndex = nextBrace;
         }

         webPage = new Element(source.substring(lastIndex + 1, nextIndex));

         // If we weren't already at the closing brace, go there.
         nextIndex = source.indexOf('>', lastIndex);

         // This loop will end when nextIndex fails to find the next tag.
         while(nextIndex > 0 )
         {
            // Add the text between two tags.
            lastIndex = nextIndex;
            nextIndex = source.indexOf('<', lastIndex);
            
            // If a second tag wasn't found, just add the text to the end of
            // the String.
            if(nextIndex > 0)
            { 
               webPage.addText(source.substring(lastIndex + 1, nextIndex));
            }
            else
            {
               // If we get here, then we are at the last part of the line.
               webPage.addText(source.substring(lastIndex + 1, source.length()));
               break;
            }

            // Add the next tag, which starts at nextIndex.
            //
            // We only want the first word in the angle brackets right now.
            // So, we should find out whether the next space or the next bracket
            // is closer.
            lastIndex = nextIndex;

            nextSpace = source.indexOf(' ', lastIndex);
            nextBrace = source.indexOf('>', lastIndex);
            
            if(nextSpace < nextBrace && nextSpace > 0)
            {
               nextIndex = nextSpace;
            }
            else
            {
               nextIndex = nextBrace;
            }

            // Determine whether to open or close a tag.
            if(source.charAt(lastIndex + 1) == '/')
            {
               webPage.closeTag(source.substring(lastIndex + 2, nextIndex));
            }
            else
            {
               webPage.openTag(source.substring(lastIndex + 1, nextIndex));
               
               // Comments must be given special attention.
               if(source.substring(lastIndex+1, lastIndex+4).equals("!--"))
               {
                  nextBrace = source.indexOf("-->", lastIndex) + 2;
               }

               // If we just opened a <script> tag, we should close it and
               // jump to the end.
               if(source.substring(lastIndex+1,lastIndex+7).equalsIgnoreCase("SCRIPT"))
               {
                  nextBrace = source.toUpperCase().indexOf("/SCRIPT", lastIndex) + 7;
                  webPage.closeTag("SCRIPT");
               }
            }

            // Move forward.
            nextIndex = nextBrace;
         }
      }
      catch(Exception e)
      {
         return getFillerPage();
      }

      return webPage;
   } // end getPage()


   /**
    * Returns the startup page.
    *
    * @return start.html, as an Element object.
    */
   public static Element getStartPage()
   {
      return getPage("start.html");
   }


   /**
    * Returns a filler element to handle the case
    * where no page is downloaded.
    *
    * This is hardcoded so no error can occur.
    *
    * @return An Element representing a filler page.
    */
   public static Element getFillerPage()
   {
      Element filler = new Element("HTML");
      filler.openTag("HEAD");
      filler.openTag("TITLE");
      filler.addText("No page downloaded");
      filler.closeTag("TITLE");
      filler.closeTag("HEAD");
      filler.openTag("BODY");
      filler.openTag("P");
      filler.addText("You seem to have made a terrible mistake. "
            + "I can't find the downloaded page!");
      filler.closeTag("P");
      filler.openTag("P");
      filler.addText("Of course, this couldn't possibly be the "
            + "programmer's fault. That would be unthinkable.");
      filler.closeTag("P");
      filler.openTag("P");
      filler.addText("You can fix this by entering a URL into the address "
            + "bar, and making sure that this fickle OSU connection is "
            + "working.");
      filler.closeTag("P");
      filler.openTag("P");
      filler.addText("HINT: Did you forget to include 'http://'?");
      filler.closeTag("P");
      filler.closeTag("BODY");
      filler.closeTag("HTML");

      return filler;
   }


   /**
    * Returns the title of the page, if possible.
    *
    * @return The text in the TITLE tags.
    */
   public String getTitle()
   {
      for(int i = 0; i < subElements.size(); i++)
      {
         // First find the head tag, and let that element
         // handle the title itself.
         if(subElements.get(i).type == HTMLTag.HEAD)
         {
            return subElements.get(i).getTitle();
         }
         if(subElements.get(i).type == HTMLTag.TITLE)
         {
            return subElements.get(i).text;
         }
      }

      // If we are out here, then there is no title.
      return "Untitled page";
   }


   /**
    * Returns the body element.
    *
    * @return The body element of the HTML page, if applicable, or null.
    */
   public Element getBody()
   {
      for(int i = 0; i < subElements.size(); i++)
      {
         if(subElements.get(i).type == HTMLTag.BODY)
         {
            return subElements.get(i);
         }
      }
      return null;
   }


   /**
    * Returns the type of this element.
    *
    * @return this.type.
    */
   public HTMLTag getType()
   {
     return type;
   } 


   /**
    * Returns an element of the subElement ArrayList.
    *
    * @param index The index of subElement to retrieve.
    * @return subElement.get(index) or null.
    */
   public Element getElement(int index)
   {
      // Return null if the index is out of bounds.
      if(index >= subElements.size())
      {
         return null;
      }

      // Else return the requested object.
      return subElements.get(index);
   }


   /**
    * Returns this.text, complete with null characters representing
    * the start of new page elements.
    *
    * @return this.text
    */
   public String getText()
   {
      return text;
   }


   /**
    * Adds visible text to this element.
    *
    * @param _text The visible text for this element.
    */
   public void addText(String _text)
   {
      // Make sure we're adding the text at the right level.
      if(currentIndex < 0 || subElements.get(currentIndex).isClosed)
      {
         text += _text;
      }
      else
      {
         // Recursively pass this down the ladder.
         subElements.get(currentIndex).addText(_text);
      }
   }


   /**
    * Handles opening tags, adding subelements.
    *
    * @param tag The all-caps String representation of the tag,
    *            without brackets or punctuation.
    */
   public void openTag(String tag)
   {
      // Make sure we're opening the tag at the right level.
      // This one method creates considerable infrastructure.
      if(currentIndex < 0 || subElements.get(currentIndex).isClosed)
      {
         // Add the element, and keep track of our current index.
         currentIndex++;
         subElements.add(currentIndex, new Element(tag));
         
         // Set this as the parent of the new element.
         subElements.get(currentIndex).parent = this;
         
         // Mark the subelement in the text.
         text += SUB_ELEMENT; 
      }
      else
      {
         // Recursively pass this down the ladder.
         subElements.get(currentIndex).openTag(tag);
      }
   }


   /**
    * Handle a closing tag.
    *
    * @param tag The all-caps String representation of the tag,
    *            without brackets or punctuation.
    */
   public void closeTag(String tag)
   {
      // Make sure this is handled at the right level.
      // Poorly written webpages will cause this code
      // to behave poorly.
      if(currentIndex >= 0 && !(subElements.get(currentIndex).isClosed))
      {
         subElements.get(currentIndex).closeTag(tag);
      }
      else if(tag.equalsIgnoreCase(type.name()) || type == HTMLTag.UNSUPPORTED)
      {
         isClosed = true;
      }
   }


   /**
    * Returns whether or not this element is closed.
    *
    * @return isClosed
    */
   public boolean isClosed()
   {
      return isClosed;
   }


   /**
    * Returns this.text.
    *
    * @return this.text.
    */
   public String toString()
   {
      return text;
   }


   /**
    * Returns a reference to the parent of this Element.
    *
    * @return this.parent.
    */
   public Element getParent()
   {
      return parent;
   }


   /**
    * Runs a series of tests on this class.
    */
   public static void main(String[] args)
   {
      System.out.println("Building infrastructure for HTML:"
           +" <HTML><BODY>Foobar</BODY></HTML>");
      Element el = new Element("HTML");
      el.openTag("BODY");
      el.addText("Foobar");
      el.closeTag("BODY");
      el.closeTag("HTML");

      System.out.println("Outer: " + el);
      System.out.println("Inner: " + el.subElements.get(0));

      System.out.println("\nBuilding infrastructure for HTML:");
      System.out.println("<HTML>\n"
            + "<HEAD><TITLE>Foobar</TITLE></HEAD>\n"
            + "<BODY><P>Quux<BR />XYZZY</P></BODY>\n"
            + "</HTML>");

      Element el2 = new Element("HTML");

      el2.openTag("HEAD");
      el2.openTag("TITLE");
      el2.addText("Foobar");
      el2.closeTag("TITLE");
      el2.closeTag("HEAD");
      el2.openTag("BODY");
      el2.openTag("P");
      el2.addText("Quux");
      el2.openTag("BR");
      el2.addText("XYZZY");
      el2.closeTag("P");
      el2.closeTag("BODY");
      el2.closeTag("HTML");

      System.out.println("\nRecursively printing the contents of this "
            + "infrastructure...");

      System.out.println("\nVerbose:");
      System.out.println(el2.debugString(true));
      System.out.println("\nNonverbose:");
      System.out.println(el2.debugString(false));


      System.out.println("\nBuilding current.html...");
      Element el3 = getPage("current.html");
      System.out.println(el3.debugString(true));
      System.out.println(el3.debugString(false));
   }


   /**
    * Prints the entire Element tree for debug purposes.
    * I don't comment debug stuff. Sorry.
    */
   public String debugString(boolean verbose)
   {
      String str = "";

      if(verbose)
      {
         str += type.name() + ": " + text + "\n";

         for(int i = 0; i <= currentIndex; i++)
         {
            str += subElements.get(i).debugString(verbose);
         }
         str += "END " + type.name() + "\n";
      }
      else
      {
         int se = 0;

         for(int i = 0; i < text.length(); i++)
         {
            if(text.charAt(i) != SUB_ELEMENT)
            {
               str += text.charAt(i);
            }
            else
            {
               str += subElements.get(se++).debugString(verbose);
            }
         }
      }

      return str;
   }


   /**
    * Returns the String associated with an Element.
    * FOR DEBUG PURPOSES ONLY
    */
   public String getTextDebug()
   {
      String str = "";
      int nextSub = 0;

      // Only return text that is inside a body.
      if(type == HTMLTag.HTML)
      {
         for(int i = 0; i < subElements.size(); i++)
         {
            if(subElements.get(i).type == HTMLTag.BODY)
            {
               return subElements.get(i).getTextDebug();
            }
         }
      }
      else
      {
         for(int i = 0; i < text.length(); i++)
         {
            if(text.charAt(i) != SUB_ELEMENT)
            {
               str += text.charAt(i);
            }
            else
            {
               if(subElements.get(nextSub).type != HTMLTag.UNSUPPORTED)
               {
                  str += subElements.get(nextSub).getTextDebug();
               }
               nextSub++;
            }
         }
      }
      return str;
   }

} // end Element
