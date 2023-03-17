package com.codingpatterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JavaLanguage {
    public static void main(String[] args) {
        //names("Daman", "Jit", "Singh");
        playWithStreams();
        int[] arr = new int[] {1, 2, 3};
        int[] arr1 = {1,2,3};
        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.stream(arr).sum());

        //System.out.println(Arrays.stream(arr).distinct().toString());
        filterMapIterate();
        getMaleAgeSum();
    }
    
    public static class Item {
        int price;
        String name;
        public Item(String name, int price) {
            this.name = name;
            this.price = price;
        }
    }
    
    public static void filterMapIterate() {
        System.out.println("-------filterMapIterate-----------");
        List<Item> l = new ArrayList<>();
        l.add(new Item("John", 12));
        l.add(new Item("Melissa", 22));
        l.add(new Item("Sean", 34));

        l.stream().filter(p -> p.price > 12).map(pm -> pm.price).forEach(System.out::println);
    }
/***************************************************************************************************/
    public enum SEX {
        MALE,
        FEMALE
    }

    public static class Person {
        private String name;
        private int age;
        private SEX gender;
        public Person (String name, int age, SEX gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public int getAge() {
            return this.age;
        }

        public String getName() {
            return this.name;
        }
        
        public SEX getGender() {
            return this.gender;
        }
    }
    
    public static void getMaleAgeSum() {
        List<Person> l = new ArrayList<>();
        l.add(new Person("Sam", 20, SEX.MALE));
        l.add(new Person("Bradley", 30, SEX.MALE));
        l.add(new Person("Jane", 35, SEX.FEMALE));
        l.add(new Person("Bob", 40, SEX.MALE));

        // Get the sum of male person ages from the list
        int sum = l.stream().
                filter(p -> p.getGender() == SEX.MALE).
                map(pm -> pm.getAge()).
                reduce(0, (a,b)-> a+b); // first argument is the default/initial value which is the sum here
        System.out.println("Sum of age of all males: " + sum);
        

        // Use collect to collect the result in a list
        System.out.println("List of age of all males: " + l.stream().
                filter(p -> p.getGender() == SEX.MALE).
                map(pm -> pm.getAge()).
                collect(Collectors.toList()));
    }

    /***************************************************************************************************/

    public static void names(String... names) {
        for (int i=0; i < names.length; i++) {
            System.out.println(names[i]);
        }
    }

    public static void playWithStreams() {
        // List<String> l = Arrays.asList(new String[] {"daman", "jit", "singh"});
        List<String> l = List.of("daman", "jit", "singh");
        System.out.println(l.stream().filter(s -> s.length()> 3).collect(Collectors.toList()));
    }

}