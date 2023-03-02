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

        try (ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream("hashmap.index"))) {
            //noinspection unchecked
            offsetMap = (HashMap<String, Integer>) objectInput.readObject();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class not found");
            e.printStackTrace();
        }
    }

    private void writeToFile(HashMap<String, Integer> hashMap) {
        try (ObjectOutputStream myObjectOutStream = new ObjectOutputStream(new FileOutputStream("hashmap.index"))) {
            myObjectOutStream.writeObject(hashMap);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
