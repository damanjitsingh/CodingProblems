package com.codingpatterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class KnowingWhatToTrack {

    /**
     * For a given string, find whether or not a permutation of this string is a palindrome.
     * You should return TRUE if such a permutation is possible and FALSE if it isn’t possible.
     * Example s = "aab" there exists a permutation aba which is a palindrome.
     * @param st
     * @return
     */
    public static boolean permutePalindrome(String st) {
        /**
         * The idea here is to get the number of characters in a string that have odd number
         * of occurances. If there are more than one such character we return false, true otherwise.
         */
        Map<Character, Integer> map = new HashMap<>();
        //fill the frequency map
        for (char c : st.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int oddCount = 0;
        for (Map.Entry<Character, Integer> e : map.entrySet()) {
            int value = e.getValue();
            if (value%2 == 1) {
                oddCount++;
            }
        }

        return oddCount > 1 ? false : true;
    }
    
    /**
     * Given a list of words or phrases, group the words that are anagrams of each other.
     * An anagram is a word or phrase formed from another word by rearranging its letters.
     * Example:
     * strs = {"duel", "dule", "speed", "spede", "deul", "cars"}
     * @param strs
     * @return
     */
    public static List<List<String>> groupAnagrams(String[] strs){
        /**
         * The idea here is simple.
         * We traverse every string in the list, construct a new string that has the frequency of each character in their respective positions
         * starting from 0 to 26. Store that string as key in a map and its value is the list of strings that constructs the key.
         */
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            int[] count = new int[26];
            Arrays.fill(count, 0);
            for (char c: s.toCharArray()) {
                count[c - 'a']++;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                sb.append('#');
                sb.append(count[i]);
            }
            String key = sb.toString();

            List<String>l;
            if (map.containsKey(key)) {
                l = map.get(key);
                l.add(s);
            } else {
                l = new ArrayList<>();
                l.add(s);
            }
            map.put(key, l);
        }

        return new ArrayList<List<String>>(map.values());
    }

    /***************************************************************************************/

    /**
     * Design a stack-like data structure. You should be able to push elements to this data structure and pop elements with maximum frequency.
        You’ll need to implement the FreqStack class that should consist of the following:
        FreqStack: This is a class used to declare a frequency stack.
        Push(data): This is used to push an integer data onto the top of the stack.
        Pop(): This is used to remove and return the most frequent element in the stack.
     * @author damanjits
     *
     */
    public static class FreqencyStack {
        // key is the number, val is list with l[0] =  number, l[1] = frequency of the number
        // l[2].. and so on are the positions of the number in the stack in descending order
        Map<Integer, List<Integer>> map = new HashMap<>();
        private int counter = 0; // stack counter
        PriorityQueue<List<Integer>> maxHeap = new PriorityQueue<List<Integer>>(2000, new Comparator<List<Integer>>() {
            public int compare(List<Integer> l1, List<Integer> l2) {
                if (l2.get(1) != l1.get(1)) {
                    // frequency is not equal, keep most frequent at top
                    return l2.get(1) - l1.get(1);
                } else {
                    // if frequency is equal, keep the recent added number to the top
                    return l2.get(2) - l1.get(2);
                }
            }
        });

        public void Push(int data) {
            counter++;
            List<Integer> l;
            if (map.containsKey(data)) {
                l = map.get(data);
                l.set(1, l.get(1) + 1);
                l.add(2, counter);
            } else {
                l = new ArrayList<>();
                l.add(data); // number
                l.add(1); // frequency
                l.add(counter); // index in the stack
            }
            map.put(data, l);
        }

        private void fillHeap() {
            for (Map.Entry<Integer, List<Integer>> e : map.entrySet()) {
                maxHeap.offer(e.getValue());
            }
        }

        public int Pop() {
            if (maxHeap.size() == 0) {
                fillHeap();
            }
            //System.out.println(map.toString());
            //System.out.println(maxHeap.toString());
            List<Integer> l = maxHeap.poll();
            int num = l.get(0);
            int freq = l.get(1);
            if (freq > 1) {
                l.remove(2); // remove the latest stack index
                l.set(1, freq-1);
                maxHeap.offer(l);
            }
            return num;
        }
    }

    class FreqStack {
        // Declare a FreqStack class containing frequency and group hashmaps
        // and maxFrequency integer
        Map<Integer, Integer> frequency;
        Map<Integer, Stack <Integer>> group;
        int maxFrequency;

        // Use constructor to initialize the FreqStack object
        public FreqStack() {
            frequency = new HashMap < > ();
            group = new HashMap < > ();
            maxFrequency = 0;
        }

        // Use push function to push the value into the FreqStack
        public void push(int value) {
            // Get the frequency for the given value and 
            // increment the frequency for the given value
            int freq = frequency.getOrDefault(value, 0) + 1;
            frequency.put(value, freq);

            // Check if the maximum frequency is lower that the new frequency
            // of the given show
            if (freq > maxFrequency)
                maxFrequency = freq;

            // Save the given showName for the new calculated frequency
            group.computeIfAbsent(freq, z -> new Stack < > ()).push(value);
        }

        // Use the pop function to pop the showName from the FreqStack
        public int pop() {
            int show = 0;
            if (maxFrequency > 0) {
                // Fetch the top of the group[maxFrequency] stack and 
                // pop the top of the group[maxFrequency] stack
                show = group.get(maxFrequency).pop();

                // Decrement the frequency after the show has been popped
                frequency.put(show, frequency.get(show) - 1);
                if (group.get(maxFrequency).size() == 0)
                    maxFrequency--;
            } else {
                return -1;
            }
            return show;
        }
    }

    /***************************************************************************************/
    /**
     * Find the index of first unique character in a string.
     * @param s
     * @return
     */
    public int indexOfFirstUniqueCharacter(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int res = -1;
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (map.get(c) == 1) {
                // we found the first unique character
                res = i;
                break;
            }
        }
        return res;
    }

    /***************************************************************************************/
    /**
     * Given two strings a and b return an array of all the start indexes of b's anagrams in a
     * We may return the answer in any order.
     * @param a
     * @param b
     * @return
     */
    public static List<Integer> findAnagrams(String a, String b) {
        List<Integer> res = new ArrayList<>();
        int len = b.length();
        String bAnagram = getAnagramArray(b);
        System.out.println("B anagram array is: " + bAnagram);

        int startIndex = 0;
        int endIndex = startIndex + len;
        // Runs O((n-m)*m) time
        while (endIndex <= a.length()) {
            String aAnagram = getAnagramArray(a.substring(startIndex, endIndex));
            System.out.println("New A anagram array is: " + aAnagram);
            if (aAnagram.equals(bAnagram)) {
                res.add(startIndex);
            }
            startIndex++;
            endIndex++;
        }

        return res;
    }

    // Runs in O(m) time where m is the length of str, needs constant space to create the result
    private static String getAnagramArray(String str) {
        int[] arr = new int[26];
        Arrays.fill(arr, 0);
        for (char c : str.toCharArray()) {
            int index = c - 'a';
            arr[index] += 1;
        }
        return Arrays.toString(arr);
    }
    /***************************************************************************************/

    public static void main(String[] args) {
        System.out.println(permutePalindrome("aab"));

        FreqencyStack stack = new FreqencyStack();
        stack.Push(2);
        stack.Push(8);
        stack.Push(10);
        stack.Push(9);
        stack.Push(4);
        stack.Push(6);
        stack.Push(1);
        stack.Push(2);
        stack.Push(5);
        stack.Push(8);
        System.out.println(stack.Pop());
        System.out.println(stack.Pop());
        System.out.println(stack.Pop());

        System.out.println(KnowingWhatToTrack.findAnagrams("hello", "ll"));
    }

}
