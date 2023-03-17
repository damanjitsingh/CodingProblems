package com.codingpatterns;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Strings {

    /**
     * Find the minimum changes which must be made to make two string anagrams.
     * e.g. for {aba, bba} the min. changes are 1
     * for {abc, def} the minimum changes are 3
     * for {tea, eat} the minimum changes are 0
     * @param s
     * @param t
     * @return
     */
    public static int minChangesAnagrams(String s, String t) {
        Map<Character,Integer> m1 = new HashMap<>();
        Map<Character,Integer> m2 = new HashMap<>();

        int len1 = s.length();
        int len2 = t.length();
        if (len1 != len2) {
            return -1;
        }

        for (char c : s.toCharArray()) {
            m1.put(c, m1.getOrDefault(c, 0) + 1);
        }

        for (char c : t.toCharArray()) {
            m2.put(c, m2.getOrDefault(c, 0) + 1);
        }

        // now we have both the maps with char frequency ready
        int res = 0;
        for (Entry<Character, Integer> e : m1.entrySet()) {
            char key = e.getKey();
            int val = e.getValue();
            if (m2.containsKey(key)) {
                res = res + Math.abs(val - m2.get(key));
            } else {
                res = res + val;
            }
        }

        // Find all the characters that are in m2 but not in m1
        for (Entry<Character, Integer> e : m2.entrySet()) {
            char key = e.getKey();
            int val = e.getValue();
            if (!m1.containsKey(key)) {
                res = res + val;
            }
        }

        return res/2;
    }

    public static void main(String[] args) {
        
    }

}
