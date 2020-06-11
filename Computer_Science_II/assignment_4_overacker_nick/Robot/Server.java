import java.io.*;
import java.net.*;

public class Server
{
   public static void main(String[] args)
   {
      try {
         ServerSocket ss = new ServerSocket(2000);
         Socket s = ss.accept();
         BufferedReader in =
            new BufferedReader(new InputStreamReader(s.getInputStream()));
         char ch;
         while(true) {
            ch = (char) in.read();
            System.out.print(ch);
         }
      } catch(IOException e) {
         e.printStackTrace();
      }
   }
}
