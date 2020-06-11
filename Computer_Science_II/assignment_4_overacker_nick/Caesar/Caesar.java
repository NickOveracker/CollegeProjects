/**
 * Encrypts and decrypts text files using a Vignere cipher.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 4.1
 */

import java.io.*;

public class Caesar
{
   // Don't encipher characters outside these bounds.
   public static char MIN_CHAR = (char) 32;
   public static char MAX_CHAR = (char) 126;
   public static int RANGE     = ((MAX_CHAR + 1) - MIN_CHAR);
   
   /**
    * Reads in the command-line arguments and
    * encodes the input file.
    *
    * @param args args[0] is the encoding keyword; this must be specified.
    *             If preceded by a hyphen, the infile will be deciphered.
    *
    *             args[1] is the input file; this must be specified.
    *             
    *             args[2] is the output file; if not specified, output
    *                will be printed to the terminal by default.
    */
   public static void main(String[] args)
   {
      // Check the input. Two fields are mandatory.
      if(args.length < 2)
      {
         System.out.println("USAGE:  java Caesar <key> <infile> [outfile]");
         System.out.println("        To decrypt, precede the decryption "
                                   + "key with a hyphen.");
         System.exit(0);
      }

      // Read in applicable arguments.
      String key = args[0];
      String infile = args[1];
      String outfile = args.length >= 3 ? args[2] : "";
      
      // If the key starts with '-', decrypt instead of encrypting.
      if(key.charAt(0) == '-')
      {
         decipher(key.substring(1), infile, outfile);
      }
      else
      {
         encipher(key, infile, outfile);
      }
   }


   /**
    * Encrypts a file with the Vignere cipher.
    *
    * @param key The key to be used.
    * @param infile The existing file to be encrypted.
    * @param outfile The file to save the encrypted file to;
    *                 if not specified, the file will be printed to the screen.
    */
   public static void encipher(String key, String infile, String outfile)
   {
      // Initialize all objects as null.
      // Each of these will be checked before use.
      String inputLine = null;
      String outputLine = null;
      BufferedReader in = null;
      DataOutputStream out = null;

      // Set the BufferedReader "in"
      // and, if applicable, the DataOutputStream "out".
      try
      {
         in = new BufferedReader(new FileReader(infile));
         if(outfile.length() > 0)
         {
            out = new DataOutputStream(new FileOutputStream(outfile));
         }
      }
      catch(FileNotFoundException e)
      {
         System.out.println("ERROR:  File not found.");
         System.exit(1);
      }
      
      // Read and encipher input,
      // and send to either out or the terminal.
      do
      {
         try
         {
            inputLine = in.readLine();
            outputLine = vignere(key, inputLine);

            // The final line will be null.
            if(outputLine != null)
            {
               // Decide whether to use out or the terminal.
               if(out != null)
               {
                  for(int i = 0; i < outputLine.length(); i++)
                  {
                     // Chars are two bytes, but I only want to write one byte.
                     out.writeByte((byte) outputLine.charAt(i));
                  }
                  out.writeByte((byte) '\n');
                  out.flush();
               }
               else
               {
                  System.out.println(outputLine);
               }
            }
         }
         catch(Exception e)
         {
            System.out.println("An error has occurred.");
            System.exit(1);
         }
      }
      while(inputLine != null);
   }


   /**
    * Deciphers a file with the Vignere cipher.
    *
    * @param key The key to be used.
    * @param infile The existing file to be deciphered.
    * @param outfile The file to save the deciphered file to;
    *                 if not specified, the file will be printed to the screen.
    */
    public static void decipher(String key, String infile, String outfile)
    {
       // This is really simple - just convert the key to the reverse of the
       // encryption key, then call "encipher". Deciphering and enciphering
       // are really the same thing.
       String reverseKey = "";

       // Do the actual work.
       for(int i = 0; i < key.length(); i++)
       {
          int charVal =  key.charAt(i);

          // I'll be honest with you. This took me way too long to
          // perfect.
          charVal = MAX_CHAR + 1 - MIN_CHAR - charVal;

          // charVal is likely to be far less than MIN_CHAR
          while(charVal < MIN_CHAR)
          {
             charVal = MAX_CHAR - MIN_CHAR + charVal + 1;
          }

          reverseKey += (char) charVal;
       }

       // Yay!
       encipher(reverseKey, infile, outfile);
    }


    /**
     * Returns a the encrypted form of a given String.
     *
     * @param key The key to be used.
     * @param source The String to be encrypted.
     */
    public static String vignere(String key, String source)
    {
       // Don't try to encrypt null Strings.
       if(source == null)
       {
          return null;
       }

       // Make sure the key has only acceptable characters.
       for(int i = 0; i < key.length(); i++)
       {
          if(key.charAt(i) < MIN_CHAR || key.charAt(i) > MAX_CHAR)
          {
             System.out.println("The key includes illegal characters. "
                   + "You have been reported to the proper authorities.");

             System.exit(1);
          }
       }

       String encrypted = "";

       // Build the encrypted String.
       while(encrypted.length() < source.length())
       {
          // Cycle through the characters of key, and encrypt appropriately.
          for(int i = 0;
                i < key.length() && encrypted.length() < source.length();
                i++)
          {
             // Get the next character to encrypt.
             char current = source.charAt(encrypted.length());

             // If this character uses two bytes, there will
             // be a problem. Make sure the user knows this, but
             // continue as normal.
             if(current > 0xFF)
             {
                System.out.println("Error: The input file contains "
                      + "illegal characters.\n"
                      + "Cipering aborted.");

                System.exit(0);
             }

             // Do not encrypt characters outside of our range.
             if(current < MIN_CHAR || current > MAX_CHAR)
             {
                encrypted += current;
                continue;
             }
             
             // Encrypt.
             current += key.charAt(i);

             // Make sure it's within our chosen range. If not, cycle it back 
             // into our range.
             while(current > MAX_CHAR)
             {
                current = (char) (current - MAX_CHAR + MIN_CHAR - 1);
             }

             // Add to the String.
             encrypted += current;
          }
       }
       return encrypted;
    }

} // end Caesar
