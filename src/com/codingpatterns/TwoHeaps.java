package com.codingpatterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class TwoHeaps {

    /**
     * Design a class to calculate the median of a number stream. The class should have the following two methods:
        insertNum(int num): stores the number in the class
        findMedian(): returns the median of all numbers inserted in the class
        If the count of numbers inserted in the class is even, the median will be the average of the middle two numbers.
     * @author damanjits
     *
     */
    static class MedianOfAStream {
        List<Integer> l = new ArrayList<>();

        public void insertNum(int num) {
            // we will insert the number such that the list will remain sorted
          if (l.size() == 0) {
              l.add(num);
              return;
          }
          for (int i=0; i<l.size(); i++) {
              if (l.get(i) > num) {
                  l.add(i, num);
                  return;
              }
          }
          l.add(num); // num is largest, so add it to the end of the list
        }

        public double findMedian() {
          int len = l.size();
          double res = 0;
          if (len%2 == 1) {
              // find the middle element
              res = (double) l.get(len/2);
          } else {
              res = ((double)l.get(len/2 - 1) + l.get(len/2))/2;
          }
          return res;
        }
    }

    static class MedianOfAStreamHeap {
        List<Integer> l = new ArrayList<>();
        // The idea is:
        // 1. We will store the first element in max heap.
        // 2. if the new element is greater than top of max, we will put it in min heap
        // 3. else we will put it to max heap and shift the max heap top element to min heap
        // to keep the heaps size almost same (for odd numbers of elements max heap can have one
        // more element than min heap).
        PriorityQueue<Integer> min = new PriorityQueue<Integer>();
        PriorityQueue<Integer> max = new PriorityQueue<Integer>(100,
                new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                if (a < b) return 1;
                if (a > b) return -1;
                return 0;
            }
        }
                );

        public void insertNum(int num) {
            if (max.size() == 0) {
                max.add(num);
                return;
            }

            if (num >= max.peek()) {
                min.add(num);
            } else {
                max.add(num);
            }

            // now balance the size of the two heaps
            int maxSize = max.size();
            int minSize = min.size();
            if (minSize > maxSize) {
                max.add(min.remove());
            } else if (maxSize > minSize+1) {
                min.add(max.remove());
            }
            System.out.println("Max heap is " + max.toString());
            System.out.println("Min heap is " + min.toString());
        }

        public double findMedian() {
            int maxSize = max.size();
            int minSize = min.size();
            if ((maxSize+minSize)%2 == 1) {
                // odd number of elements in total
                return (double)max.peek();
            } else {
                // even number of elements
                return ((double)max.peek() + min.peek())/2;
            }
        }
    }

    /*****************************************************************************************************/
    /**
     * Remember below properties when defining heap using an array.
     * -> the last parent element will be at (n-1)/2th position of the array
     * -> for a node at position k, its left child will be at 2k+1 and its right
     * child will be at 2k+2 index
     * @author damanjits
     *
     */
    static class Heap {
        private static void maxHeapify(int[] heapArray, int index, int heapSize){
            int largest = index;
            while (largest < heapSize / 2){      // check parent nodes only
                int left = (2 * index) + 1;       //left child
                int right = (2 * index) + 2;      //right child

                if (left < heapSize && heapArray[left] > heapArray[index]){
                    largest = left;
                }
                if (right < heapSize && heapArray[right] > heapArray[largest]){
                    largest = right;
                }
                if (largest != index){ // swap parent with largest child 
                    int temp = heapArray[index];
                    heapArray[index] = heapArray[largest];
                    heapArray[largest] = temp;
                    index = largest;
                }
                else 
                    break; // if heap property is satisfied 
            } //end of while
        }

        /**
         * Build max heap from a heap represented in an array.
         * Time complexity of maxHeapify is log(n) and this method traverse all the parent nodes so total 
         * complexity is O(nlogn) in a very loose sense.
         * @param heapArray Contains parent elements till n2th position and leaves after that.
         * @param heapSize
         */
        /**
         */
        public static void buildMaxHeap(int[] heapArray, int heapSize) {
            for (int i=(heapSize-1)/2; i>=0; i--) {
                maxHeapify(heapArray, i, heapSize);
            }
        }

        /*****************************************************************************************************/
        
        private static void minHeapify(int[] heapArray, int index, int heapSize){
            int smallest = index;
            while (smallest < heapSize / 2){      // check parent nodes only
                int left = (2 * index) + 1;       //left child
                int right = (2 * index) + 2;      //right child

                if (left < heapSize && heapArray[left] < heapArray[index]){
                    smallest = left;
                }
                if (right < heapSize && heapArray[right] < heapArray[smallest]){
                    smallest = right;
                }
                if (smallest != index){ // swap parent with smallest child 
                    int temp = heapArray[index];
                    heapArray[index] = heapArray[smallest];
                    heapArray[smallest] = temp;
                    index = smallest;
                }
                else 
                    break; // if heap property is satisfied 
            } //end of while
        }

        /**
         * Build max heap from a heap represented in an array.
         * Time complexity of maxHeapify is log(n) and this method traverse all the parent nodes so total 
         * complexity is O(nlogn) in a very loose sense.
         * @param heapArray Contains parent elements till n2th position and leaves after that.
         * @param heapSize
         */
        /**
         */
        public static void buildMinHeap(int[] heapArray, int heapSize) {
            for (int i=(heapSize-1)/2; i>=0; i--) {
                minHeapify(heapArray, i, heapSize);
            }
        }

        /**
         * Delete top element of the min heap.
         * Algorith:
         * 1. Move the last element to the top/root of the tree.
         * 2. Compare root with its children and swap current element with the smallest child.
         * 3. Do step 2 till you reach the last parent.
         * @param heapArray
         */
        public static void deleteElement(int[] heapArray) {
            heapArray[0] = heapArray[heapArray.length-1];
            minHeapify(heapArray, 0, heapArray.length);
        }

        /**
         * Algorithm:
         * 1. Insert new element at the end of the list.
         * 2. Then compare the element with its parent. If < parent swap
         * 3. Do step 2 till you reach root or the current element is not swappable (i.e. not < parent).
         * @param heapArray
         * @param val
         * @return
         */
        public static int[] insertElement(int[] heapArray, int val) {
            // construct new array and add the value to be inserted at the end of the array
            int[] heapArr = Arrays.copyOf(heapArray, heapArray.length+1);
            int len = heapArr.length;
            heapArr[len-1] = val;
            int index = len-1;
            while (index > 0) {
                int curElem = heapArr[index];
                int parentIndex = (index-1)/2;
                int parent = heapArr[parentIndex];
                if (curElem < parent) {
                    // swap
                    heapArr[index] = parent;
                    heapArr[parentIndex] = curElem;
                }
                index = parentIndex;
            }
            return heapArr;
        }

        public static void convertMaxHeapToMinHeap(int[] maxArr) {
            buildMinHeap(maxArr, maxArr.length);
        }

        /**
         * Find k smallest elements in an array.
         * I think the run time of below implementation is O(klogn + n).
         * @param arr
         * @param k
         * @return
         */
        public static int[] findKSmallest(int[] arr, int k) {
            PriorityQueue<Integer> q = new PriorityQueue<Integer>();
            for (int elem : arr) {
                q.add(elem);
            }

            System.out.println(q.toString());

            int[] res = new int[k];
            for (int i=0; i<k; i++) {
                res[i] = q.poll();
            }
            return res;
        }

        public static int[] findKLargest(int[] arr, int k) {
            PriorityQueue<Integer> q = new PriorityQueue<Integer>(arr.length,
                    new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    if (a < b) return 1;
                    if (a > b) return -1;
                    return 0;
                }
            }
                    );
            for (int elem : arr) {
                q.add(elem);
            }

            System.out.println(q.toString());

            int[] res = new int[k];
            for (int i=0; i<k; i++) {
                res[i] = q.poll();
            }
            return res;
        }
    }

    /*****************************************************************************************************/
    /**
     * Given an array that has projects requiring a given amount of money to invest called capital and an array
     * which has profits for that project. A person with initial capital of c with k as the max number of projects
     * he/she can invest a money into from the list of projects. Find the max capital he can make.
     * @param c
     * @param k
     * @param capitals
     * @param profits
     * @return
     */
    public static int maximizeCapital(int c, int k, int[] capitals, int[] profits) {
        /**
         * We will maintain a min heap of projects based on capitals
         * Then we will create a max heap of projects based on profits which the client can invest into bases on his
         * current capital. This is the key point.
         * We will select the head from this max heap and the next currentCapital will be incremented by its profit.
         * Now if k projects has not been covered, we will again go to step 2nd and repeat till we end up investing
         * in k projects.
         */
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a,b) -> (a[0] - b[0]));
        int index = 0;
        for (int capital : capitals) {
            int[] arr = new int[] {capital, index++};
            minHeap.offer(arr);
        }

        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a,b) -> (b[0] - a[0]));
        int curCapital = c;
        int count = 1;
        while (count < k) {
            while (!minHeap.isEmpty() && minHeap.peek()[0] <= curCapital) {
                int[] currArr = minHeap.poll();
                int[] arr = new int[] {profits[currArr[1]], currArr[1]};
                maxHeap.offer(arr);
            }
            if (maxHeap.isEmpty()) {
                break;
            }

            curCapital = curCapital + maxHeap.poll()[0];
            count++;
        }

        return curCapital;
    }
    /*****************************************************************************************************/
    /**
     * Given a set of n tasks, implement tasks method to run in O(nlogn) time that find
     * the min number of machines required to complete these n tasks.
     * @param tasksList
     * @return
     */
    public static int tasks(ArrayList<ArrayList<Integer>> tasksList) {
        // we can use a min heap to store tasks, so the task with min earliest time will be
        // at the top
        PriorityQueue<ArrayList<Integer>> minStartTime = new PriorityQueue<>((a,b) -> (a.get(0) - b.get(0)));
        for (ArrayList<Integer> task : tasksList) {
            minStartTime.offer(task);
        }

        PriorityQueue<ArrayList<Integer>> minEndTime = new PriorityQueue<>((a,b) -> (a.get(1) - b.get(1)));
        minEndTime.offer(minStartTime.poll()); // start the first task
        int res = minEndTime.size();
        while (!minStartTime.isEmpty()) {
            // get the next task
            ArrayList<Integer> nextTask = minStartTime.poll();
            // check if can accommodate our task by replacing one of the running tasks
           if (nextTask.get(0) < minEndTime.peek().get(1)) {
               // we cannot replace running task, since the new task starts
               // before the top task in minEndTime heap ends
               minEndTime.offer(nextTask);
           } else {
               // we can replace the task
               minEndTime.poll();
               minEndTime.offer(nextTask);
           }
           // finally update the res
           res = Math.max(res, minEndTime.size());
        }
        return res;
    }

    /*****************************************************************************************************/

    public static void main(String[] args) {
        int[] heapArray = { 1, 4, 7, 12, 15, 14, 9, 2, 3, 16 };
        System.out.println("Before heapify: "+Arrays.toString(heapArray));
        Heap.buildMaxHeap(heapArray, heapArray.length);
        System.out.println("After max heapify: "+Arrays.toString(heapArray));
        heapArray = new int[] {20,8,15,5,1,2};
        Heap.buildMinHeap(heapArray, heapArray.length);
        System.out.println("After min heapify: " + Arrays.toString(heapArray));
        Heap.deleteElement(heapArray);
        System.out.println("After removing root: " + Arrays.toString(heapArray));

        heapArray = new int[] { 1, 4, 7, 12, 15, 14, 9, 2, 3, 16 };
        Heap.buildMinHeap(heapArray, heapArray.length);
        System.out.println("After min heapify: " + Arrays.toString(heapArray));
        System.out.println("After inserting " + 0 + " " + Arrays.toString(Heap.insertElement(heapArray, 0)));

        heapArray = new int[] { 1, 4, 7, 12, 15, 14, 9, 2, 3, 16 };
        Heap.buildMaxHeap(heapArray, heapArray.length);
        System.out.println("After min heapify: " + Arrays.toString(heapArray));
        Heap.convertMaxHeapToMinHeap(heapArray);
        System.out.println("Converting to Min heap " + Arrays.toString(heapArray));

        heapArray = new int[] { 1, 4, 7, 12, 15, 14, 9, 2, 3, 16 };
        System.out.println("K smallest elements in " + Arrays.toString(heapArray) + " are " + Arrays.toString(Heap.findKSmallest(heapArray, 5)));
        System.out.println("K largest elements in " + Arrays.toString(heapArray) + " are " + Arrays.toString(Heap.findKLargest(heapArray, 5)));

        // find median of numbers in a list
        MedianOfAStream medianOfAStream = new MedianOfAStream();
        medianOfAStream.insertNum(3);
        medianOfAStream.insertNum(1);
        System.out.println("The median is: " + medianOfAStream.findMedian());
        medianOfAStream.insertNum(5);
        System.out.println("The median is: " + medianOfAStream.findMedian());
        medianOfAStream.insertNum(4);
        System.out.println("The median is: " + medianOfAStream.findMedian());

        MedianOfAStreamHeap medianOfAStreamH = new MedianOfAStreamHeap();
        medianOfAStreamH.insertNum(3);
        medianOfAStreamH.insertNum(1);
        System.out.println("The median is: " + medianOfAStreamH.findMedian());
        medianOfAStreamH.insertNum(5);
        System.out.println("The median is: " + medianOfAStreamH.findMedian());
        medianOfAStreamH.insertNum(4);
        System.out.println("The median is: " + medianOfAStreamH.findMedian());

        //  Input: A set "tasks_list" of "n" tasks, such that
        // each taskList has a start time and a finish time
        List < List < List < Integer >>> inputs = Arrays.asList(Arrays.asList(Arrays.asList(1, 1), Arrays.asList(5, 5), Arrays.asList(8, 8), Arrays.asList(4, 4), Arrays.asList(6, 6), Arrays.asList(10, 10), Arrays.asList(7, 7)), Arrays.asList(Arrays.asList(1, 7), Arrays.asList(1, 7), Arrays.asList(1, 7), Arrays.asList(1, 7), Arrays.asList(1, 7), Arrays.asList(1, 7)), Arrays.asList(Arrays.asList(1, 7), Arrays.asList(8, 13), Arrays.asList(5, 6), Arrays.asList(10, 14), Arrays.asList(6, 7)), Arrays.asList(Arrays.asList(1, 3), Arrays.asList(3, 5), Arrays.asList(5, 9), Arrays.asList(9, 12), Arrays.asList(12, 13), Arrays.asList(13, 16), Arrays.asList(16, 17)), Arrays.asList(Arrays.asList(12, 13), Arrays.asList(13, 15), Arrays.asList(17, 20), Arrays.asList(13, 14), Arrays.asList(19, 21), Arrays.asList(18, 20)));
        // loop to execute till the length of tasks
        ArrayList<ArrayList<ArrayList<Integer>>> inputTaskList = new ArrayList<ArrayList<ArrayList<Integer>>>();
        for(int j = 0; j < inputs.size(); j++)
        {
          inputTaskList.add(new ArrayList<ArrayList<Integer>>());
          for(int k = 0; k < inputs.get(j).size(); k++)
          {
            inputTaskList.get(j).add(new ArrayList<Integer>());
            for(int g = 0; g < inputs.get(j).get(k).size(); g++)
            {
              inputTaskList.get(j).get(k).add(inputs.get(j).get(k).get(g));
            }
          }
        }
        for (int i = 0; i < inputTaskList.size(); i++) {
          System.out.println(i + 1 + ".\tTask = " + inputTaskList.get(i).toString());
          // Output: A non-conflicting schedule of tasks in
          // "tasks_list" using the minimum number of machines
          System.out.println("\tOptimal number of machines = " + tasks(inputTaskList.get(i)));
          System.out.println("-------------------------------------------------------------------------------");
        }
      }

}
