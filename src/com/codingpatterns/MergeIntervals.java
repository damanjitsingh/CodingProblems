package com.codingpatterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeIntervals {

    
    public static class Interval{
        int start;
        int end;
        boolean closed;
        public Interval(int start, int end)
        {
            this.start = start;
            this.end = end;
            this.closed = true; // by default, the interval is closed
        }
        public int getStart()
        {
            return start;
        }
            
        public int getEnd(){
            return end;
        }
        public void setEnd(int end)
        {
            this.end = end;
        }

        // set the flag for closed/open
        public void setClosed(boolean closed)
        {
            this.closed = closed;
        }
        
        public String toString() {
            return "[" + start + "," + end + "]";
        }
    }

    /*******************************************************************************************************/
    /**
     * We’re given an array of closed intervals as input where each interval has a start and end timestamp.
     * The input array is sorted by starting timestamps. Merge the overlapping intervals and return a new output array.
     * Time complexity is O(n) and space complexity is O(n) as res will store n Interval objects max.
     * @param args
     */
    public static List <Interval> mergeIntervals(List <Interval> l) {
        List<Interval> res = new ArrayList<>();
        if (l.size() <=1) return l;

        boolean isOverlap = false;
        Interval cur = null;
        Interval next = null;;
        for (int i=0; i<l.size()-1; i++) {
            if (!isOverlap) cur = l.get(i);
            next = l.get(i+1);
            if (cur.end >= next.start) {
                // overlaps
                isOverlap = true;
                cur.end = Math.max(cur.end, next.end);
            } else {
                // does not overlap
                res.add(cur);
                isOverlap = false;
            }
        }
        if (!isOverlap) res.add(next);
        return res;
      }
    
    
    /*******************************************************************************************************/
    /**
     * You’re given a list containing the schedules of multiple people.
     * Each person’s schedule is a list of non-overlapping intervals in sorted order.
     * An interval is specified with the start time and the end time, both being positive integers.
     * Your task is to find the list of intervals representing the free time for all the people.
     * We’re not interested in the interval from negative infinity to zero or from the end of the last
     * scheduled interval in the input to positive infinity.
     * @param schedule
     * @return
     */
    public static List <Interval> employeeFreeTime(ArrayList <ArrayList <Interval>> schedule) {
        // Your code will replace this placeholder return statement

        List<Interval> combineIntervalsList = new ArrayList<>();
        for (int i=0; i<schedule.size(); i++) {
            combineIntervalsList = mergeIntervalsList(combineIntervalsList, schedule.get(i));
        }

        // this will give me all the overlapping intervals merged
        List<Interval> mergedList = mergeIntervals(combineIntervalsList);

        List<Interval> ans = new ArrayList<Interval>();
        // traverse this list to find the free time
        if (mergedList.size() == 1) return ans;

        for (int i=0; i<mergedList.size()-1; i++) {
            ans.add(new Interval(mergedList.get(i).end, mergedList.get(i+1).start));
        }
        return ans;
      }

    // this will merge src and dest Intervals list based on their start time
    private static List<Interval> mergeIntervalsList(List<Interval> src, List<Interval> dest) {
        int srcIndex = 0;
        int destIndex = 0;
        List<Interval> newList = new ArrayList<>();
        while (srcIndex < src.size() && destIndex < dest.size()) {
            if (src.get(srcIndex).start <= dest.get(destIndex).start) {
                newList.add(src.get(srcIndex));
                srcIndex++;
            } else {
                newList.add(dest.get(destIndex));
                destIndex++;
            }
        }
        if (srcIndex < src.size()) {
            // add remaining src intervals
            while (srcIndex < src.size()) {
                newList.add(src.get(srcIndex));
                srcIndex++;
            }
        }

        if (destIndex < dest.size()) {
            // add remaining dest intervals
            while (destIndex < dest.size()) {
                newList.add(dest.get(srcIndex));
                destIndex++;
            }
        }
        return newList;
    }

    /*******************************************************************************************************/
    public static void main(String[] args) {
        Interval[] arr = {new Interval(1,2), new Interval(3,4), new Interval(4,5), new Interval(4,6), new Interval(7,8)};
        List<Interval> inp = Arrays.asList(arr);
        System.out.println(mergeIntervals(inp).toString());
    }

}
