package com.codingpatterns;

import java.util.ArrayList;
import java.util.List;

public class Backtracking {
    /**
     * Given a chessboard of size n×n, determine how many ways n queens can be
     * placed on the board, such that no two queens attack each other.
     * A queen can move horizontally, vertically, and diagonally on a chessboard.
     * One queen can be attacked by another queen if both share the same row, column, or diagonal.
     * Time Complexity is O (n^n)
     * Space Complexity is O(n)
     * @param args
     */
    public static int numberOfWays(int n) {
        /**
         * 1. Place a queen at first square of first row.
         * 2. Place second queen at second row such that:
         *      a. it is not at the same column as that of the previous queens
         *      b. it is not at the diagonal opposite of the previous queens
         *  3. If both steps are true move to the step1 again for the next row.
         *  4. Other wise backtrack and place the queen from step 1 to the next column of the first row.
         */
        List<List<Integer>> res = new ArrayList<>();
        int colIndex = 0;
        // traverse all the columns of the first row
        while (colIndex < n) {
            List<Integer> l = getList(n);
            l.set(0, colIndex); // place the queen at the first row at given colIndex, e.g. l = {3,...} means queen is at first row, third column
            placeQueen(l, 1, res);
            colIndex++;
        }

        System.out.println(res.toString());
        return res.size();
    }

    // check all the columns at a given row given by rowIndex and place a queen at the valid column
    private static void placeQueen(List<Integer> l, int rowIndex, List<List<Integer>> res) {
        if (rowIndex == l.size()) {
            res.add(l);
        };

        // start from the first column and find a valid position, if found make the move
        for (int col=0; col<l.size(); col++) {
            if (isMoveValid(l, rowIndex, col)) {
                // this means we can place a queen at rowIndex and col
                l.set(rowIndex, col);
                placeQueen(l, rowIndex + 1, res);
            }
        }
    }

    // Check if we can place queen at column col of row row.
    private static boolean isMoveValid(List<Integer> l , int row, int col) {
        if (row==0) return true;
        for (int i=row-1; i>=0; i--) {
            int prevRowVal = l.get(i);
            // return false if:
            // 1. the queen in the previous row is at the same column as that of col Or
            // 2. the queen in the previous row is at the diagonal opposite (left or right) of the col
            if (prevRowVal == col || prevRowVal == (col - (row-i)) || prevRowVal == (col + (row-i))) {
                return false;
            }
        }

        return true;
    }
    
    private static List<Integer> getList(int n) {
        List<Integer> l = new ArrayList<>();
        for (int i=0; i<n; i++) {
            l.add(Integer.MIN_VALUE);
        }
        return l;
    }
    
    public static void main(String[] args) {
        System.out.println(Backtracking.numberOfWays(1));
        System.out.println(Backtracking.numberOfWays(4));
        System.out.println(Backtracking.numberOfWays(5));
        System.out.println(Backtracking.numberOfWays(6));
        //System.out.println(Backtracking.numberOfWays(7));
        System.out.println(Backtracking.numberOfWays(8));
    }

}
