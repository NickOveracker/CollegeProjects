/**
 * Retrieves HTML from a web server.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 4.2
 */

import java.io.*;
import java.net.*;

public class Communicator
{
   private Socket socket;
   private PrintWriter out;
   private BufferedReader in;
   private DataOutputStream currentPageWriter;

   /**
    * Allows nongraphical web-browsing; primarily
    * for debugging purposes.
    */
   public static void main(String[] args)
   {
      String url = args.length > 0 ? args[0] : "http://cs.okstate.edu";
      try
      {
         new Communicator().downloadWebPage(url, "test");
      }
      catch(Exception e)
      {
      }
   }


   /**
    * Connects to a server and retrieves a page.
    *
    * @param _url The address of the page to be retrieved.
    * @param toFile The file to write the page source to.
    * @return The web page as a String.
    */
   public void downloadWebPage(String _url, String toFile) throws Exception
   {
      URL url;
      String currentLine;

      try
      {
         // Infrastructure
         url = new URL(_url);
         socket = new Socket(url.getHost(), 80);
         out = new PrintWriter(socket.getOutputStream());
         in = new BufferedReader(new InputStreamReader(url.openStream()));

         currentPageWriter
            = new DataOutputStream(new FileOutputStream(toFile));

         // Connect
         out.print("GET / HTTP/1.1\n");
         out.print("host: " + url.getHost() + "\n\n");
         out.flush();

         // Retrieve and store
         while((currentLine = in.readLine()) != null)
         {
            // Write this line to a local file.
            if(currentLine.length() > 0)
            {
               // Ignore characters that take more than one byte.
               // This is by far the easiest way to handle them.
               for(int i = 0; i < currentLine.length(); i++)
               {
                  if(currentLine.charAt(i) < 0xFF)
                  {
                     currentPageWriter.writeByte(currentLine.charAt(i));
                  }
               }
               // Finish with a new line.
               currentPageWriter.writeByte('\n');
            }
         }
      }
      catch(Exception e)
      {
         throw e;
      }
   }
}
