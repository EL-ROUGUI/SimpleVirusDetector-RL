# SimpleVirusDetector-RL

A Java virus scanner with an adaptive mode based on reinforcement learning (ε-greedy). Detects malicious files from their MD5 fingerprints — one file at a time or by scanning a whole folder.

## Features

- MD5 signature-based detection (1,000 virus definitions in `virusDef.txt`)
- Adaptive scan prioritization using ε-greedy reinforcement learning
- Scan a single file or a full directory
- Swing graphical interface

## Project structure

- `src/virusanalyzer/` — Java source code
- `virusDef.txt` — virus definitions as MD5 hashes (1,000 entries)
- `dist/VirusAnalyzer.jar` — packaged executable

## Run the project

### From an IDE (Eclipse or NetBeans)

1. Open the project in the IDE.
2. Run `VirusAnalyzer.java` (main class).
3. Choose a file or folder to scan.

### From the terminal

1. Open a terminal and go to the `dist` folder.
2. Run:
   ```bash
   java -jar VirusAnalyzer.jar
   ```

## Requirements

- Java 8+
