package com.itmo.lab;

import com.itmo.lab.generator.DataGenerator;
import com.itmo.lab.index.HashIndex;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try (InputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            props.load(fis);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Data generation
//        DataGenerator generator = new DataGenerator( 10, 20);
//        FileIO fileIO = new FileIO(props.getProperty("db.name"), Integer.parseInt(props.getProperty("db.key.size")));
//        fileIO.writeGeneratedData(generator.createGenerator(), Integer.parseInt(props.getProperty("generator.samples")));


        // Creating index
        FileIO fileIO = new FileIO(props.getProperty("db.name"), Integer.parseInt(props.getProperty("db.key.size")));
        HashIndex hashIndex = new HashIndex("hashmap.index");
        HashMap<String, Long> offsetMap = fileIO.getOffsetMap();
        hashIndex.setOffsetMap(offsetMap);


        // Loading existing index
//        HashIndex hashIndex = new HashIndex("hashmap.index");
//        hashIndex.loadIndex();

        System.out.println(fileIO.readValueByOffset(hashIndex.findOffset("User_0")));
        System.out.println(fileIO.readValueByOffset(hashIndex.findOffset("User_1")));
        System.out.println(fileIO.readValueByOffset(hashIndex.findOffset("User_2")));

        fileIO.close();
    }
}