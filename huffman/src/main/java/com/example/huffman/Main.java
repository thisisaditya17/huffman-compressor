package com.example.huffman;
import java.util.*;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            printUsage();
            return;
        }

        String operation = args[0];
        String inputFile = args[1];
        String outputFile = (args.length >= 3) ? args[2] : null;

        try {
            switch (operation.toLowerCase()) {
                case "-c":
                case "compress":
                    compressFile(inputFile, outputFile);
                    break;
                case "-d":
                case "decompress":
                    decompressFile(inputFile, outputFile);
                    break;
                default:
                    System.out.println("Unknown operation: " + operation);
                    printUsage();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void printUsage() {
        System.out.println("Huffman Compression Utility");
        System.out.println("Usage:");
        System.out.println("  Compress:   java -jar huffman.jar -c input.txt [output.huf]");
        System.out.println("  Decompress: java -jar huffman.jar -d input.huf [output.txt]");
    }

    private static void compressFile(String inputFile, String outputFile) {
        if (!new File(inputFile).exists()) {
            System.err.println("Error: Input file '" + inputFile + "' does not exist.");
            return;
        }
        if (outputFile == null) {
            outputFile = inputFile + ".huf";
        }

        System.out.println("Compressing: " + inputFile + " → " + outputFile);
        
        long originalSize = new File(inputFile).length();
        HuffmanCompressor.compress(inputFile);

        if (!outputFile.equals("compressed.huf")) {
            new File("compressed.huf").renameTo(new File(outputFile));
        }

        long compressedSize = new File(outputFile).length();
        double compressionRatio = (double) compressedSize / originalSize;
        
        System.out.println("✓ Compression completed!");
        System.out.println("Original size: " + originalSize + " bytes");
        System.out.println("Compressed size: " + compressedSize + " bytes");
        System.out.println("Compression ratio: " + String.format("%.2f", compressionRatio));
    }

    private static void decompressFile(String inputFile, String outputFile){
        if (!new File(inputFile).exists()) {
            System.err.println("Error: Input file '" + inputFile + "' does not exist.");
            return;
        }

        if (outputFile == null) {
            outputFile = inputFile.replaceAll("\\.huf$", "_decompressed.txt");
        }

        System.out.println("Decompressing: " + inputFile + " → " + outputFile);
        
        HuffmanCompressor.decompress(inputFile, outputFile);
        System.out.println("✓ Decompression completed successfully!");
        System.out.println("Output saved to: " + outputFile);
    }
}
