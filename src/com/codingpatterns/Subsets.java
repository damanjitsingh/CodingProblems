package com.codingpatterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Subsets {
    
    /*****************************************************************************************************/
    /**
     * For a given number ‘N’, write a function to generate all combination of ‘N’ pairs of balanced parentheses.
     * Input: N=2
      Output: (()), ()()
      Input: N=3
      Output: ((())), (()()), (())(), ()(()), ()()()
     * @param n
     * 
     * What is the time and space complexity of below implemented method.
     * Time complexity is O(n*2^n) where n is the number of pairs. 2^n are the max number of such pairs (although for balanced paranthesis this will be less).
     * We multiplied by n as string append happens in O(n) time for a string.
     * Space complexity is O(2^n)
     * 
     * 
     * @return
     */
    static class BracketString {
        String val;
        int openBracketsCount = 0;
        int closedBracketsCount = 0;

        public BracketString(String value, int oCount, int cCount) {
            this.val = value;
            this.openBracketsCount = oCount;
            this.closedBracketsCount = cCount;
        }
    }

    public static List<String> generateBalancedParenthesis(int n) {
        List<String> res = new ArrayList<>();
        Queue<BracketString> q = new LinkedList<>();
        q.add(new BracketString("", 0,0));
        while (!q.isEmpty()) {
            BracketString cur = q.poll();
            if (cur.openBracketsCount < n) {
                q.add(new BracketString(cur.val + "(", cur.openBracketsCount + 1, cur.closedBracketsCount));
            }
            if (cur.openBracketsCount <= n && cur.closedBracketsCount < cur.openBracketsCount) {
                q.add(new BracketString(cur.val + ")", cur.openBracketsCount, cur.closedBracketsCount + 1));
            }
            if (cur.openBracketsCount == n && cur.openBracketsCount == cur.closedBracketsCount) {
                res.add(cur.val);
            }
        }
        return res;
    }

    /*****************************************************************************************************/
    /**
     * Given a set with distinct elements, find all of its distinct subsets.
     * Input: [1, 3]
        Output: [], [1], [3], [1,3]
     * @param nums
     * @return
     * 
     * Time Complexity is O(n* 2^n): 
     * Since, in each step, the number of subsets doubles as we add each element to all the existing subsets,
     * therefore, we will have a total of O(2^N) O(2^N) subsets, where ‘N’ is the total number of elements in
     * the input set. And since we construct a new subset from an existing set, therefore, the time complexity
     * of the above algorithm will be O(N*2^N)O(N∗2N).
     * Space Complexity is O(n*2^n)
     */
    public static List<List<Integer>> findSubsets(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();
        subsets.add(new ArrayList<Integer>());

        for (int num : nums) {
            int subsetsSize = subsets.size();
            for (int i=0 ; i<subsetsSize; i++) {
                List<Integer> newSubset = new ArrayList<>(subsets.get(i));
                newSubset.add(num);
                subsets.add(newSubset);
            }
        }
        return subsets;
      }
    
    /*****************************************************************************************************/
    /**
     * Given a set of distinct numbers, find all of its permutations.
     * Permutation is defined as the re-arranging of the elements of the set. For example, {1, 2, 3} has the following six permutations:

        {1, 2, 3}
        {1, 3, 2}
        {2, 1, 3}
        {2, 3, 1}
        {3, 1, 2}
        {3, 2, 1}
     * @param nums
     * @return
     * Time complexity is O(n*n!), we multiply by n because inserting an element into a list takes O(n) time.
     * Space complexity is O(n*n!)
     */
    public static List<List<Integer>> findPermutations(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        subsets.add(new ArrayList<Integer>());

        for (int num : nums) {
            int subsetsSize = subsets.size();
            for (int i=0 ; i<subsetsSize; i++) {
                List<Integer> curList = subsets.get(i);
                for (int k=0; k<=curList.size(); k++) {
                    List<Integer> newSubset = new ArrayList<>(curList);
                    newSubset.add(k, num);
                    subsets.add(newSubset);
                    if (newSubset.size() == nums.length) {
                        result.add(newSubset);
                    }
                }
            }
        }
        return result;
      }

    /*****************************************************************************************************/
    protected static final String[] arr = new String[] {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public List<String> letterCombinationsUsingRecursion(String digits) {
        // if input is 456
        // then possible outcomes are {"gjm", "gjn", "gjo"....};
        // lets use the backtracking recursion approach
        List<String> res = new ArrayList<>();
        constructLetters(digits, 0, res, "");
        return res;
     }
    
    private static void constructLetters(String digits, int digitIndex, List<String>res, String currentString) {
        if (digitIndex >= digits.length()) {
            return;
        }

        int digitValue = (int) (digits.charAt(digitIndex) - '0'); // get the value of digit at digitIndex
        String s = Subsets.arr[digitValue]; // get the corresponding string from the phone book

        if (digitIndex == digits.length() - 1) {
            // we need to append each character of the digit
            for (int i=0; i<s.length(); i++) {
                res.add(currentString + s.charAt(i));
            }
            return;
        }

        // for all other digits prior to last digit
        for (int i=0; i<s.length(); i++) {
            constructLetters(digits, digitIndex+1, res, currentString + s.charAt(i));
        }
    }

    // The time complexity is O(k^n * n) where n is the length of the digits and k is the length of the max mapped string (here it is 4)
    // it is multiplied by n as the final elements in the res is of length n.
    // The space complexity is O(n.k) where n is the total number of input digits, and  k is the maximum number of letters mapped to any digit.
    public List<String> letterCombinationsUsingSubsets(String digits) {
        List<String> res = new ArrayList<>();

        for (int i=0; i<digits.length(); i++) {
            int digitValue = (int) (digits.charAt(i) - '0');
            String digitString = Subsets.arr[digitValue];
            if (res.size() == 0) {
                // If the res list is empty we will just add all the characters of the first digit, e.g. if first digit is 3, res = {"d", "e", "f"}
                for (char c : digitString.toCharArray()) {
                    res.add("" + c);
                }
            } else {
                // if res is not empty, then for each element of res , we will append characters of the given digit
                // e.g. if the second digit is 4 then res = {"dg", "dh", "di", "eg", "eh", "ei", "fg", "fh", "fi"}
                List<String> newList = new ArrayList<>();
                for (String s : res) {
                    for (char c : digitString.toCharArray()) {
                        newList.add(s + c);
                    }
                }
                res = newList;
            }
        }
        return res;
    }

    /*****************************************************************************************************/
    
    public static void main(String[] args) {
        System.out.println("Combination of " + 3 + " pairs of balanced parentheses are: " + generateBalancedParenthesis(3).toString());
        System.out.println("Combination of " + 2 + " pairs of balanced parentheses are: " + generateBalancedParenthesis(2).toString());
        System.out.println("Combination of " + 1 + " pairs of balanced parentheses are: " + generateBalancedParenthesis(1).toString());


        System.out.println(findSubsets(new int[] {1,3}).toString());
        int[] arr = new int[] {1,2,3};
        System.out.println("All permutations of " + Arrays.toString(arr) + " are: " + findPermutations(arr).toString());
    }

}
