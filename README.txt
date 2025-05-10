# SimpleVirusDetector-RL

Scanner de virus en Java amélioré avec un mode adaptatif basé sur l'apprentissage par renforcement (ε-greedy).  
Le programme permet de détecter des fichiers malveillants à partir de leurs empreintes MD5, soit un par un, soit en scannant un dossier entier.

---

## 📁 Structure du projet

- `src/virusanalyzer/` : contient le code source (Java)
- `virusDef.txt` : définitions de virus sous forme de hash MD5 (1000 lignes)
- `dist/VirusAnalyzer.jar` : version exécutable packagée

---

## ▶️ Exécution du projet

### Depuis l'IDE (Eclipse ou NetBeans)

1. Ouvrir le projet depuis l'IDE.
2. Exécuter `VirusAnalyzer.java` (classe principale).
3. Choisir un fichier ou un dossier à scanner.

### Depuis le terminal

1. Ouvre un terminal et rends-toi dans le dossier `dist`.
2. Lance :
   ```bash
   java -jar VirusAnalyzer.jar
