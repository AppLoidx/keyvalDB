package com.itmo.lab;

import com.itmo.lab.index.HashIndex;

public class Main {
    public static void main(String[] args) {
        HashIndex hi = new HashIndex();

        hi.saveIndex();

        HashIndex hi2 = new HashIndex();
        hi2.readIndex();

        System.out.println(hi.findOffset("123"));
    }
}