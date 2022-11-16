package com.codingpatterns;

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

	  public static void main(String[] args) {
	    int[] result = TwoPointers.search(new int[] { 1, 2, 3, 4, 6 }, 6);
	    System.out.println("Pair with target sum: [" + result[0] + ", " + result[1] + "]");
	    result = TwoPointers.search(new int[] { 2, 5, 9, 11 }, 11);
	    System.out.println("Pair with target sum: [" + result[0] + ", " + result[1] + "]");
	  }

}
