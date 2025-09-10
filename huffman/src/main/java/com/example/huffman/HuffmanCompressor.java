package com.example.huffman;
import java.io.*;
import java.util.*   ;


public class HuffmanCompressor {

    public static HuffmanNode buildHuffmanTree(Map<Character, Integer> frequencies) {
        if (frequencies.isEmpty()) {
            return null; 
        }
        
        PriorityQueue<HuffmanNode> sortedNodes = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            sortedNodes.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
        if (sortedNodes.size() == 1) {
            return sortedNodes.poll();
        }

        while (sortedNodes.size() > 1) {
            HuffmanNode left = sortedNodes.poll();
            HuffmanNode right = sortedNodes.poll();
            HuffmanNode parent = new HuffmanNode(left, right);
            sortedNodes.add(parent);
        }
        return sortedNodes.poll();
    }

    public static Map<Character, String> generateCode(HuffmanNode root) {
        Map<Character, String> binaryCharTable = new HashMap<>();

        if (root.isLeaf()) {
            binaryCharTable.put(root.getCharacter(), "0");
            return binaryCharTable;
        }

        HuffmanNode traverse = root;
        StringBuilder binaryCode = new StringBuilder();
        
        generateCodeHelper(traverse, binaryCode, binaryCharTable);
        return binaryCharTable;
            
    }

    private static void generateCodeHelper(HuffmanNode node, StringBuilder code, Map<Character, String> table) {
        if (node.isLeaf()) {
            table.put(node.getCharacter(), code.toString());
            return;
        }
        if (node.getLeft() != null) {
            code.append('0');
            generateCodeHelper(node.getLeft(), code, table);
            code.deleteCharAt(code.length() - 1);
        }
        if (node.getRight() != null) {
            code.append('1');
            generateCodeHelper(node.getRight(), code, table);
            code.deleteCharAt(code.length() - 1);
        }
    }

    public static void compress(String filename) {
        StringBuilder compressedBinary = new StringBuilder();
        Map<Character, Integer> frequencies = FrequencyCounter.countFrequencies(filename);
        HuffmanNode root = buildHuffmanTree(frequencies);
        Map<Character, String> codes = generateCode(root);

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                char c = (char) ch;
                compressedBinary.append(codes.get(c));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileOutputStream fos = new FileOutputStream("compressed.huf")) {
            writeCodeTable(fos, codes);

            writePaddingInfo(fos, compressedBinary.length());

            byte[] compressedBytes = convertBitStringToBytes(compressedBinary.toString());
            fos.write(compressedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] convertBitStringToBytes(String bitString) {
        int byteLength = (bitString.length() + 7) / 8;
        byte[] bytes = new byte[byteLength];
        for (int i = 0; i < bitString.length(); i++) {
            if (bitString.charAt(i) == '1') {
                bytes[i / 8] |= (1 << (7 - (i % 8)));
            }
        }
        return bytes;
    }

    private static void writeCodeTable(FileOutputStream fos, Map<Character, String> codes) throws IOException {
        fos.write(codes.size());
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            fos.write((byte) entry.getKey().charValue());
            String code = entry.getValue();
            fos.write(code.length());
            byte[] codeBytes = convertBitStringToBytes(code);
            fos.write(codeBytes);
        }
    }

    private static void writePaddingInfo(FileOutputStream fos, int bitLength) throws IOException {
        int padding = (8 - (bitLength % 8)) % 8;
        fos.write(padding);
    }

    public static void decompress(String compressedFilename, String outputFilename) {
        try (FileInputStream fis = new FileInputStream(compressedFilename)) {
            int tableSize = fis.read();
            Map<String, Character> codeToChar = readCodeTable(fis, tableSize);
            int paddingBits = fis.read();
            byte[] compressedData = fis.readAllBytes();
            String bitString = bytesToBitString(compressedData);

            if (paddingBits > 0) {
                bitString = bitString.substring(0, bitString.length() - paddingBits);
            }

            StringBuilder currentCode = new StringBuilder();
            StringBuilder output = new StringBuilder();

            for (char bit : bitString.toCharArray()) {
                currentCode.append(bit);
                if (codeToChar.containsKey(currentCode.toString())) {
                    output.append(codeToChar.get(currentCode.toString()));
                    currentCode.setLength(0); // Reset for next code
                }
            }

            try (FileOutputStream fos = new FileOutputStream(outputFilename)) {
                fos.write(output.toString().getBytes());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error during decompression.");
        }
    }

    private static String bytesToBitString(byte[] bytes) {
        StringBuilder bitString = new StringBuilder();
        for (byte b : bytes) {
            for (int i = 7; i >= 0; i--) {
                bitString.append(((b >> i) & 1) == 1 ? '1' : '0');
            }
        }
        return bitString.toString();
    }


    private static Map<String, Character> readCodeTable(FileInputStream fis, int tableSize) throws IOException {
        Map<String, Character> codeToChar = new HashMap<>();
        for (int i = 0; i < tableSize; i++) {
            char character = (char) fis.read();
            int codeLength = fis.read();
            int byteLength = (codeLength + 7) / 8;
            byte[] codeBytes = new byte[byteLength];
            fis.read(codeBytes);
            StringBuilder code = new StringBuilder();
            for (int j = 0; j < codeLength; j++) {
                int byteIndex = j / 8;
                int bitIndex = 7 - (j % 8);
                if ((codeBytes[byteIndex] & (1 << bitIndex)) != 0) {
                    code.append('1');
                } else {
                    code.append('0');
                }
            }
            codeToChar.put(code.toString(), character);
        }
        return codeToChar;
    }

}
