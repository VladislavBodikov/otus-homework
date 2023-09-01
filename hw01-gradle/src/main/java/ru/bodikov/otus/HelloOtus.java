package ru.bodikov.otus;

import com.google.common.base.Joiner;

import java.util.List;

public class HelloOtus {

    public static void main(String[] args) {
        List<String> words = List.of("first", "second", "third");
        System.out.println("Used Joiner from com.google.common.base.Joiner");
        System.out.println(Joiner.on(',').appendTo(new StringBuilder(), words));
        System.out.println(Joiner.on('*').appendTo(new StringBuilder(), words));
        System.out.println(Joiner.on('/').appendTo(new StringBuilder(), words));
    }

}
