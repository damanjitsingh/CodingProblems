package com.codingpatterns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

public class TopKElements {
    /***********************************************************************************************************/
    /**
     * Given an unsorted array of numbers, find the ‘K’ largest numbers in it.
     * @param nums
     * @param k
     * @return
     * 
     */
     public static List<Integer> findKLargestNumbers(int[] nums, int k) {
        PriorityQueue<Integer> minQ = new PriorityQueue<Integer>(nums.length, (a, b) -> a-b);
        List<Integer> res = new ArrayList<>();
        // Fill min heap
        for (int val : nums) {
            if (minQ.size() < k) {
                minQ.add(val);
            } else {
                if (val > minQ.peek()) {
                    minQ.poll();
                    minQ.add(val);
                }
            }
        }
        // Above for loop has time complexity O((n-k)logk) because in the worst case for n-k times we need to remove the smallest element from the heap

        for (int i=0; i<k; i++) {
            res.add(minQ.poll());
        }
        // Above for loop has time complexity of O(klog) because each time we fetch the smallest element it takes log(k) to bring the next smallest element
        // to the top

        // So the overall time complexity of this method is O(nlogk) which is better than O(nlogn) where we sort the list and then select the k elements
        return res;
      }
     /***********************************************************************************************************/
     /**
      * Find kth smallest number from a list.
      * @param nums
      * @param k
      * @return
      */
     public static int findKthSmallestNumber(int[] nums, int k) {
         PriorityQueue<Integer> minQ = new PriorityQueue<Integer>(nums.length, (a, b) -> a-b);
         int len = nums.length;

         // Fill min heap
         for (int val : nums) {
             if (minQ.size() < (len-k+1)) {
                 minQ.add(val);
             } else {
                 if (val > minQ.peek()) {
                     minQ.poll();
                     minQ.add(val);
                 }
             }
         }

         return minQ.poll();
     }
     /***********************************************************************************************************/
     /**
      * Find top k frequent numbers in a list.
      * @param nums
      * @param k
      * @return
      */
     public static List<Integer> findTopKFrequentNumbers(int[] nums, int k) {
         Map<Integer, Integer> map = new HashMap<>();
         for (int val : nums) {
             if (map.containsKey(val)) {
                 map.put(val, map.get(val) + 1);
             } else {
                 map.put(val, 1);
             }
         }
         Set<Entry<Integer, Integer>> set = map.entrySet();
         System.out.println(set.toString());
         PriorityQueue<Entry<Integer, Integer>> minQueue = new PriorityQueue<Entry<Integer, Integer>>(k, new Comparator<Entry<Integer, Integer>>() {
            public int compare(Entry<Integer, Integer> e1, Entry<Integer, Integer> e2) {
                return e1.getValue() - e2.getValue();
            }
         });

         // Fill min queue
         for (Entry<Integer, Integer> e : set) {
             if (minQueue.size() < k) {
                 minQueue.add(e);
             } else {
                 if (minQueue.peek().getValue() < e.getValue()) {
                     minQueue.poll();
                     minQueue.add(e);
                 }
             }
         }
         System.out.println(minQueue.toString());

         List<Integer> res = new ArrayList<>();
         for (int i=0 ; i<k; i++) {
             res.add(minQueue.poll().getKey());
         }

         return res;
       }

     /***********************************************************************************************************/
     /**
      * Given an array, find the sum of all numbers between the K1’th and K2’th smallest elements of that array.
      * @param nums
      * @param k1
      * @param k2
      * k1log(n-k1) + (k2-k1)log(n-k1) = k2log(n-k1)
      * @return
      */
     public static int findSumOfElements(int[] nums, int k1, int k2) {
         PriorityQueue<Integer> queue = new PriorityQueue<>();
         int len = nums.length;
         // first store len-k1 elements in min queue
         for (int val : nums) {
             if (queue.size() < len-k1) {
                 queue.add(val);
             } else {
                 if (val > queue.peek()) {
                     queue.poll();
                     queue.add(val);
                 }
             }
         }
         System.out.println(queue.toString());
         // at this pont the smallest/top element will be the k1+1 smallest element in nums.
         // we will poll next (k2-k1-1) elements to find all the numbers.
         int res = 0;
         int count = k2-k1-1;
         while (count-- > 0) {
             res = res + queue.poll();
         }
         return res;
       }

     /***********************************************************************************************************/
     /**
      * Given a string, find if its letters can be rearranged in such a way that no two same characters come next to each other.
      * Input: "aappp"
        Output: "papap"
        Explanation: In "papap", none of the repeating characters come next to each other.
      * @param str
      * @return
      * 
      * str = "Programming"
      * Map will be:
      * m->2
      * g->2
      * r->2
      * P->1
      * o->1
      * a->1
      * i->1
      * n->1
      * logic -> mgmgrProain
      */
     public static String rearrangeString(String str) {
         int len = str.length();
         Map<Character, Integer> map = new HashMap<>();
         for (char c : str.toCharArray()) {
             map.put(c, map.getOrDefault(c, 0) + 1);
         }

         List<Entry<Character, Integer>> l = new LinkedList<>(map.entrySet());
         Collections.sort(l, new Comparator<Entry<Character, Integer>>() {
             public int compare(Entry<Character, Integer> e1, Entry<Character, Integer> e2) {
                 return e2.getValue() - e1.getValue();
             }
         });

         System.out.println(l.toString());
         // At this point I have a sorted list by values
         StringBuilder sb = new StringBuilder();
         while (sb.length() < len) {
             if (l.size() == 1 && l.get(0).getValue() > 1) {
                 System.out.print("String letters cannot be rearranged in a given way.");
                 return "";
             } else if (l.size() == 1 && l.get(0).getValue() == 1) {
                 sb.append(l.get(0).getKey());
                 break;
             }

             Entry<Character, Integer> e1 = l.get(0);
             Entry<Character, Integer> e2 = l.get(1);
             sb.append(e1.getKey());
             sb.append(e2.getKey());
             // re arrange the list
             boolean prevRemoved = false;
             if (e1.getValue() == 1) {
                 // remove it from l
                 l.remove(0);
                 prevRemoved = true;
             } else {
                 e1.setValue(e1.getValue()-1);
             }

             if (e2.getValue() == 1) {
                 // remove it from l
                 if (prevRemoved) {
                     l.remove(0);
                 } else {
                     l.remove(1);
                 }
             } else {
                 e2.setValue(e2.getValue()-1);
             }
         }

         return sb.toString();
       }

     /***********************************************************************************************************/
     
     /**
      * Given a string and a number ‘K’, find if the string can be rearranged such that the same characters are at least ‘K’
      * distance apart from each other.
      * Input: "Programming", K=3
        Output: "rgmPrgmiano" or "gmringmrPoa" or "gmrPagimnor" and a few more
        Explanation: All same characters are 3 distance apart.
      */
     public static String reorganizeString(String str, int k) {
         Map<Character, Integer> charFrequencyMap = new HashMap<>();
         for (char chr : str.toCharArray())
           charFrequencyMap.put(chr, charFrequencyMap.getOrDefault(chr, 0) + 1);

         PriorityQueue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<Map.Entry<Character, Integer>>(
             (e1, e2) -> e2.getValue() - e1.getValue());

         // add all characters to the max heap
         maxHeap.addAll(charFrequencyMap.entrySet());

         List<Map.Entry<Character, Integer>> prevKEntries = new LinkedList<>();
         StringBuilder resultString = new StringBuilder(str.length());
         while (!maxHeap.isEmpty()) {
           Map.Entry<Character, Integer> currentEntry = maxHeap.poll();
           // add the previous entry back in the heap if its frequency is greater than k
           if (prevKEntries.size() == k-1) {
               Map.Entry<Character, Integer> previousEntry = prevKEntries.remove(0);
               if (previousEntry.getValue() > 0) {
                   maxHeap.offer(previousEntry);
               }
           }
           // append the current character to the result string and decrement its count
           resultString.append(currentEntry.getKey());
           currentEntry.setValue(currentEntry.getValue() - 1);
           prevKEntries.add(currentEntry);
         }

         // if we were successful in appending all the characters to the result string, return it
         return resultString.length() == str.length() ? resultString.toString() : "";
       }
     /***********************************************************************************************************/
     /**
      * You are given a list of tasks that need to be run, in any order, on a server.
      * Each task will take one CPU interval to execute but once a task has finished,
      * it has a cooling period during which it can’t be run again. If the cooling period for all
      * tasks is ‘K’ intervals, find the minimum number of CPU intervals that the server needs to finish all tasks.
      * Input: [a, a, a, b, c, c], K=2
        Output: 7
        Explanation: a -> c -> b -> a -> c -> idle -> a

        Input: [a, b, a], K=3
        Output: 5
        Explanation: a -> b -> idle -> idle -> a
      * @param tasks
      * @param k
      * @return
      */
     public static int scheduleTasks(char[] tasks, int k) {
         Map<Character, Integer> charFrequencyMap = new HashMap<>();
         for (char chr : tasks)
           charFrequencyMap.put(chr, charFrequencyMap.getOrDefault(chr, 0) + 1);

         PriorityQueue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<Map.Entry<Character, Integer>>(
             (e1, e2) -> e2.getValue() - e1.getValue());

         // add all characters to the max heap
         maxHeap.addAll(charFrequencyMap.entrySet());
         int intervalCount = 0;
         while (!maxHeap.isEmpty()) {
             List<Map.Entry<Character, Integer>> waitList = new ArrayList<>();
             int n = k + 1; // try to execute as many as 'k+1' tasks from the max-heap
             for (; n > 0 && !maxHeap.isEmpty(); n--) {
                 intervalCount++;
                 Map.Entry<Character, Integer> currentEntry = maxHeap.poll();
                 if (currentEntry.getValue() > 1) {
                   currentEntry.setValue(currentEntry.getValue() - 1);
                   waitList.add(currentEntry);
                 }
             }
             maxHeap.addAll(waitList); // put all the waiting list back on the heap
             if (!maxHeap.isEmpty())
               intervalCount += n; // we'll be having 'n' idle intervals for the next iteration
         }
         return intervalCount;
     }

     /***********************************************************************************************************/

      public static void main(String[] args) {
        List<Integer> result = TopKElements.findKLargestNumbers(new int[] { 3, 1, 5, 12, 2, 11 }, 3);
        System.out.println("Here are the top K numbers: " + result);

        result = TopKElements.findKLargestNumbers(new int[] { 5, 12, 11, -1, 12 }, 3);
        System.out.println("Here are the top K numbers: " + result);

        int res = TopKElements.findKthSmallestNumber(new int[] { 5, 12, 11, -1, 12 }, 3);
        System.out.println("Here is the Kth smallest number: " + res);

        res = TopKElements.findKthSmallestNumber(new int[] { 5, 12, 11, -1, 12, -2}, 3);
        System.out.println("Here is the Kth smallest number: " + res);

        result = TopKElements.findTopKFrequentNumbers(new int[] { 1, 3, 5, 12, 11, 12, 11 }, 2);
        System.out.println("Here are the K frequent numbers: " + result);

        result = TopKElements.findTopKFrequentNumbers(new int[] { 5, 12, 11, 3, 11 }, 2);
        System.out.println("Here are the K frequent numbers: " + result);

        res = TopKElements.findSumOfElements(new int[] { 1, 3, 12, 5, 15, 11 }, 3, 6);
        System.out.println("Sum of all numbers between k1 and k2 smallest numbers: " + res);

        res = TopKElements.findSumOfElements(new int[] { 3, 5, 8, 7 }, 1, 4);
        System.out.println("Sum of all numbers between k1 and k2 smallest numbers: " + res);

        System.out.println("Rearranged string: " + TopKElements.rearrangeString("aappp"));
        System.out.println("Rearranged string: " + TopKElements.rearrangeString("Programming"));
        System.out.println("Rearranged string: " + TopKElements.rearrangeString("aapa"));

        System.out.println("Reorganized string: " + TopKElements.reorganizeString("mmpp", 2));
        System.out.println("Reorganized string: " + TopKElements.reorganizeString("Programming", 3));
        System.out.println("Reorganized string: " + TopKElements.reorganizeString("aab", 2));
        System.out.println("Reorganized string: " + TopKElements.reorganizeString("aappa", 3));
        
        
        char[] tasks = new char[] { 'a', 'a', 'a', 'b', 'c', 'c' };
        System.out.println("Minimum intervals needed to execute all tasks: " + TopKElements.scheduleTasks(tasks, 2));

        tasks = new char[] { 'a', 'b', 'a' };
        System.out.println("Minimum intervals needed to execute all tasks: " + TopKElements.scheduleTasks(tasks, 3));
      }
}
