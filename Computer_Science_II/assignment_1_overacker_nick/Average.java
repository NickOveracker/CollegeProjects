/**
 * Prompts the user to enter numbers until a negative value
 * is entered, and then returns the average of the input, except
 * for the negative value.
 *
 * Overacker, Nick
 *
 * Computer Science 2
 * Spring 2013
 * Assignment 1.2
 */

import java.util.Scanner;

public class Average
{
   public static void main(String[] args)
   {
      Scanner scanner             = new Scanner(System.in);
      boolean endOfInput          = false;
      double  currentInputValue   = 0;
      double  sumOfInputValues    = 0;
      double  overallAverage      = 0;
      int     numberOfInputValues = 0;

      System.out.println("Enter a series of numbers. Enter a negative "
            + "number to quit.");

      while(!endOfInput)
      {
         // Defensive programming stuff
         if(scanner.hasNextDouble())
         {
            currentInputValue = scanner.nextDouble();

            // If the value is not negative continue
            if(currentInputValue >= 0)
            {
               sumOfInputValues += currentInputValue;
               numberOfInputValues++;
            }
            else
            {
               endOfInput = true;
            }
         }  // end if
         else
         {  
            // Clear the buffer, completely ignoring non-numeric input.
            scanner.nextLine();
         }
      }  // end while

      overallAverage = sumOfInputValues / numberOfInputValues;

      System.out.println("You entered " + numberOfInputValues
            + " numbers averaging " + overallAverage);

   }  // end main

}  // end Average.java
