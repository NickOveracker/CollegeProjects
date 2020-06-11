/**
 * Compares the relative efficiency of the bubble and merge sort methods,
 * and continues to test on arrays with increasing orders of magnitude
 * until sorting takes longer than twenty seconds with both methods.
 *
 * The running time of merge sort, O(nlogn), grows much more slowly than
 * the running time of bubble sort, with O(n^2) running time.
 *
 * The time required for merge sort appears to grow by about in orders of
 * magnitude at nearly the same rate as the input (for the level of input
 * allowed by memory constraints), while the time required for bubble sort
 * increases and accelerates rapidly as the input size increases. 
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 5.3
 */

import java.util.Arrays;

public class Sorting
{
   public static final long TIMEOUT = 20000;
   public static boolean bubbleTimeOut = false;
   public static boolean mergeTimeOut = false;

   public static void main(String[] args)
   {
      int n = 10;

      // Continue as long as at least one sort hasn't
      // timed out.
      // The loop may be broken from within by an exception
      // while generating arrays.
      while(!(bubbleTimeOut && mergeTimeOut))
      {
         try
         {
            // Generate the arrays.
            double[] arr1 = generateArray(n);
            double[] arr2;

            // If neither has timed out, follow this path.
            if(!(bubbleTimeOut || mergeTimeOut))
            {
               arr2 = Arrays.copyOf(arr1, arr1.length);
               testBubbleSort(arr1);
               testMergeSort(arr2);
            }
            // Otherwise, test this path
            else if(!bubbleTimeOut)
            {
               testBubbleSort(arr1);
            }
            // Finally, test only merge sort.
            else
            {
               testMergeSort(arr1);
            }
         }
         catch(OutOfMemoryError e)
         {
            System.out.println("Out of memory at n=" + n);
            System.exit(1);
         }

         // Move up one order of magnitude.
         n *= 10;
      }
   }

   
   /**
    * Generates a random double array of length n.
    *
    * @param n The length of the array.
    */
   public static double[] generateArray(int n)
   {
      double[] arr = new double[n];

      for(int i = 0; i < arr.length; i++)
      {
         arr[i] = Math.random();
      }

      return arr;
   }


   /**
    * Sorts a double array with bubble sort, and returns the elapsed time.
    * Exits early if the sort takes longer than 20 seconds.
    *
    * @param arr The array to be sorted.
    * @return The elapsed time.
    */
   public static long bubbleSort(double[] arr)
   {
      long startTime = System.currentTimeMillis();
      boolean swapped;

      // Outer loop - go through array
      for(int i = 0; i < arr.length; i++)
      {
         // Stop if we have taken more than 20 seconds.
         if(System.currentTimeMillis() - startTime > TIMEOUT)
         {
            break;
         }

         swapped = false;

         // Inner loop - check all elements after i.
         for(int j = arr.length-1; j > i; j--)
         {
            // Swap values, if applicable.
            if(arr[j] < arr[j-1])
            {
               double x = arr[j];
               arr[j] = arr[j-1];
               arr[j-1] = x;
               swapped = true;
            }
         }

         // If we haven't swapped anything, then the array is sorted.
         if(!swapped)
         {
            break;
         }
      }

      // Return the elapsed time.
      return System.currentTimeMillis() - startTime;
   }


   /**
    * Sorts a double array with merge sort, and returns the elapsed time.
    * Exits early if the sort takes longer than 20 seconds.
    *
    * @param arr The array to be sorted.
    * @param startTime The time that the sort began; 0 for first call.
    * @return The elapsed time.
    */
   public static long mergeSort(double[] arr, long startTime)
   {
      // First case: Set the start time
      if(startTime == 0)
      {
         startTime = System.currentTimeMillis();
      }

      // Have we taken too long?
      if(System.currentTimeMillis() - startTime > TIMEOUT)
      {
         return System.currentTimeMillis() - startTime;
      }

      // Base case:
      // If the range has one or zero elements, we're done.
      if(arr.length <= 1)
      {
         // If we are here, then it either really did take
         // 0 ms to sort, or this number will be thrown away
         // anyway. So, I will just send 0 so as not to slow
         // down the algorithm with pointless computation.
         // 
         // Testing has shown that this does measureably
         // improve the algorithm's efficiency.
         return 0;
      }

      // Recursive case
      double[] left = Arrays.copyOfRange(arr, 0, arr.length/2);
      double[] right = Arrays.copyOfRange(arr, arr.length/2, arr.length);

      // Sort the two parts separately.
      mergeSort(left,  startTime);
      mergeSort(right, startTime);

      // Merge the two parts.
      int i = 0;
      int j = 0;
      int k = 0;
      
      // Nasty comparisons and stuff.
      // Do while there are elements left in either array.
      while(i < left.length || j < right.length)
      {
         // If both arrays have elements left...
         if(i < left.length && j < right.length)
         {
            // Sort these elements.
            if(left[i] <= right[j])
            {
               arr[k] = left[i];
               i++;
            }
            else if(right[j] < left[i])
            {
               arr[k] = right[j];
               j++;
            }
         }
         // Otherwise, just add the remaining elements.
         else if(i < left.length)
         {
            arr[k] = left[i];
            i++;
         }
         else if(j < right.length)
         {
            arr[k] = right[j];
            j++;
         }
         // Increment the main array index.
         k++;
      }

      return System.currentTimeMillis() - startTime;
   }


   /**
    * Tests the bubbleSort method and prints results.
    *
    * @param arr The array to be sorted.
    */
   public static void testBubbleSort(double[] arr)
   {
      long bubbleSortTime = bubbleSort(arr);

      // Have we timed out?
      if(bubbleSortTime > TIMEOUT)
      {
        bubbleTimeOut = true; 
        System.out.println("Bubble sort has timed out at "
              + arr.length + " elements.");
      }
      else
      {
         System.out.println("Bubble sorting " + arr.length + " elements "
               + "took " + bubbleSortTime + " ms.");
      }
   }
      

   /**
    * Tests the mergeSort method and prints results.
    *
    * @param arr The array to be sorted.
    */
   public static void testMergeSort(double[] arr)
   {
      long mergeSortTime = mergeSort(arr, 0L);

      // Have we timed out?
      if(mergeSortTime > TIMEOUT)
      {
        mergeTimeOut = true; 
        System.out.println("Merge sort has timed out at "
              + arr.length + " elements.");
      }
      else
      {
         System.out.println("Merge sorting " + arr.length + " elements "
               + "took " + mergeSortTime + " ms.");
      }
   }
}
