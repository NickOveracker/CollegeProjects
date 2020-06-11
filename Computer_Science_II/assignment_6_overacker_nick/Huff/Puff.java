/**
 * Decompresses files compressed by Huff.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 6.3
 */

import java.io.*;

public class Puff
{
   public static void main(String[] args)
   {
      // Variable declarations
      ObjectInputStream in  = null;
      PrintStream out       = null;
      HuffmanNode tree      = null;
      int trailingBits = 0;

      // Makes sure we have proper input.
      if(args.length != 2)
      {
         System.out.println("USAGE: java Puff <infile> <outfile>");
         System.exit(1);
      }

      // Initialize the streams
      try
      {
         in = new ObjectInputStream(new FileInputStream(args[0]));
         out = new PrintStream(new FileOutputStream(args[1]));
      }
      catch(Exception e)
      {
         System.out.println("ERROR: An error has occurred opening the "
               + "in and out files.");
         System.exit(1);
      }

      // Read in the Huffman tree, and the number of trailing bits.
      try
      {
         trailingBits = in.readByte();
         tree = (HuffmanNode) in.readObject();
      }
      catch(Exception e)
      {
         System.out.println(args[0] + " was not compressed with Huff.");
         e.printStackTrace();
         System.exit(1);
      }

      // Decompress and write to outfile.
      try
      {
         HuffmanNode node = tree;

         // Write to the outfile.
         while(in.available() > 0)
         {
            int byt = in.readUnsignedByte();
            int bitIndex = 0; // big-endian

            // Reset node if it is currently a leaf.
            if(node.isLeaf())
            {
               node = tree;
            }

            // Go down the binary tree bit by bit.
            // Account for the case where we are on the last byte as well.
            while(bitIndex < 8)
            {
               // Be wary of trailing bits, my son.
               if(in.available() == 0 &&
                     (trailingBits == 8 - bitIndex || trailingBits == 0))
               {
                  break;
               }

               switch((byt>>7) & 1)
               {
                  case 0:
                     node = node.getZeroChild();
                     break;
                  case 1:
                     node = node.getOneChild();
                     break;
               }

               // Shift the bits.
               byt <<= 1;
               byt &= 0xFF;
               bitIndex++;
               
               // Write the decompressed byte to the outfile.
               if(node.isLeaf())
               {
                  out.write(node.getByteValue());
                  node = tree;
               }
            }
         }

         out.flush();
      }
      catch(Exception e)
      {
         System.out.println("ERROR: Unable to decompress file.");
         e.printStackTrace();
         System.exit(1);
      }
   }
}
