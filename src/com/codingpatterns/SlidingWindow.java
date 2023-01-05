package com.codingpatterns;

import java.util.HashMap;

public class SlidingWindow {
    /***************************************************************************************************************/
    /**
     * Given an array of positive numbers and a positive number ‘k,’ find the maximum sum of any contiguous subarray of size ‘k’.
     * @param args
     */
    public static int findMaxSumSubArray(int k, int[] arr) {
        int res = Integer.MIN_VALUE;
        int sum = 0;
        int startIndex = 0;
        int endIndex = 0;
        while (endIndex < arr.length) {
            sum += arr[endIndex];
            if (endIndex - startIndex + 1 == k) {
                res = Math.max(res, sum);
                sum -= arr[startIndex];
                startIndex++;
            }
            endIndex++;
        }
        return res;
    }

    /***************************************************************************************************************/
    /**
     * Given an array of positive integers and a number ‘S,’ find the length of the smallest contiguous subarray whose sum
     * is greater than or equal to ‘S’. Return 0 if no such subarray exists.
     */
    public static int findMinSubArray(int S, int[] arr) {
        int res = arr.length;
        int sum = 0;
        int sIndex = 0;
        int eIndex = 0;
        while (eIndex < arr.length) {
            sum += arr[eIndex];
            while (sum >= S) {
                int curSpan = eIndex - sIndex + 1;
                res = Math.min(res, curSpan);
                sum -= arr[sIndex];
                sIndex++;
            }
            eIndex++;
        }
        return res;
    }

    /***************************************************************************************************************/
    /**
     * Given a string, find the length of the longest substring in it with no more than K distinct characters.
     * Input: String="araaci", K=2
       Output: 4
       Explanation: The longest substring with no more than '2' distinct characters is "araa".
     */
    public static int findLength(String str, int k) {
        int res = 0;
        HashMap<Character, Integer> map = new HashMap<>(); // Space complexity is O(K) as we will be storing max K+1 characters in the map.
        int sIndex = 0;
        int eIndex = 0;
        while (eIndex < str.length()) {
            char curChar = str.charAt(eIndex);
            if (map.containsKey(curChar)) {
                map.put(curChar, map.get(curChar) + 1);
            } else {
                map.put(curChar, 1);
            }
            while (map.size() > k) {
                int curSpan = (eIndex - 1) - sIndex + 1;
                res = Math.max(res, curSpan);
                // Remove char at sIndex if possible
                char sChar = str.charAt(sIndex);
                if (map.get(sChar) == 1) {
                    map.remove(sChar);
                } else {
                    map.put(sChar, map.get(sChar) - 1);
                }
                sIndex++;
            }
            eIndex++;
            //System.out.println("res is: " + res);
        }
        //System.out.println("EIndex is: " + eIndex + " SIndex is: " + sIndex);
        //System.out.println(map.toString());
        return Math.max(res, eIndex - sIndex);
    }

    /***************************************************************************************************************/
    /**
     * Given a string, find the length of the longest substring, which has all distinct characters.
     * @param str
     * @return
     */
    public static int findLengthAllDistinct(String str) {
        int res = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        int sIndex = 0;
        int eIndex = 0;

        while (eIndex < str.length()) {
            char curChar = str.charAt(eIndex);
            if (!map.containsKey(curChar)) {
                map.put(curChar, eIndex);
            } else {
                // we found a repetitive character
                int index = map.get(curChar); // get original stored index of character which is repetitive
                map.put(curChar, eIndex); // update the index with the latest one
                if (index >= sIndex) {
                    res = Math.max(res, eIndex - sIndex);
                    sIndex = index + 1;
                }
            }
            eIndex++;
        }

        return Math.max(res, eIndex - sIndex);
    }

    /***************************************************************************************************************/
    public static void main(String[] args) {
        System.out.println(findMaxSumSubArray(3, new int[] {2,1,5,1,3,2}));
        System.out.println(findMaxSumSubArray(2, new int[] {2,3,4,1,5}));

        System.out.println(findMinSubArray(7, new int[] {2,1,5,2,3,2}));
        System.out.println(findMinSubArray(7, new int[] {2,1,5,2,8}));
        System.out.println(findMinSubArray(8, new int[] {3,4,1,1,6}));

        System.out.println(findLength("araaci", 2));
        System.out.println(findLength("araaci", 2));

        System.out.println(findLengthAllDistinct("abbbbb"));
        System.out.println(findLengthAllDistinct("aaaaaa"));
        System.out.println(findLengthAllDistinct("abcdefghij"));
        System.out.println(findLengthAllDistinct("a"));
    }

}
