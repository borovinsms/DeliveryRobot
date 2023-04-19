package org.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    ;

    public static synchronized void incrementMapByKey(Integer key) {
        if (sizeToFreq.containsKey(key)) {
            Integer counter = sizeToFreq.get(key);
            sizeToFreq.put(key, counter + 1);
        } else {
            sizeToFreq.put(key, 1);
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void countFrequency(String route, char letter) {
        boolean isFrequencyFound = false;
        int frequencyLength = 0;
        char[] chars = route.toCharArray();
        for (char c : chars) {
            if (c == letter) {
                frequencyLength++;
            } else {
                if (frequencyLength > 1) {
                    isFrequencyFound = true;
                    incrementMapByKey(frequencyLength);
                }
                frequencyLength = 0;
            }
        }
        if (!isFrequencyFound) {
            incrementMapByKey(1);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                countFrequency(route, 'R');
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ignored) {
                }
            }).start();
        }
        Map.Entry<Integer, Integer> maxEntry = Collections.max(sizeToFreq.entrySet(), Map.Entry.comparingByValue());
        System.out.format("Самое частое количество повторений %d (встретилось %d раз) \n",maxEntry.getKey(),maxEntry.getValue());
        sizeToFreq.remove(maxEntry.getKey());
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> entry: sizeToFreq.entrySet()) {
            System.out.format("- %d (%d раз) \n",entry.getKey(),entry.getValue());
        }
    }
}