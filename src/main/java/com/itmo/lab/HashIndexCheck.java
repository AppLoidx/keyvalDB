package com.itmo.lab;

import com.itmo.lab.index.HashIndex;

import java.util.HashMap;

public class HashIndexCheck {
    public static void main(String[] args) {
        HashIndex hashIndex = new HashIndex();
        // create offset map
        HashMap<String, Integer> offsetMap = new HashMap<>();
        offsetMap.put("123", 22);
        offsetMap.put("999", 90);
        offsetMap.put("777", 2048);

        // set new offset map (clear old serialized file and update it with new one)
        hashIndex.setOffsetMap(offsetMap);

        // creating another hash index instance
        HashIndex anotherHashIndex = new HashIndex();
        anotherHashIndex.readIndex(); // read from index file

        // finding offset from deserialized hash index
        System.out.println(anotherHashIndex.findOffset("777")); // should print 2048
    }
}
