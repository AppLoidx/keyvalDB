package com.itmo.lab;

import com.itmo.lab.index.HashIndex;

import java.util.HashMap;

public class HashIndexCheck {
    public static void main(String[] args) {
        HashIndex hashIndex = new HashIndex("hashmap.index");
        // create offset map
        HashMap<String, Long> offsetMap = new HashMap<>();
        offsetMap.put("123", 22L);
        offsetMap.put("999", 90L);
        offsetMap.put("777", 2048L);

        // set new offset map (clear old serialized file and update it with new one)
        hashIndex.setOffsetMap(offsetMap);

        // creating another hash index instance
        HashIndex anotherHashIndex = new HashIndex("hashmap.index");
        anotherHashIndex.loadIndex(); // read from index file

        // finding offset from deserialized hash index
        System.out.println(anotherHashIndex.findOffset("777")); // should print 2048
    }
}
