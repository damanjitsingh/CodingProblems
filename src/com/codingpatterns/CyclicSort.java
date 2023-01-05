package com.codingpatterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This pattern describes an interesting approach to deal with problems involving arrays containing numbers in a given range.
 * For example, take the following problem:
 * You are given an unsorted array containing n numbers taken from the range 1 to n. The array can have duplicates,
 * which means that some numbers will be missing. Find all the missing numbers.
 * 
 * To efficiently solve this problem, we can use the fact that the input array contains numbers in the range of 1 to n. 
 * For example, to efficiently sort the array, we can try placing each number at its correct place, i.e., placing 1 at index '0',
 * placing 2 at index ‘1’, and so on. Once we are done with the sorting, we can iterate the array to find all indices missing the
 * correct numbers. These will be our required numbers.
 * @author damanjits
 *
 */
public class CyclicSort {
    /***************************************************************************************************************/
    /**
     * Sort the array in O(n) time and O(1) space.
     * nums with length n contains numbers from 1 to n.
     * 
     * 
     * Time complexity: O(n) + O(n-1). The additional O(n-1) is the maximum swaps in the worst case.
     * Space complexity: O(1)
     * 
     * @param nums
     */
    public static int[] cyclicSort(int[] nums) {
        int len = nums.length;
        int sIndex = 0;
        while (sIndex < len) {
            if (nums[sIndex] == sIndex + 1) {
                sIndex++;
            } else {
                // move value at sIndex to its correct position
                int destIndex = nums[sIndex]-1;
                int temp = nums[destIndex];
                nums[destIndex] = nums[sIndex];
                nums[sIndex] = temp;
            }
        }
        return nums;
    }

    /***************************************************************************************************************/
    /**
     * We are given an unsorted array containing numbers taken from the range 1 to ‘n’.
     * The array can have duplicates, which means some numbers will be missing. Find all those missing numbers.
     * Input: [2, 3, 1, 8, 2, 3, 5, 1]
       Output: 4, 6, 7
       Explanation: The array should have all numbers from 1 to 8, due to duplicates 4, 6, and 7 are missing.

     * @param nums
     * @return
     */
    public static List<Integer> findNumbers(int[] nums) {
        List<Integer> missingNumbers = new ArrayList<>();
        int len = nums.length;
        int sIndex = 0;
        while (sIndex < len) {
            if (nums[sIndex] == sIndex + 1) {
                sIndex++;
            } else {
                // move value at sIndex to its correct position
                int destIndex = nums[sIndex]-1;
                if (nums[destIndex] == nums[sIndex]) {
                    // if correct number is already there at destination
                    // this means the number at sIndex is a duplicate
                    sIndex++;
                } else {
                    // swap the number
                    int temp = nums[destIndex];
                    nums[destIndex] = nums[sIndex];
                    nums[sIndex] = temp;
                }
            }

            //System.out.println(Arrays.toString(nums));
        }

        // now the list is sorted
        for (int i=0; i<len; i++) {
            if (nums[i]-1 != i) {
                missingNumbers.add(i+1);
            }
        }
        return missingNumbers;
    }
    
    /***************************************************************************************************************/
    /**
     * We are given an unsorted array containing n numbers taken from the range 1 to n.
     * The array has some numbers appearing twice, find all these duplicate numbers using constant space.
     * @param nums
     * @return
     */
    public static List<Integer> findDuplicateNumbers(int[] nums) {
        List<Integer> duplicateNumbers = new ArrayList<>();
        int len = nums.length;
        int sIndex = 0;
        while (sIndex < len) {
            if (nums[sIndex] == sIndex + 1) {
                sIndex++;
            } else {
                // move value at sIndex to its correct position
                int destIndex = nums[sIndex]-1;
                if (nums[destIndex] == nums[sIndex]) {
                    // if correct number is already there at destination
                    // this means the number at sIndex is a duplicate
                    sIndex++;
                } else {
                    // swap the number
                    int temp = nums[destIndex];
                    nums[destIndex] = nums[sIndex];
                    nums[sIndex] = temp;
                }
            }

            //System.out.println(Arrays.toString(nums));
        }

        // now the list is sorted
        for (int i=0; i<len; i++) {
            if (nums[i]-1 != i) {
                duplicateNumbers.add(nums[i]);
            }
        }
        return duplicateNumbers;
    }
    /***************************************************************************************************************/

    public static void main(String[] args) {
        System.out.println(Arrays.toString(cyclicSort(new int[] {5,4,3,2,1})));

        System.out.println(findNumbers(new int[] {2,3,1,8,2,3,5,1}).toString());
        System.out.println(findNumbers(new int[] {1}).toString());

        System.out.println(findDuplicateNumbers(new int[] {2,3,1,8,2,3,5,1}).toString());
        System.out.println(findNumbers(new int[] {1}).toString());
    }

}
