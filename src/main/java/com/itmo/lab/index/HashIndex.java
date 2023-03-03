package com.itmo.lab.index;

import java.io.*;
import java.util.HashMap;

public class HashIndex {
    private final String indexFineName;
    private HashMap<String, Long> offsetMap = new HashMap<>();

    public HashIndex(String indexFineName) {
        this.indexFineName = indexFineName;
    }

    public Long findOffset(String val) {
        return offsetMap.get(val);
    }

    public void setOffsetMap(HashMap<String, Long> offsetMap) {
        clearIndex();   // clear old hash index file
        this.offsetMap = offsetMap;
        saveIndex();    // save new index file
    }

    private void saveIndex() {
        writeToFile(offsetMap);
    }

    public void clearIndex() {
        writeToFile(new HashMap<>());
    }

    public void loadIndex() {
        // ...

        try (ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(indexFineName))) {
            //noinspection unchecked
            offsetMap = (HashMap<String, Long>) objectInput.readObject();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class not found");
            e.printStackTrace();
        }
    }

    private void writeToFile(HashMap<String, Long> hashMap) {
        try (ObjectOutputStream myObjectOutStream = new ObjectOutputStream(new FileOutputStream(indexFineName))) {
            myObjectOutStream.writeObject(hashMap);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
