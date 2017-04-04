
/**
 Note: Write a solution with O(n2) time complexity, since this is what you would be asked to do during a real interview.

 You have an array a composed of exactly n elements. Given a number x, determine whether or not a contains three elements for which the sum is exactly x.

 Example

 For x = 15 and a = [14, 1, 2, 3, 8, 15, 3], the output should be
 tripletSum(x, a) = false;

 For x = 8 and a = [1, 1, 2, 5, 3], the output should be
 tripletSum(x, a) = true.

 The given array contains the elements 1,2, and 5, which add up to 8.

 Input/Output

 [time limit] 3000ms (java)
 [input] integer x

 Constraints:
 1 ≤ x ≤ 3000.

 [input] array.integer a

 Constraints:
 3 ≤ a.length ≤ 1000,

 1 ≤ a[i] ≤ 1000.

 [output] boolean

 Return true if the array contains three elements that add up to x and false otherwise.
*/

/** Found these in research:
 *  http://www.geeksforgeeks.org/find-a-triplet-that-sum-to-a-given-value/
 *  Basically to make it 0(n^3) you can just do a triple loop. To make it O(n^2) you sort the numbers and have two loops.
 *  The first loop goes through each number, and the inner loop compares two numbers at a time from the remainders to get rid of an n amount of complexity.
 */

import java.util.*;

public class TripletSum {

    boolean tripletSum(int x, int[] a) {

        // Sort the array
        Arrays.sort(a);

        // We're going to iterate over each entry using method in url above. Checking the left-most and right most in sorted array to see if we can find the sum for the third.
        int leftPointer, rightPointer;

        /* Now fix the first element one by one and find the other two elements */
        for (int fixedElement = 0; fixedElement < a.length - 2; fixedElement++)
        {
            // To find the other two elements, start two index variables from two corners of the array and move them toward each other.

            // Index of the first element in the remaining elements
            leftPointer = fixedElement + 1;

            // Index of the last element
            rightPointer = a.length - 1;

            while (leftPointer < rightPointer)
            {
                if (a[fixedElement] + a[leftPointer] + a[rightPointer] == x)
                {
                    // Debug
                    System.out.print("Triplet is " + a[fixedElement] + " ," + a[leftPointer] + " ," + a[rightPointer]);
                    return true;
                }
                else if (a[fixedElement] + a[leftPointer] + a[rightPointer] < x) {
                    leftPointer++;
                }
                else
                {
                    // A[i] + A[l] + A[r] > sum . No need for conditional as this is the only other case.
                    rightPointer--;
                }
            }
        }

        // If we reach here, then no triplet was found
        return false;
    }
}
