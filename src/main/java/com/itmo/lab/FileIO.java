package com.itmo.lab;

import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FileIO {
    private final RandomAccessFile raf;
    private final int keySize;

    public FileIO(String dbFileName, int keySize) {
        this.keySize = keySize;
        try {
            this.raf = new RandomAccessFile(dbFileName, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, Long> getOffsetMap() {
        HashMap<String, Long> offsetMap = new HashMap<>();

        byte[] key = new byte[keySize];
        long entryOffset;
        try {
            raf.seek(0);
            while (raf.getFilePointer() != raf.length()) {
                entryOffset = raf.getFilePointer();
                raf.read(key);
                int valueSize = raf.readInt();
                raf.skipBytes(valueSize);
                offsetMap.put(new String(key, StandardCharsets.UTF_8).trim(), entryOffset);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return offsetMap;
    }

    public String readValueByOffset(long offset) {
        // offset + value size
        try {
            raf.seek(offset + keySize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // read value
        byte[] value;
        try {
            int size = raf.readInt();
            value = new byte[size];
            raf.read(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new String(value, StandardCharsets.UTF_8);
    }

    public void writeGeneratedData(Function<Integer, Map.Entry<String, String>> fun, int samplesAmount) {
        System.out.println("Writing data to the file...");

        for (int i = 0; i < samplesAmount; i++) {
            Map.Entry<String, String> entry = fun.apply(i);
            writeKey(entry.getKey(), keySize);
            writeValue(entry.getValue());
        }

        System.out.println("Done!");
    }

    public void writeKey(String val, int size)  {
        byte[] byteVal = Arrays.copyOf(val.getBytes(StandardCharsets.UTF_8), size);
        try {
            raf.write(byteVal);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // writes value after it's size
    // 4 bytes for size
    public void writeValue(String val) {
        try {
            raf.writeInt(val.length());
            raf.writeBytes(val);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            raf.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
