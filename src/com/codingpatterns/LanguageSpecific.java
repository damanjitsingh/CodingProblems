package com.codingpatterns;

import java.util.Comparator;
import java.util.PriorityQueue;

public class LanguageSpecific {
    
    public interface RaiseToPower {
        public int raise(int x);
    }

    
    public static void lambdaExpressions() {
        // creating max heap priority queue using anonymous class
        PriorityQueue<Integer> p1 = new PriorityQueue<Integer>(10, new Comparator<Integer>() {
           @Override
           public int compare(Integer a, Integer b) { 
               return b - a;
           }
        });

        // max heap using lambda expression
        PriorityQueue<Integer> p2 = new PriorityQueue<Integer>(10, (a, b) -> b-a);
        p2.add(1);
        p2.add(2);
        System.out.println(p2.poll());
        System.out.println(p2.poll());
        
        // implementing an interface using lambda expression
        RaiseToPower square = (x) -> { return (x*x);};
        RaiseToPower cube = (x) -> { return (x*x*x);};
        System.out.println(cube.raise(5));
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        lambdaExpressions();
    }

}
