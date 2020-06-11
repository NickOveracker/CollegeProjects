/**
 * A class that behaves roughly like an Iterator, but iterates through
 * permutations of a List rather than the contents of a List.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 6.2
 */

import java.util.*;

public class Permutations<E>
{
   private List<E> list;
   private E element;
   private Permutations<E> permutator = null;
   private int index = 0;

   /**
    * Test the permutations class.
    */
   public static void main(String[] args)
   {
      // Create an arbitrary List of Strings
      LinkedList<String> l = new LinkedList<String>();
      l.offer("fourscore");
      l.offer("and");
      l.offer("twenty");
      l.offer("years");

      // Create a Permutations object with the List.
      Permutations<String> p = new Permutations<String>(l);

      // Print what should be all 24 permutations.
      while(p.hasNext())
      {
         List<String> l2 = p.next();
         for(String s : l2)
         {
            System.out.print(s + " ");
         }
         System.out.println();
      }

      // Test the sorting method.
      System.out.println("---------------------------------------------");
      System.out.println("Brace yourself - activating permutation sort!");
      System.out.println("---------------------------------------------");

      try
      {
         for(int i = 1; ; i++)
         {
            System.out.println("Sorting " + i + " elements...");
            Integer[] a = new Integer[i];

            for(int j = 0; j < i; j++)
            {
               a[j] = new Integer((int) (Math.random() * 100));
            }

            sort(Arrays.asList(a));

            System.out.println("Sorted:");
            
            for(Integer j : a)
            {
               System.out.print(j + " ");
            }

            System.out.println("\n");
         }
      }
      catch(Exception e)
      {
         System.out.println("This is taking too long. I'm quitting.");
      }
   }


   /**
    * Sorts a List by permutation.
    *
    * I hope your computer is faster than mine.
    * 
    * @param l The Integer List to be sorted.
    * @throws Exception if sorting takes more than 20 seconds.
    */
   public static void sort(List<Integer> l) throws Exception
   {
      long time = System.currentTimeMillis();
      Permutations<Integer> p = new Permutations<Integer>(l);
      List<Integer> l2 = p.next();
      boolean sorted = false;

      while(!sorted)
      {
         // Timeout
         if(System.currentTimeMillis() - time > 2E4)
         {
            throw new Exception();
         }

         sorted = true;

         // See if it's sorted
         for(int i = 1; i < l2.size(); i++)
         {
            if(l2.get(i-1).compareTo(l2.get(i)) > 0)
            {
               sorted = false;
               break;
            }
         }

         // Handle the case in which it is sorted
         if(sorted)
         {
            // Copy the sorted order to the original List.
            for(int i = 0; i < l.size(); i++)
            {
               l.set(i, l2.get(i));
            }
         }
         else
         {
            l2 = p.next();
         }
      }
   }


   /**
    * Initialize the list variable and construct the
    * Permutations object.
    *
    * @param _list The list for which permutations will be
    *              generated.
    */
   public Permutations(List<E> _list)
   {
      // We don't want to work directly on the original list.
      list = new LinkedList<E>();
      list.addAll(_list);
      
      // Save the first element to a variable, and
      // remove it from the list.
      element = list.get(0);
      list.remove(0);

      // Recursive case: construct another Permutations object
      // with a smaller List.
      if(list.size() > 1)
      {
         permutator = new Permutations<E>(list);
         // Child permutators should start at index 1 so that
         // we don't repeat the initial configuration several times.
         permutator.index = 1;
      }
   }


   /**
    * Determines whether or not all permutations have been
    * generated.
    *
    * @return True if any permutations of the list remain,
    *         false otherwise.
    */
   public boolean hasNext()
   {
      // We have another iteration if we have not moved
      // element to each index (including list.size(),
      // since the whole list is one element larger),
      // or if the permutator has another permutation.
      return index <= list.size() || permutator != null && permutator.hasNext();
   }


   /**
    * Generates and returns the next permutation.
    *
    * @return The next permutation of the List.
    */
   public List<E> next()
   {
      if(!hasNext())
      {
         // By analogy with Iterators...
         throw new NoSuchElementException();
      }

      // If the index <= list.size(), just permute
      // at this level.
      if(index <= list.size())
      {
         // We'll return this thing.
         List<E> ret = new LinkedList<E>();
         ret.addAll(list);
         ret.add(index, element);
         index++;
         return ret;
      }
      else
      {
         // Permute at a lower level, reset the index.
         index = 0;
         List<E> ret = permutator.next();
         list.clear();
         list.addAll(ret);
         ret.add(index, element);
         index++;
         return ret;
      }
   }
}
