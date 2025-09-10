package com.example.huffman;
import java.util.*;

public class HuffmanNode implements Comparable<HuffmanNode> {
    int frequency;
    char character;
    HuffmanNode left;
    HuffmanNode right;

    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    public HuffmanNode(HuffmanNode left, HuffmanNode right) {
        this.character = '\0';
        this.frequency = left.frequency + right.frequency;
        this.left = left;
        this.right = right;
    }

    public Boolean isLeaf() {
        return left == null && right == null;
    }

    public char getCharacter() {
        return character;
    }

    public int getFrequency() {
        return frequency;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "character=" + character +
                ", frequency=" + frequency +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof HuffmanNode)) return false;
        HuffmanNode other = (HuffmanNode) obj;
        return this.character == other.character && this.frequency == other.frequency;
    }

    @Override
    public int compareTo(HuffmanNode other) {
        return Integer.compare(this.frequency, other.frequency);
    }

    
}
