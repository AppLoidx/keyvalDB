package com.itmo.lab;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FileIO {
    private final String dbFileName;
    private RandomAccessFile raf = null;

    public FileIO(String dbFileName) {
        this.dbFileName = dbFileName;
        try {
            this.raf = new RandomAccessFile(dbFileName, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String readValue(int offset) {
        // offset + value size
        try {
            raf.seek(offset + 32);
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
