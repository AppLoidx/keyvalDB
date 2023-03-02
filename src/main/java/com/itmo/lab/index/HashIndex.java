package com.itmo.lab.index;

import java.io.*;
import java.util.HashMap;

public class HashIndex {
    private HashMap<String, Integer> offsetMap = new HashMap<>();

    public Integer findOffset(String val) {
        return offsetMap.get(val);
    }

    public void saveIndex() {
        writeToFile(offsetMap);
    }

    public void setOffsetMap(HashMap<String, Integer> offsetMap) {
        clearIndex();   // clear old hash index file
        this.offsetMap = offsetMap;
        saveIndex();    // save new index file
    }

    public void clearIndex() {
        writeToFile(new HashMap<>());
    }

    public void readIndex() {
        // ...

        try {
            FileInputStream fileInput = new FileInputStream(
                    "hashmap.index");

            ObjectInputStream objectInput
                    = new ObjectInputStream(fileInput);

            //noinspection unchecked
            offsetMap = (HashMap<String, Integer>)objectInput.readObject();

            objectInput.close();
            fileInput.close();
        }

        catch (IOException obj1) {
            obj1.printStackTrace();
        }

        catch (ClassNotFoundException obj2) {
            System.out.println("Class not found");
            obj2.printStackTrace();
        }

    }

    private void writeToFile(HashMap<String, Integer> hashMap) {
        try {
            FileOutputStream myFileOutStream
                    = new FileOutputStream(
                    "hashmap.index");

            ObjectOutputStream myObjectOutStream
                    = new ObjectOutputStream(myFileOutStream);

            myObjectOutStream.writeObject(hashMap);

            // closing FileOutputStream and
            // ObjectOutputStream
            myObjectOutStream.close();
            myFileOutStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
