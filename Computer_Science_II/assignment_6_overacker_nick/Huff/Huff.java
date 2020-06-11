/**
 * Encodes a given file through Huffman encoding.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 6.3
 */

import java.io.*;
import java.util.*;

public class Huff
{
   public static void main(String[] args)
   {
      // Variable declarations
      int[] byteFreq          = new int[0x100];
      DataInputStream in      = null;
      ObjectOutputStream out  = null;
      PriorityQueue<HuffmanNode> nodeQueue = new PriorityQueue<HuffmanNode>();
      HashMap<Integer, HuffmanNode> lookup = new HashMap<Integer, HuffmanNode>();
      

      // Makes sure we have proper input.
      if(args.length != 2)
      {
         System.out.println("USAGE: java Huff <infile> <outfile>");
         System.exit(1);
      }

      // Initialize the streams
      try
      {
         in = new DataInputStream(new FileInputStream(args[0]));
         out = new ObjectOutputStream(new FileOutputStream(args[1]));
      }
      catch(Exception e)
      {
         System.out.println("ERROR: An error has occurred opening the "
               + "in and out files.");
      }

      // Read in each byte, and store its frequency in an array.
      try
      {
         while(in.available() > 0)
         {
            byteFreq[in.readUnsignedByte()]++;
         }
      }
      catch(IOException e)
      {
         System.out.println("Error reading file.");
         System.exit(1);
      }

      // Build the tree, and leave it in nodeQueue.
      // Also, build a lookup table.
      buildTree(nodeQueue, byteFreq, lookup);

      // Encode the infile and write to the outfile.
      // First, we need a new input stream.
      try
      {
         in = new DataInputStream(new FileInputStream(args[0]));
      }
      catch(Exception e)
      {
         System.out.println("Well, this is embarrassing...");
         e.printStackTrace();
         System.exit(1);
      }

      // Compress
      compress(in, out, nodeQueue.poll(), lookup);
   }


   /**
    * Builds a Huffman tree from an array of byte frequencies
    * and a PriorityQueue.
    *
    * @param nodeQueue The PriorityQueue with which to build the tree.
    * @param byteFreq An array of byte frequencies.
    * @param lookup A HashMap for building a lookup table.
    */
   public static void buildTree(PriorityQueue<HuffmanNode> nodeQueue,
                                int[] byteFreq,
                                HashMap<Integer, HuffmanNode> lookup)
   {
      // Make a HuffmanNode from each byte, and store them in a PriorityQueue.
      for(int i = 0; i < byteFreq.length; i++)
      {
         if(byteFreq[i] > 0)
         {
            // Build the queue and the lookup table.
            HuffmanNode node = new HuffmanNode(i, byteFreq[i]);
            lookup.put(i, node);
            nodeQueue.offer(node);
         }
      }
      
      // Build a binary tree from the HuffmanNodes.
      while(nodeQueue.size() > 1)
      {
         // Leaves
         HuffmanNode left = nodeQueue.poll();
         HuffmanNode right  = nodeQueue.poll();
         
         // Root
         HuffmanNode parent =
           new HuffmanNode(0, left.getFrequency() + right.getFrequency());

         // Build tree
         parent.setChildren(left, right);

         // Add the tree back to the queue.
         nodeQueue.offer(parent);
      }
   }


   /**
    * Compresses input and writes to the give PrintStream.
    *
    * @param in The stream associated with the input to be compressed.
    * @param out The stream to which to write the output.
    * @param tree The Huffman tree to use in compression.
    * @param lookup The lookup table to use in compression.
    */
   public static void compress(DataInputStream in,
                               ObjectOutputStream out,
                               HuffmanNode tree,
                               HashMap<Integer, HuffmanNode> lookup)
   {
      // Read the file byte by byte, and compress byte by byte.
      // Store the compressed input into a List to be written
      // after we have all the information we need to put in
      // the header.
      LinkedList<Integer> encoded = new LinkedList<Integer>();
      int trailingBits = 0;  // the number of junk bits at the end
      int compByte = 0;   // the compressed byte
      int bitIndex = 0;      // the bit of compByte we are at; big-endian

      try
      {
         while(in.available() > 0)
         {
            // Get the next byte.
            int origByte = in.readUnsignedByte();
            HuffmanNode n = lookup.get(new Integer(origByte));
            
            // Get this byte's compressed bitstream.
            String val = "";
            do
            {
               val = n.getBitValue() + val;
            } while((n = n.getParent()) != null);
            
            // Remove the root bit from the bitstream.
            val = val.substring(1, val.length());

            // Add this to the output.
            for(int i = 0; i < val.length(); i++)
            {
               int bit = Integer.parseInt(val.substring(i, i+1)) << 7;

               compByte |= (bit >>> bitIndex++);

               // If we have encoded eight bits, add them to the List.
               if(bitIndex > 7)
               {
                  encoded.offer(new Integer(compByte));
                  compByte = bitIndex = 0;
               }
            } // end for
         } // end while

         // Okay, the whole thing is compressed in a LinkedList.
         // How many trailing bits will there be?
         trailingBits = bitIndex == 0 ? 0 : (8 - bitIndex);

         // Add the last byte.
         encoded.offer(new Integer(compByte));

         // Write the Huffman tree to the file, and then write the number
         // of trailing bits.
         out.writeByte(trailingBits);
         out.writeObject(tree);

         // Now, at last, write the encoded file.
         for(Integer b : encoded)
         {
            out.writeByte(b);
         }
         out.flush();
      }
      catch(IOException e)
      {
         System.out.println("Error reading or writing file.");
         System.exit(1);
      }
   }
}
