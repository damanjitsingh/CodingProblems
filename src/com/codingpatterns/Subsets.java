package com.codingpatterns;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Subsets {
    /**
     * For a given number ‘N’, write a function to generate all combination of ‘N’ pairs of balanced parentheses.
     * Input: N=2
      Output: (()), ()()
      Input: N=3
      Output: ((())), (()()), (())(), ()(()), ()()()
     * @param n
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

    public static void main(String[] args) {
        System.out.println("Combination of " + 3 + " pairs of balanced parentheses are: " + generateBalancedParenthesis(3).toString());
        System.out.println("Combination of " + 2 + " pairs of balanced parentheses are: " + generateBalancedParenthesis(2).toString());
        System.out.println("Combination of " + 1 + " pairs of balanced parentheses are: " + generateBalancedParenthesis(1).toString());
    }

}
