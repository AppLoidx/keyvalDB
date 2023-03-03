package com.itmo.lab.generator;

import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class DataGenerator {
    private final int leftValueLength;
    private final int rightValueLength;

    public DataGenerator(int leftValueLength, int rightValueLength) {
        this.leftValueLength = leftValueLength;
        this.rightValueLength = rightValueLength;
    }

    public Function<Integer, Map.Entry<String, String>> createGenerator() {
        // generate data: key = User_X; value = random string
        return (id) -> {
            String key = "User_" + id;
            int valueLength = ThreadLocalRandom.current().nextInt(leftValueLength, rightValueLength + 1);
            String value = generateString(valueLength);

            return new AbstractMap.SimpleEntry<>(key, value);
        };
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
