package com.itmo.lab.generator;

import com.itmo.lab.FileIO;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class DataGenerator {
    private static final int KEY_SIZE = 32;
//    private static final Integer VAL_SIZE = 512;
    private static final int SAMPLES_AMOUNT = 100_000;
    private final String dbFileName;

    public DataGenerator(String dbFileName) {
        this.dbFileName = dbFileName;
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
        // generate data: key = User_X; value = random string
        System.out.print("Writing data to the file...");
        for (int i = 0; i < SAMPLES_AMOUNT; i++) {
            String key = "User_" + i;
            String value = generateString(20);

            FileIO fileIO = new FileIO(dbFileName);
            fileIO.writeKey(key, KEY_SIZE);
            fileIO.writeValue(value);
        }
        System.out.println("Done!");
    }

    public String generateString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
            .limit(length)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    /*
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
     */
}
