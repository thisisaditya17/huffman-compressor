package com.example.huffman;
import java.util.*;
import java.io.*;

public class FrequencyCounter {

    public static Map<Character, Integer> countFrequencies(String filename) {
        Map<Character, Integer> frequencies = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            int ch;
            while((ch = reader.read()) != -1) {
                char c = (char) ch;
                    frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);           
                }
            return frequencies;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    public static void displayFrequencies(Map<Character, Integer> frequencies) {
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            System.out.println("'" + entry.getKey() + "': " + entry.getValue());
        }
    }
     
    
}
