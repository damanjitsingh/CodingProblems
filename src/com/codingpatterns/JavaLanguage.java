package com.codingpatterns;

public class JavaLanguage {

    public static void main(String[] args) {
        names("Daman", "Jit", "Singh");
    }

    public static void names(String... names) {
        for (int i=0; i < names.length; i++) {
            System.out.println(names[i]);
        }
    }

}
