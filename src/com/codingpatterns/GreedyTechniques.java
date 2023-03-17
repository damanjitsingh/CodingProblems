package com.codingpatterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class GreedyTechniques {
    
    public static class Pair{
        int cost1;
        int cost2;
        int diff1Over2;
        int index;
        
        public Pair(int cost1, int cost2, int diff1Over2, int index){
            this.cost1 = cost1;
            this.cost2 = cost2;
            this.diff1Over2 = diff1Over2;
            this.index= index;
        }
        public int getDiff1Over2(){
            return diff1Over2;
        }

        public String toString() {
            return Arrays.toString(new int[] {cost1, cost2});
        }
    }

    /**
     * Return the minimum cost to fly every person to a city such that exactly n people arrive in each city.
     * @param costs
     * @return
     */
    public static int twoCityScheduling(int[][] costs) {
        List<Pair> l = new ArrayList<>();
        for (int i=0; i < costs.length; i++) {
            int[] cost = costs[i];
            Pair pair = new Pair(cost[0], cost[1], cost[0] - cost[1], i);
            l.add(pair);
        }

        // sort the list with ascending order of difference
        Collections.sort(l, (a, b) -> (a.diff1Over2 - b.diff1Over2));
        System.out.println(l.toString());

        int cost = 0;
        int n = costs.length/2;
        /// Now we will send first n elements of the Pair to cityA and the next n to cityB
        for (int i=0; i<n; i++) {
           cost = cost + l.get(i).cost1;
        }

        for (int i=n; i<2*n; i++) {
            cost = cost + l.get(i).cost2;
         }

        return cost;
    }
    
    
    public static int twoCitySchedulingAlt(int[][] costs) {
        List<Pair> l = new ArrayList<>();
        for (int i=0; i < costs.length; i++) {
            int[] cost = costs[i];
            Pair pair = new Pair(cost[0], cost[1], Math.abs(cost[0] - cost[1]), i);
            l.add(pair);
        }

        // sort the list with descending order of difference
        Collections.sort(l, (a, b) -> (b.diff1Over2 - a.diff1Over2));
        System.out.println(l.toString());

        int cost = 0;
        int n = costs.length/2;
        int cityANum = 0;
        int cityBNum = 0;
        /// Now we will send first n elements of the Pair to cityA and the next n to cityB
        for (int i=0; i < costs.length; i++) {
           Pair p = l.get(i);
           if (cityANum < n && cityBNum < n) {
               // take the minimum out of the diff
               if (p.cost1 <= p.cost2) {
                   cost = cost + p.cost1;
                   cityANum++;
               } else {
                   cost = cost + p.cost2;
                   cityBNum++;
               }
           } else if (cityANum < n) {
               cost = cost + p.cost1;
               cityANum++;
           } else if (cityBNum < n) {
               cost = cost + p.cost2;
               cityBNum++;
           } else {
               System.out.println("Error: We should not hit this else case.");
           }
        }

        return cost;
    }

    public static void main(String[] args) {
        int[][][] A = {{{10, 20}, {30, 200}, {400, 50}, {30,20}}, {{259,770}, {448,54}, {926,667}, {184,139}, {840,118}, {577,469}},
                {{515, 563}, {451, 713}, {537, 709}, {343, 819}, {855, 779},{457, 60}, {650, 359}, {631, 42}}, {{1, 2}, {3, 4}, {5, 6}, {7,8}}, {{1, 2}, {1, 2}, {1, 2}, {1, 2}}};

        for(int i=0;i<A.length;i++){
            System.out.println("Test Case # "+ (i + 1));
            System.out.println("\nThe minimum cost to send people equally into City A and B when the costs are "+ Arrays.deepToString(A[i])+ " is:\n");
            System.out.println(twoCitySchedulingAlt(A[i]));
            System.out.println("--------------------------------------------------------------------------------------------------------------");
        }
    }
}
