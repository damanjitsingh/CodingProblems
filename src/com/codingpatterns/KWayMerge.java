package com.codingpatterns;

import java.util.Arrays;

public class KWayMerge {
    /**
     * Given two sorted integer arrays nums1 and nums2 implement a function that merges the second array into the first one.
     * You have to modify nums1 in place.
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     * @return
     */
    public static int[] mergeSorted(int[] nums1, int m, int[] nums2, int n) {
        // n is the size of nums2
        int endIndex1 = m-1;
        int endIndex2 = n-1;
        int nums1Index = nums1.length-1;
        while (endIndex2 >= 0 && endIndex1 >= 0) {
            if (nums2[endIndex2] > nums1[endIndex1]) {
                nums1[nums1Index] = nums2[endIndex2];
                endIndex2--;
            } else {
                nums1[nums1Index] = nums1[endIndex1];
                endIndex1--;
            }
            nums1Index--;
        }

        // Merge the remaining elements
        while (endIndex2 >= 0) {
            nums1[nums1Index--] = nums1[endIndex2--];
        }

        return nums1;
     }

    public static void main(String[] args) {
        int[] nums1 = {1,4,9,0,0};
        int[] nums2 = {1, 76};

        System.out.println(Arrays.toString(mergeSorted(nums1, 3, nums2, 2)));

    }

}
