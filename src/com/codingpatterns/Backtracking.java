package com.codingpatterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Backtracking {
    /**************************************************************************************/
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

    /**************************************************************************************/
    
    
    /**
     * Given an m x n, 2D grid of characters, we have to find a specific word in the grid by combining the adjacent characters.
     * Assume that only up, down, right, and left neighbours are considered adjacent.
     * Calculating time complexity:
     * Time complexity is O(N*3^l) where N is the total number of cells and l is the word length
     * 3 is chosen because for each cell there will be 3 directions to explore, its not 4 because one direction would
     * have been already explored by its parent when the recursive function for that cell is called. 
     * Space complexity is O(l) because the wordSearch can be recursively called and fill the stack max of l times.
     * @param grid
     * @param word
     * @return
     */
    public static boolean wordSearch(char[][] grid, String word) {
        boolean res = false;
        for (int i=0; i<grid.length; i++) {
            for (int j=0; j<grid[0].length; j++) {
                res = wordSearch(grid, i, j, word, 0);
                if(res) return res;
            }
        }
        return res;
    }

    private static boolean wordSearch(char[][] grid, int row, int col, String word, int index) {
        if (index >= word.length()) {
            return true; // we have covered all of the word
        }

        if (row < 0 || row == grid.length || col < 0 || col > grid[0].length || word.charAt(index) != grid[row][col]) {
            return false;
        }

        // explore the neighbors
        grid[row][col] = '#'; // mark the cell as visited so that the neighbors will not visit it again

        boolean proceedRight = false;
        boolean proceedLeft = false;
        boolean proceedDown = false;
        boolean proceedUp = false;

        // proceed down
        proceedDown = wordSearch(grid, row+1, col, word, index+1);

        // proceed up
        if (!proceedDown) {
            proceedUp = wordSearch(grid, row-1, col, word, index+1);
        }

        // proceed right
        if (!proceedDown && !proceedUp) {
            proceedRight = wordSearch(grid, row, col+1, word, index+1);
        }

        // proceed left
        if (!proceedDown && !proceedUp && !proceedRight) {
            proceedLeft = wordSearch(grid, row, col-1, word, index+1);
        }

        boolean result = proceedDown || proceedUp || proceedRight || proceedLeft;
        grid[row][col] = word.charAt(index); // clean up the marked cell
        return result;
    }

    /**************************************************************************************/
    
    public static class BinaryTreeNode {
        public int data;
        public BinaryTreeNode left;
        public BinaryTreeNode right;

        // below data members used only for some of the problems
        public BinaryTreeNode next;
        public BinaryTreeNode parent;
        public int count;

        public BinaryTreeNode(int d) {
            data = d;
            left = null;
            right = null;
            next = null;
            parent = null;
            count = 0;
        }
    }

    /**
     * Maximize profit while you cannot rob adjacent houses. The houses are represented in binary tree with
     * the nearest house to us as root. Child nodes are considered to be adjacent to parent.
     * @param root
     * @return
     */
    public static int rob(BinaryTreeNode root) {
        int[] res = robSumFaster(root);
        return Math.max(res[0], res[1]);
    }

    private static int robSumSlower(BinaryTreeNode node) {
        if (node == null) return 0;

        int sumIgnoringCurrentNode = robSumSlower(node.left) + robSumSlower(node.right);
        int sumNotIgnoringCurrentNode = node.data;
        if (node.left != null) {
            sumNotIgnoringCurrentNode += robSumSlower(node.left.left) + robSumSlower(node.left.right);
        }
        if (node.right != null) {
            sumNotIgnoringCurrentNode += robSumSlower(node.right.left) + robSumSlower(node.right.right);
        }
        return Math.max(sumIgnoringCurrentNode, sumNotIgnoringCurrentNode);
    }

    // Time complexity is O(n) since we are visiting each node only once.
    // Space complexity is O(h) where h is the height of the tree, as that many recursive calls
    // are done.
    private static int[] robSumFaster(BinaryTreeNode node) {
        if (node == null)
            return new int[] {0, 0};

        int[] left_children = robSumFaster(node.left); // Calculating the amount we can rob from left children of the node
        int[] right_children = robSumFaster(node.right); // Calculating the amount we can rob from right children of the node
        // Adding the maximum we can get from both sides
        int notNode = Math.max(left_children[0], left_children[1]) + Math.max(right_children[0], right_children[1]);
        int withNode = node.data + left_children[1] + right_children[1]; // Calculating value with node
        // Returning both the results, with node and without node.
        return new int[] {withNode, notNode};
    }

    /**************************************************************************************/
    
    
    /**
     * Given a string s containing digits, return a list of all possible valid IP addresses that can be obtained from the string.
     * A valid IP address is made up of four numbers separated by dots ., for example  255.255.255.123. Each number falls between 
     * 0 and 255 (including 0 and 255), and none of them can have leading zeros.
     */
    public static List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        // first lets get all the digits in an array
        // then for each number taken with either 1, 2 or 3 digits we will recrusively call our method for the next set of digits till we reach the end

        // get all digits in an array, e.g. if s is "123" the result will be {1,2,3}
        int[] digits = getAllDigits(s);

        constructString("", 0, res, digits, 1);
        return res;
      }

    // numberPos represents which number in the IP is getting constructed now, there are in total 4 numbers in an IP address.
    private static void constructString(String constructedStringTillNow, int currentIndex, List<String> res, int[] digits, int numberPos) {
        if (currentIndex >= digits.length && numberPos <= 4) {
            // no chance to produce a valid IP address
            return;
        }

        if (numberPos == 4) {
            // Since this is the last possible number in the IP, we have to take all the remaining digits and construct a number from it
            int number = 0;
            while (currentIndex < digits.length) {
                number = (number * 10) + digits[currentIndex];
                currentIndex+=1;
                if (number > 255) {
                    break;
                }
            }

            if (number <= 255) {
                res.add(constructedStringTillNow + number);
            }
            return;
        }

        int currentNumber = 0;

        // do recursive calls by taking next 1 digit
        currentNumber = digits[currentIndex];
        constructString(constructedStringTillNow + currentNumber + ".", ++currentIndex, res, digits, numberPos+1);

        // do recursive calls by taking next 2 digits
        currentNumber = (currentNumber * 10) + digits[currentIndex];
        constructString(constructedStringTillNow + currentNumber + ".", ++currentIndex, res, digits, numberPos+1);

        // do recursive calls by taking next 3 digits
        currentNumber = (currentNumber * 10) + digits[currentIndex];
        if (currentNumber > 255) return;
        constructString(constructedStringTillNow + currentNumber + ".", ++currentIndex, res, digits, numberPos+1);
    }

    private static int[] getAllDigits(String s) {
        if (s == null || s.length() == 0) return new int[] {};

        int[] res = new int[s.length()];
        for (int i=0; i < s.length(); i++) {
            char c = s.charAt(i);
            res[i] = c - '0';
        }
        System.out.println("Converted digits array is: " + Arrays.toString(res));
        return res;
    }

    /**************************************************************************************/
    public static void main(String[] args) {
        System.out.println(Backtracking.numberOfWays(1));
        System.out.println(Backtracking.numberOfWays(4));
        System.out.println(Backtracking.numberOfWays(5));
        System.out.println(Backtracking.numberOfWays(6));
        //System.out.println(Backtracking.numberOfWays(7));
        System.out.println(Backtracking.numberOfWays(8));

        char[][] grid = {{'h', 'e', 'c', 'm', 'l'}, {'w', 'l', 'i', 'e', 'u'}, {'a', 'r', 'r', 's', 'n'}, {'s', 'i', 'i', 'o', 'r'}};
        System.out.println(wordSearch(grid, "warrior"));
        grid = new char[][] {{'h', 'e', 'c', 'm', 'l'}, {'w', 'l', 'i', 'e', 'u'}, {'a', 'r', 'r', 's', 'n'}, {'s', 'i', 'i', 'o', 'r'}};
        System.out.println(wordSearch(grid, "damanjit"));
        grid = new char[][] {{'d', 'a', 'c', 'm', 'l'}, {'w', 'm', 'a', 'e', 'u'}, {'a', 'r', 'n', 'j', 'n'}, {'s', 'i', 'i', 'i', 't'}};
        System.out.println(wordSearch(grid, "damanjit"));
        grid = new char[][] {{'a', 'b', 'c' , 'e'}, {'s', 'f', 'c' , 's'}, {'a', 'd', 'e' , 'e'}};
        System.out.println(wordSearch(grid, "se"));

        System.out.println(restoreIpAddresses("1253412125"));
        System.out.println(restoreIpAddresses("199219239"));
    }

}
