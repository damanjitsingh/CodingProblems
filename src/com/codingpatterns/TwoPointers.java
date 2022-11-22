package com.codingpatterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwoPointers {
	
	/**
	 * Given an array of sorted numbers and a target sum, find a pair in the array whose sum is equal to the given target.
	Write a function to return the indices of the two numbers (i.e. the pair) such that they add up to the given target.
	 * @param arr
	 * @param targetSum
	 * @return
	 */
    public static int[] search(int[] arr, int targetSum) {
	    int left = 0, right = arr.length - 1;
	    while (left < right) {
	      int currentSum = arr[left] + arr[right];
	      if (currentSum == targetSum)
	        return new int[] { left, right }; // found the pair

	      if (targetSum > currentSum)
	        left++; // we need a pair with a bigger sum
	      else
	        right--; // we need a pair with a smaller sum
	    }
	    return new int[] { -1, -1 };
	  }
	
	/***************************************************************************************************************/
	/**
	 * Given an array of unsorted numbers, find all unique triplets in it that add up to zero.
	 * Input: [-3, 0, 1, 2, -1, 1, -2]
    Output: [-3, 1, 2], [-2, 0, 2], [-2, 1, 1], [-1, 0, 1]
    Explanation: There are four unique triplets whose sum is equal to zero.
	 */
    public static List<List<Integer>> searchTriplets(int[] arr) {
        Arrays.sort(arr);
        List<List<Integer>> res = new ArrayList<>();
        int len = arr.length;
        for (int i=0; i<len; i++) {
            findTargetSum(arr, i+1, (-1 * arr[i]), res);
        }
        return res;
    }

    private static void findTargetSum(int[] arr, int startIndex, int target, List<List<Integer>> res) {
        int left = startIndex;
        int right = arr.length-1;
        while (left < right) {
            int leftVal = arr[left];
            int rightVal = arr[right];
            int sum = leftVal + rightVal;
            if (sum == target) {
                List<Integer> l = new ArrayList<>();
                l.add((-1*target));
                l.add(leftVal);
                l.add(rightVal);
                res.add(l);
                left++;
                right--;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
    }

    /***************************************************************************************************************/
    /**
     * Given an array of unsorted numbers and a target number, find a triplet in the array whose sum is as close to the
     * target number as possible, return the sum of the triplet. If there are more than one such triplet, return the sum
     * of the triplet with the smallest sum.
     */
    public static int searchTripletCloseSum(int[] arr, int targetSum) {
        int len = arr.length;
        if (len == 3) return arr[0] + arr[1] + arr[2];
        Arrays.sort(arr);
        int resSum = Integer.MAX_VALUE;
        for (int i=0; i<=len-3; i++) {
            int sum = findTargetSumClose(arr, i+1, targetSum - arr[i], arr[i]);
            int currentDiff = Math.abs(targetSum - sum);
            int minDiff = Math.abs(targetSum - resSum);
            if (currentDiff < minDiff) {
                resSum = sum;
            } else if (currentDiff == minDiff) {
                resSum = Math.min(sum, resSum);
            }
        }
        return resSum;
    }

    private static int findTargetSumClose(int[] arr, int startIndex, int target, int curVal) {
        int left = startIndex;
        int right = arr.length-1;
        int minDiff = Math.abs(target - (arr[left] + arr[right]));
        int res = curVal + arr[left] + arr[right];
        while (left < right) {
            int leftVal = arr[left];
            int rightVal = arr[right];
            int sum = leftVal + rightVal;
            if (sum == target) {
                res = leftVal + rightVal + curVal;
                break;
            } else if (sum < target) {
                int curMinDiff = Math.abs(target - (arr[left] + arr[right]));
                if (curMinDiff < minDiff) {
                    res = curVal + leftVal + rightVal;
                }
                left++;
            } else {
                int curMinDiff = Math.abs(target - (arr[left] + arr[right]));
                if (curMinDiff < minDiff) {
                    res = curVal + leftVal + rightVal;
                }
                right--;
            }
        }
        return res;
    }

    /***************************************************************************************************************/
    static public class SmallerSumCount{
        int res = 0;
    }
    /**
     * 
     * @param arr
     * @param target
     * @return
     */
    public static int searchTripletsSmallerSumCount(int[] arr, int target) {
        SmallerSumCount count = new SmallerSumCount();
        int len = arr.length;
        Arrays.sort(arr);
        if (len == 3) {
            if (arr[0] + arr[1] + arr[2] < target) {
                return 1;
            } else {
                return 0;
            }
        }

        for (int i=0; i<=len-3; i++) {
            findSmalerSum(arr, i+1, target - arr[i], count);
        }
        return count.res;
      }

    private static void findSmalerSum(int[] arr, int startIndex, int target, SmallerSumCount count) {
        int left = startIndex;
        int right = arr.length-1;
        while (left < right) {
            if (arr[left] + arr[right] < target) {
                count.res = count.res + (right-left);
                left++;
            } else {
                right--;
            }
        }
    }
    
    // Modify above method to print all such triplets
    public static List<List<Integer>> searchTripletsSmallerSum(int[] arr, int target) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        int len = arr.length;
        Arrays.sort(arr);
        if (len == 3) {
            if (arr[0] + arr[1] + arr[2] < target) {
                List<Integer> l = new ArrayList<>();
                l.add(arr[0]);
                l.add(arr[1]);
                l.add(arr[2]);
                res.add(l);
                return res;
            } else {
                return res;
            }
        }

        for (int i=0; i<=len-3; i++) {
            addSmalerSum(arr, i+1, target - arr[i], res, arr[i]);
        }
        return res;
      }
    
    private static void addSmalerSum(int[] arr, int startIndex, int target, List<List<Integer>> res, int curVal) {
        int left = startIndex;
        int right = arr.length-1;
        while (left < right) {
            if (arr[left] + arr[right] < target) {
                int ePos = right;
                while (left < ePos) {
                    List<Integer> l = new ArrayList<>();
                    l.add(curVal);
                    l.add(arr[left]);
                    l.add(arr[ePos]);
                    res.add(l);
                    ePos--;
                }
                left++;
            } else {
                right--;
            }
        }
    }

    /***************************************************************************************************************/
    
    /**
     * Given an array containing 0s, 1s and 2s, sort the array in-place. You should treat numbers of the array as objects,
     * hence, we can’t count 0s, 1s, and 2s to recreate the array.
     * Pseudocode

     * @param arr
     * @return
     */
    public static int[] dutchNationalFlag(int[] arr) {
        int  low = 0;
        int high = 0;

        for (int i=0; i<arr.length; i++) {
            if (arr[i] < 1) {
                swap(arr, i, low);
                low++;
            } else if (arr[i] > 1) {
                swap(arr, i, high);
                high--;
            }
        }
        return arr;
    }
    
    private static void swap(int[] arr, int src, int dest) {
        int temp = arr[src];
        arr[src] = arr[dest];
        arr[dest] = temp;
    }

    /***************************************************************************************************************/

      public static void main(String[] args) {
        //int[] result = TwoPointers.search(new int[] { 1, 2, 3, 4, 6 }, 6);
        //System.out.println("Pair with target sum: [" + result[0] + ", " + result[1] + "]");
        //result = TwoPointers.search(new int[] { 2, 5, 9, 11 }, 11);
        //System.out.println("Pair with target sum: [" + result[0] + ", " + result[1] + "]");
        System.out.println("Triplets with 0 sum are " + TwoPointers.searchTriplets(new int[] {-5, 2, -1, -2, 3}));
        System.out.println("Triplets with 0 sum are " + TwoPointers.searchTriplets(new int[] {-3, 0, 1, 2, -1, 1, -2}));

        int target = 2;
        System.out.println("Sum closest to " + target + " is " + TwoPointers.searchTripletCloseSum(new int[] {-2, 0, 1, 2}, target));
        target = 1;
        System.out.println("Sum closest to " + target + " is " + TwoPointers.searchTripletCloseSum(new int[] {-3, -1, 1, 2}, target));
        target = 100;
        System.out.println("Sum closest to " + target + " is " + TwoPointers.searchTripletCloseSum(new int[] {1, 0, 1, 1}, target));
        
        
        target = 5;
        int[] arr = new int[] {-1, 4, 2, 1, 3};
        System.out.println("Number of triplets with sum smaller than " + target + " are " + searchTripletsSmallerSumCount(arr, target));
        System.out.println("Triplets with sum less than " + target + "are: " + searchTripletsSmallerSum(arr, target).toString());

        System.out.println(Arrays.toString(dutchNationalFlag(new int[] {2,2,0,1,2,0})));
      }

}
