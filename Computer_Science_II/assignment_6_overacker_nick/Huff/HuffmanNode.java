/**
 * A binary-tree unit for compressing and decompressing data.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 6.3
 */

import java.io.*;
import java.util.*;

public class HuffmanNode implements Comparable, Serializable
{
   private int byteValue;
   private int bitValue;
   private int frequency;
   private HuffmanNode parent    = null;
   private HuffmanNode zeroChild = null;
   private HuffmanNode oneChild  = null;


   /**
    * Runs a simple test on the HuffmanNode class.
    */
   public static void main(String[] args)
   {
      HuffmanNode m = new HuffmanNode(3, 4);
      HuffmanNode n = new HuffmanNode(7, 8);
      HuffmanNode o = new HuffmanNode(1, 2);
      HuffmanNode p = new HuffmanNode(5, 6);

      PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>();
      q.offer(n);
      q.offer(m);
      q.offer(o);
      q.offer(p);

      System.out.println("Top of queue has value of " + q.peek().getByteValue()
            + ", expected 1");

      while(q.size() > 1)
      {
         HuffmanNode a = q.poll();
         HuffmanNode b = q.poll();
         HuffmanNode r = new HuffmanNode(0, a.getFrequency() + b.getFrequency());
         r.setChildren(a, b);
         q.offer(r);
      }

      HuffmanNode r = q.poll();
      HuffmanNode s = r;

      System.out.println("Top of tree has frequency of " + r.getFrequency()
            + ", expected 20");

      int i = 0;
      
      while(s.zeroChild != null)
      {
         s = s.getZeroChild();
         System.out.println(i++);
      }

      System.out.println("The last zero child has a bitValue of " + s.getBitValue());
      System.out.println("and a byteValue of " + s.getByteValue());

      s = r;

      i = 0;
      while(s.oneChild != null)
      {
         s = s.getOneChild();
         System.out.println(i++);
      }

      System.out.println("The last one child has a bitValue of " + s.getBitValue());
      System.out.println("and a byteValue of " + s.getByteValue());

      if(s.isLeaf())
      {
         System.out.println("s is a leaf.");
      }
      if(r.isLeaf())
      {
         System.out.println("Error: r is a leaf.");
      }
   }

   
   /**
    * Constructs a parentless and childless HuffmanNode by value.
    *
    * @param _byteValue The uncompressed byte value of this node;
    *                   ignored if this node has children.
    * @param _frequency The number of times this byte occurs.
    */
   public HuffmanNode(int _byteValue, int _frequency)
   {
      byteValue = _byteValue;
      frequency = _frequency;
   }


   /**
    * Assigns children to a HuffmanNode.
    *
    * @param zero The left child, having a bit value of zero.
    * @param one The right child, having a bit value of one.
    */
   public void setChildren(HuffmanNode zero, HuffmanNode one)
   {
      zeroChild = zero;
      zero.setParent(this);
      zero.bitValue = 0;

      oneChild = one;
      one.setParent(this);
      one.bitValue = 1;
   }


   /**
    * Assigns a parent to a HuffmanNode; cannot be called explicitly.
    *
    * @param _parent The HuffmanNode to be set as this.parent.
    */
   private void setParent(HuffmanNode _parent)
   {
      parent = _parent;
   }


   /**
    * Returns the number of times the byte represented by this node occurs,
    * or the number of times that the bytes represented by this node's
    * children occur.
    *
    * @return this.frequency
    */
   public int getFrequency()
   {
      return frequency;
   }


   /**
    * Returns whether or not this node is a leaf.
    *
    * @return True if this is a leaf, false if not.
    */
   public boolean isLeaf()
   {
      return zeroChild == null;
   }


   /**
    * Gets the parent node.
    *
    * @return this.parent
    */
   public HuffmanNode getParent()
   {
      return parent;
   }


   /**
    * Gets the child node with a bit value of zero.
    *
    * @return The child with a bit value of zero.
    */
   public HuffmanNode getZeroChild()
   {
      return zeroChild;
   }


   /**
    * Gets the child node with a bit value of one.
    *
    * @return The child with a bit value of one.
    */
   public HuffmanNode getOneChild()
   {
      return oneChild;
   }


   /**
    * Returns the bit value of this node.
    * Warning! If the node is not a leaf, the program will crash!
    *
    * @return The bit value (1 or 0) of this node.
    */
   public int getBitValue()
   {
      return bitValue;
   }


   /**
    * Returns the decompressed byte value of a leaf.
    * Warning! If the node is not a leaf, the program will crash!
    *
    * @return this.byteValue
    */
   public int getByteValue()
   {
      return byteValue;
   }


   /**
    * Compares this HuffmanNode with the specified HuffmanNode for order.
    *
    * @param node The HuffmanNode to compare with.
    * @return A negative number if this should come before node,
    *         a positive number if this should come after node,
    *         zero if this == node,
    *         zero if node is not a HuffmanNode.
    */
   public int compareTo(Object node)
   {
      if(node instanceof HuffmanNode)
      {
         return (int) Math.signum(frequency - ((HuffmanNode)node).frequency);
      }
      return 0;
   }
}

