package com.itmo.lab.generator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {
    private static final Integer KEY_SIZE = 32;
    private static final Integer VAL_SIZE = 512;
    private static final int SAMPLES_AMOUNT = 100_000;
    private final String dbFileName;

    public DataGenerator(String dbFileName) {
        this.dbFileName = dbFileName;
    }

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
        // generate data: key = User_X; val = Random_Int
        System.out.print("Writing data to file...");
        for (int i = 0; i < SAMPLES_AMOUNT; i++) {
            String key = "User_" + i;
            String value = String.valueOf(ThreadLocalRandom.current().nextInt(12, 24));

            Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<>(key, value);
            writeToFile(entry);
        }
        System.out.println(" Done!");
    }

    public void writeToFile(Map.Entry<String, String> entry) {
        try (FileOutputStream outputStream = new FileOutputStream(dbFileName, true)) { // open in append mode
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

    public void checkRead() { // function for test
        File file = new File(dbFileName);
        byte[] bytes = new byte[(int) file.length()];

        try (FileInputStream fis = new FileInputStream(file)) {
            int res = fis.read(bytes);

            if (res == -1) {
                System.out.println("Database file is empty");
                return;
            }

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
        DataGenerator dataGenerator = new DataGenerator("data.db");
        dataGenerator.generate();

        dataGenerator.checkRead();
    }
}
