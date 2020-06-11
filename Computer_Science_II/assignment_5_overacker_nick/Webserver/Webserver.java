/**
 * A simple webserver that serves files in the subdirectory
 * ./website.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 5.2
 */

import java.io.*;
import java.net.*;

public class Webserver implements Runnable
{
   // Server responses
   public static final String OK          = "HTTP/1.1 200 OK\r\n";
   public static final String BAD_REQUEST = "HTTP/1.1 400 Bad Request\r\n\r\n";
   public static final String NOT_FOUND   = "HTTP/1.1 404 Not Found\r\n\r\n";
   public static final String SERV_ERROR  = "HTTP/1.1 500 "
                                             + "Internal Server Error\r\n\r\n";

   // Content types
   public static final String TEXT_HTML   = "Content-type: text/html\r\n\r\n";
   public static final String TEXT_PLAIN  = "Content-type: text/plain\r\n\r\n";
   public static final String IMAGE_JPEG  = "Content-type: image/jpeg\r\n\r\n";
   public static final String IMAGE_GIF   = "Content-type: image/gif\r\n\r\n";
   public static final String IMAGE_PNG   = "Content-type: image/png\r\n\r\n";

   // The directory containing the website
   public static final String ROOT_DIRECTORY = "./website";
   
   // The default page (used when the request ends with '/')
   public static final String DEFAULT_PAGE = "index.html";

   // Socket to client
   private Socket socket;


   /**
    * Runs an infinite loop, opening a new Thread for every
    * client connection.
    */
   public static void main(String[] args)
   {
      try
      {
         ServerSocket ss = new ServerSocket(1992);
         while(true)
         {
            Thread connection =
               new Thread(new Webserver(ss.accept()));
            connection.start();
         }
      }
      catch(IOException e)
      {
         e.printStackTrace();
      }
   }


   /**
    * Constructs a Webserver object by initializing the Socket.
    *
    * @param _socket The Socket to the client.
    */
   public Webserver(Socket _socket)
   {
      socket = _socket;
   }


   /**
    * Serves the requested webpage to the client,
    * if permissible.
    */
   public void run()
   {
      String request = "";
      BufferedReader in = null;
      PrintWriter out = null;

      try
      {
         // IO objects
         in = new BufferedReader(
               new InputStreamReader(socket.getInputStream()));
         out = new PrintWriter(socket.getOutputStream());
         
         // Read the request
         request = in.readLine();
      }
      catch(IOException e)
      {
         // If we get here, there's not much we can do.
         e.printStackTrace();
         close();
         return;
      }

      // Parse the request
      if(request != null)
      {
         System.out.println(request);

         // Start at the beginning of GET,
         // just in case there is any junk before it.
         int start = request.indexOf("GET");

         // Make sure that "GET" is actually in the String.
         if(start < 0)
         {
            // If "GET" is not in the request, this is a bad request.
            out.print(BAD_REQUEST);
            out.flush();
            close();
            return;
         }
         
         // Move our starting point right after "GET ", which
         // should put us at the beginning of the filename.
         start += "GET ".length();

         // Get the end of the filename, which should be at the next space.
         int end = request.indexOf(" ", start);

         // Make sure that this gives us a valid index.
         if(end < 0)
         {
            // If the index wasn't found, this is a bad request.
            out.print(BAD_REQUEST);
            out.flush();
            close();
            return;
         }

         // Now get the actual file.
         String filename = request.substring(start, end);

         // Make sure that no malicious user attempts to browse my
         // filesystem. Redirect all requests including ".." to the index.
         // Likewise, redirect if the requested page is just "/".
         if(filename.indexOf("..") > 0)
         {
            filename = ROOT_DIRECTORY + DEFAULT_PAGE;
            System.out.println(filename);
         }
         // If it's a directory, go to the directory index.
         else if(new File(ROOT_DIRECTORY + filename).isDirectory())
         {
            filename = ROOT_DIRECTORY + filename + DEFAULT_PAGE;
         }
         // If the request is okay, just redirect it to the proper directory.
         else
         {
            filename = ROOT_DIRECTORY + filename;
         }

         // If it's an HTML file, handle appropriately
         if(filename.toUpperCase().endsWith(".HTML"))
         {
            sendTextFile(filename, TEXT_HTML, out);
         }
         // If it's a JPEG, handle appropriately.
         else if(filename.toUpperCase().endsWith("JPEG")
               || filename.toUpperCase().endsWith("JPG"))
         {
            sendImageFile(filename, IMAGE_JPEG);
         }
         // PNG
         else if(filename.toUpperCase().endsWith("PNG"))
         {
            sendImageFile(filename, IMAGE_PNG);
         }
         // GIF
         else if(filename.toUpperCase().endsWith("GIF"))
         {
            sendImageFile(filename, IMAGE_GIF);
         }
         // If none of these, send as plaintext
         else
         {
            sendTextFile(filename, TEXT_PLAIN, out);
         }
      }
   }


   /**
    * Sends an HTML file to the client.
    *
    * @param filename The name of the file to send.
    * @param filetype The type of file to send.
    * @param out The PrintWriter object to be used.
    */
   public void sendTextFile(String filename, String filetype, PrintWriter out)
   {
      // Get and send the file.
      BufferedReader fileIn = null;
      String toSend = "";
      try
      {
         // Get the requested file
         fileIn = new BufferedReader(new FileReader(filename));
         String line;

         // Handshake
         toSend += OK + TEXT_HTML;

         // Read and add to the output String.
         while((line = fileIn.readLine()) != null)
         {
            toSend += line + '\n';
         }
      }
      catch(FileNotFoundException e)
      {
         out.print(NOT_FOUND);
         out.flush();
         close();
         return;
      }
      catch(Exception e)
      {
         out.print(SERV_ERROR);
         out.flush();
         close();
         return;
      }

      // Send the file
      out.print(toSend);
      out.flush();
      close();
   }


   /**
    * Sends a JPEG file to the client.
    * Does not take a PrintWriter argument because
    * a PrintStream is created instead.
    *
    * @param filename The name of the file to send.
    * @param filetype The type of the file to send.
    */
   public void sendImageFile(String filename, String filetype)
   {
      // Get and send the file.
      PrintStream out = null;
      DataInputStream fileIn = null;

      try
      {
         // Get the requested file
         fileIn = new DataInputStream(new FileInputStream(filename));
         out = new PrintStream(socket.getOutputStream());

         byte datum;

         // Handshake
         out.print(OK + filetype);

         // Read and send the file
         while(fileIn.available() > 0)
         {
            datum = fileIn.readByte();
            out.write(datum);
         }
      }
      catch(EOFException e)
      {
         // Who cares?
      }
      catch(FileNotFoundException e)
      {
         out.print(NOT_FOUND);
         out.flush();
         close();
         return;
      }
      catch(Exception e)
      {
         out.print(SERV_ERROR);
         out.flush();
         close();
         return;
      }

      // Send the file
      out.flush();
      close();
  }


   /**
    * Closes the socket.
    *
    * This is here so I don't have to use a try-catch block every
    * time I want to close the socket.
    */
   public void close()
   {
      try
      {
         socket.close();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }
}
