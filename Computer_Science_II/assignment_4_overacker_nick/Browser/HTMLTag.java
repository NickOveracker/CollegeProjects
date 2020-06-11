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

public enum HTMLTag
{
   // This is not an exhaustive list, but includes most of the tags
   // I need to include for most of a page's content to display.
   A,
   AREA,
   B,
   BASE,
   BODY,
   BR,
   CENTER,
   COL,
   COMMAND,
   DIV,
   EM,
   EMBED,
   H1, H2, H3, H4, H5,
   HEAD,
   HR,
   HTML,
   I,
   IMG,
   INPUT,
   KEYGEN,
   LI,
   LINK,
   META,
   OL,
   P,
   PARAM,
   SOURCE,
   STRONG,
   TABLE,
   TD,
   TITLE,
   TR,
   TRACK,
   U,
   UL,
   WBR,
   UNSUPPORTED,
   UNSUPPORTED_SELF_CLOSING;

   /**
    * Converts the String representation of
    * an HTML tag to a Tag object.
    *
    * @param tag The all-caps tag, without brackets or punctuation.
    * @return The Tag-object representation of the given tag.
    */
   public static HTMLTag toHTMLTag(String tag)
   {
      try
      {
         // Some tags are special and need to be handled separately.
         // The ones that start with ! can be handled generally.
         if(tag.startsWith("!"))
         {
            return UNSUPPORTED_SELF_CLOSING;
         }
         // Some self-closing tags may be written without a space.
         if(tag.endsWith("/"))
         {
            return toHTMLTag(tag.substring(0, tag.length() - 1));
         }

         return valueOf(tag.toUpperCase());
      }
      catch(Exception e)
      {
         return HTMLTag.UNSUPPORTED;
      }
   }


   /**
    * Returns whether or not a given tag is self-closing.
    *
    * @return True if self-closing.
    */
   public boolean isSelfClosing()
   {
      switch(this)
      {
         case UNSUPPORTED_SELF_CLOSING:
         case AREA:
         case BASE:
         case BR:
         case COL:
         case COMMAND:
         case EMBED:
         case HR:
         case IMG:
         case INPUT:
         case KEYGEN:
         case LINK:
         case META:
         case PARAM:
         case SOURCE:
         case TRACK:
         case WBR:
            return true;
         default:
            return false;
      }
   }
}
