package com.codingpatterns;

import java.util.Arrays;

public class ModifiedBinarySearch {

    /****************************************************************************************************************************/
    /**
     * Given a sorted array (ascending or descending), find the index
     * of a given element in the array. Return -1 if element is not 
     * found in the array.
     * @param arr
     * @return
     */
    public static int findIndex(int[] arr, int key) {
        int len = arr.length;
        if (len == 1) {
            return arr[0] == key ? 0 : -1;
        }
        
        int res = -1;
        if (arr[0] <= arr[1]) {
            res = findIndexAsc(arr, 0, arr.length-1, key);
        } else {
            res = findIndexDesc(arr, 0, arr.length-1, key);
        }
        return res;
    }

    private static int findIndexAsc(int arr[], int startIndex, int endIndex, int key) {
        if (startIndex > endIndex) return -1;
        int mid = startIndex + (endIndex-startIndex)/2; // to avoid integer overflow when we do (start+end)/2
        if (key > arr[mid]) {
            startIndex = mid + 1;
        } else if (key < arr[mid]) {
            endIndex = mid-1;
        } else {
            // found the key
            return mid;
        }
        return findIndexAsc(arr, startIndex, endIndex, key);
    }

    private static int findIndexDesc(int arr[], int startIndex, int endIndex, int key) {
        if (startIndex > endIndex) return -1;
        int mid = startIndex + (endIndex-startIndex)/2;
        if (key < arr[mid]) {
            startIndex = mid + 1;
        } else if (key > arr[mid]) {
            endIndex = mid-1;
        } else {
            // found the key
            return mid;
        }
        return findIndexDesc(arr, startIndex, endIndex, key);
    }

    /****************************************************************************************************************************/
    /**
     * Given an array of numbers sorted in an ascending order, find the ceiling of a given number ‘key’. The ceiling of the ‘key’ will
     * be the smallest element in the given array greater than or equal to the ‘key’.
     * Write a function to return the index of the ceiling of the ‘key’. If there isn’t any ceiling return -1.
     * @param arr
     * @param key
     * @return
     */
    public static int findCeilingIndex(int arr[], int key) {
        int len = arr.length;
        if (key > arr[len-1]) return -1;
        if (key <= arr[0]) return 0;
        return findCeilingIndex(arr, 0, len-1, key);
    }

    private static int findCeilingIndex(int arr[], int startIndex, int endIndex, int key) {
        if (startIndex > endIndex) return startIndex;
        int mid = startIndex + (endIndex-startIndex)/2;
        if (key > arr[mid]) {
            startIndex = mid+1;
        } else if (key < arr[mid]) {
            endIndex = mid-1;
        } else {
            // found the key
            return mid;
        }

        return findCeilingIndex(arr, startIndex, endIndex, key);
    }

    /****************************************************************************************************************************/
    /**
     * Given an array of numbers sorted in ascending order, find the range of a given number ‘key’.
     * The range of the ‘key’ will be the first and last position of the ‘key’ in the array.
     * Write a function to return the range of the ‘key’. If the ‘key’ is not present return [-1, -1].
     * @param arr
     * @param key
     * @return
     */
    public static int[] findRange(int[] arr, int key) {
        int[] result = new int[] { -1, -1 };
        result[0] = search(arr, key, false);
        if (result[0] != -1) // no need to search, if 'key' is not present in the input array
          result[1] = search(arr, key, true);
        return result;
    }

    // modified Binary Search
    private static int search(int[] arr, int key, boolean findMaxIndex) {
      int keyIndex = -1;
      int start = 0, end = arr.length - 1;
      while (start <= end) {
        int mid = start + (end - start) / 2;
        if (key < arr[mid]) {
          end = mid - 1;
        } else if (key > arr[mid]) {
          start = mid + 1;
        } else { // key == arr[mid]
          keyIndex = mid;
          if (findMaxIndex)
            start = mid + 1; // search ahead to find the last index of 'key'
          else
            end = mid - 1; // search behind to find the first index of 'key'
        }
      }
      return keyIndex;
    }

    /****************************************************************************************************************************/
    static class ArrayReader {
        int[] arr;

        ArrayReader(int[] arr) {
          this.arr = arr;
        }

        public int get(int index) {
          if (index >= arr.length)
            return Integer.MAX_VALUE;
          return arr[index];
        }
      }
    
    /**
     * Given an infinite sorted array (or an array with unknown size), find if a given number ‘key’ is present in the array.
     * Write a function to return the index of the ‘key’ if it is present in the array, otherwise return -1.
    Since it is not possible to define an array with infinite (unknown) size, you will be provided with an interface ArrayReader
    to read elements of the array. ArrayReader.get(index) will return the number at index; if the array’s size is smaller than the index, it will return Integer.MAX_VALUE.
     * @param reader
     * @param key
     * @return
     */
    public static int search(ArrayReader reader, int key) {
        // first we will find bounds in which key lies, exponentially
        if (key < reader.get(0)) return -1;
        int start = 0;
        int end = 1;
        int bound = 0;
        while (true) {
            if (key >= reader.get(start) && key <= reader.get(end)) {
                // we will break as the bound is found
                break;
            }
            bound = 2 * (end - start + 1);
            start = end + 1;
            end = start + bound-1;
        }

        // perform actual binary search
        System.out.println("Range starts from " + start + " and ends at " + end);
        int mid = 0;
        int res = -1;
        while (start <= end) {
            mid = start + (end - start)/2;
            if (key < reader.get(mid)) {
                end = mid - 1;
            } else if (key > reader.get(mid)) {
                start = mid + 1;
            } else {
                res = mid;
                break;
            }
        }

        return res;
    }

    /****************************************************************************************************************************/
    public static int minDiff(int[] arr, int key) {
        int minDiff = Integer.MAX_VALUE;
        int start = 0;
        int end = arr.length-1;
        int res = -1;
        while (start <= end) {
            int mid = start + (end - start)/2;
            if (key < arr[mid]) {
                int curDiff = Math.abs(arr[mid] - key);
                if (curDiff < minDiff) {
                    minDiff = curDiff;
                    res = arr[mid];
                }
                end = mid - 1;
            } else if (key > arr[mid]) {
                int curDiff = Math.abs(arr[mid] - key);
                if (curDiff < minDiff) {
                    minDiff = curDiff;
                    res = arr[mid];
                }
                start = mid + 1;
            } else {
                res = arr[mid];
                break;
            }
        }
        return res;
    }

    /****************************************************************************************************************************/
    public static void main(String[] args) {
        /*
        System.out.println(ModifiedBinarySearch.findIndex(new int[] {4,6,10}, 10));
        System.out.println(ModifiedBinarySearch.findIndex(new int[] {10,6,4}, 10));
        System.out.println(ModifiedBinarySearch.findIndex(new int[] {10,6,4}, 1));
        System.out.println(ModifiedBinarySearch.findIndex(new int[] {10,6,4,3,1,1,1,1,1}, 1));
        System.out.println(ModifiedBinarySearch.findIndex(new int[] {1,1,1,1,1}, 1));
        
        
        System.out.println(ModifiedBinarySearch.findCeilingIndex(new int[] {4,6,10}, 12));
        System.out.println(ModifiedBinarySearch.findCeilingIndex(new int[] {4,6,10}, 6));
        System.out.println(ModifiedBinarySearch.findCeilingIndex(new int[] {4,6,10}, 8));
        System.out.println(ModifiedBinarySearch.findCeilingIndex(new int[] {1,2,3,4,5,6,15,20}, 11));
        
        System.out.println(Arrays.toString(ModifiedBinarySearch.findRange(new int[] {10,6,4,3,1,1,1,1,1}, 1)));
        System.out.println(Arrays.toString(ModifiedBinarySearch.findRange(new int[] {10,6,4,3,1,1,1,1,1}, -1)));
        System.out.println(Arrays.toString(ModifiedBinarySearch.findRange(new int[] {10,6,4,3,1,1,1,1,1}, 10)));
        
        ArrayReader reader = new ArrayReader(new int[] { 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30 });
        System.out.println(ModifiedBinarySearch.search(reader, 16));
        System.out.println(ModifiedBinarySearch.search(reader, 11));
        reader = new ArrayReader(new int[] {-1,0,3,5,9,12});
        System.out.println(ModifiedBinarySearch.search(reader, 9));
        */

        System.out.println(minDiff(new int[] {4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30}, 32));
        System.out.println(minDiff(new int[] {4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30}, 13));
    }

}
