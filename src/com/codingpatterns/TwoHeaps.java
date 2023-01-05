package com.codingpatterns;

import java.util.ArrayList;
import java.util.Arrays;
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
     * Remeber below properties when defining heap using an array.
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
      }

}
