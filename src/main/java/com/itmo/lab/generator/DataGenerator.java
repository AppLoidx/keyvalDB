package com.itmo.lab.generator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataGenerator {
    private static final Integer KEY_SIZE = 256;
    private static final Integer VAL_SIZE = 512;

    private byte[] toBytes(String key, int size) {
        byte[] dst = new byte[size];
        byte[] src = key.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(src, 0, dst, 0, src.length);

        return dst;
    }

    private String byteToString(byte[] bytes) {
        int firstNonZeroByteIndex = 0;
        for (byte b : bytes) {
            if (b == 0) {
                break;
            } else {
                firstNonZeroByteIndex++;
            }
        }


        return new String(bytes, 0, firstNonZeroByteIndex, StandardCharsets.UTF_8);
    }

    public void generate() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Egor", "11");
        map.put("Sanya", "15");
        map.put("Jenya", "22");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            writeToFile(entry);
        }

    }

    public void writeToFile(Map.Entry<String, String> entry) {
        try (FileOutputStream outputStream = new FileOutputStream("data.db", true)) { // open in append mode
            byte[] data = new byte[KEY_SIZE + VAL_SIZE];
            byte[] keyBytes = toBytes(entry.getKey(), KEY_SIZE);
            byte[] valBytes = toBytes(entry.getValue(), VAL_SIZE);
            // write key
            System.arraycopy(keyBytes, 0, data, 0, keyBytes.length);
            // write val
            System.arraycopy(valBytes, 0, data, KEY_SIZE, valBytes.length);
            outputStream.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkRead() {
        File file = new File("data.db");
        byte[] bytes = new byte[(int) file.length()];

        try(FileInputStream fis = new FileInputStream(file)) {
            fis.read(bytes);
            byte[] key = new byte[KEY_SIZE];
            byte[] val = new byte[VAL_SIZE];
            System.arraycopy(bytes, 0, key, 0, KEY_SIZE);
            System.arraycopy(bytes, KEY_SIZE, val, 0, VAL_SIZE);

            String keyStr = byteToString(key);
            String valStr = byteToString(val);

            System.out.println("First key is " + keyStr);
            System.out.println("Value of first key is " + valStr);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        DataGenerator dataGenerator = new DataGenerator();
        dataGenerator.generate();

        dataGenerator.checkRead();
    }
}
