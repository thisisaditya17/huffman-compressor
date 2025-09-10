# Huffman Compressor

A high-performance file compression utility implementing the Huffman coding algorithm in Java.

---

## Features

- **Lossless Compression**: Achieves 30–50% compression ratio on text files  
- **Complete Round-Trip**: Perfect compression and decompression cycle  
- **Professional CLI**: Clean command-line interface with intuitive usage  
- **Custom File Format**: Structured compressed files with embedded metadata  
- **Cross-Platform**: Works on any system with Java 17+  
- **Maven Built**: Professional build system and dependency management  

---

## Quick Start

### Prerequisites

- Java 17 or higher  
- Maven 3.6+ (for building from source)  

### Installation

#### Option 1: Download Pre-built JAR

Download `huffman-compressor-1.0.0.jar` from the releases section.

#### Option 2: Build from Source

```sh
git clone https://github.com/thisisaditya17/huffman-compressor
cd huffman-compressor
mvn clean package
```

### Usage

```sh
# Compress a file
java -jar huffman-compressor-1.0.0.jar -c input.txt

# Decompress with custom output name
java -jar huffman-compressor-1.0.0.jar -d compressed.huf output.txt
```

---

## Performance

### Compression Results

- **Text files**: 30–50% size reduction  
- **Source code**: 35–45% size reduction  
- **Processing speed**: ~5MB/sec on modern hardware  

### Example

```
Original file:    10,189 bytes
Compressed file:   6,026 bytes
Compression ratio: 0.59 (41% space savings)
```

---

## File Format

Compressed files use a custom format:

```
[Header: Code table size]
[Code Table: Character → Binary code mappings]
[Padding Info: Number of padding bits]
[Compressed Data: Actual file content as bits]
```

---

## Project Structure

```
src/main/java/com/example/huffman
├── Main.java               # CLI interface and application entry point
├── HuffmanCompressor.java  # Core compression/decompression algorithms
├── HuffmanNode.java        # Binary tree node data structure
└── FrequencyCounter.java   # Character frequency analysis
```

---

## Author

**Aditya Joshi**  
- Portfolio: [https://thisisaditya17.github.io/portfolio-website/](https://thisisaditya17.github.io/portfolio-website/)  
- LinkedIn: [https://www.linkedin.com/in/thisisaditya17](https://www.linkedin.com/in/thisisaditya17)  
- GitHub: [https://www.github.com/thisisaditya17](https://www.github.com/thisisaditya17)  

---

## Acknowledgments

- Huffman coding algorithm originally developed by David A. Huffman (1952)  
- Inspired by classical algorithms from "Algorithms, 4th Edition" by Sedgewick & Wayne  
- Built using modern Java development practices and Maven build system  

