package virusanalyzer;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class VirusAnalyzer {
    public static void main(String[] args) {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select file or folder to scan");
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;
            File input = chooser.getSelectedFile();

            if (input.isDirectory()) {
                // mode batch RL
                double epsilon = 0.2;  // taux d'exploration
                RLBatchScanner scanner = new RLBatchScanner(input, epsilon);
                scanner.scanAll();
            } else {
                // mode fichier unique (existant)
                boolean infected = scanFile(input);
                // message déjà affiché dans scanFile
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean scanFile(File file) throws Exception {
        AnalyzingLogic logic = new AnalyzingLogic();
        String checksum = logic.md5Generator(file.getPath());
        FileHandler fh = new FileHandler();
        if (!fh.readVirusDefinition()) {
            System.out.println("virusDef.txt not found.");
            return false;
        }
        int idx = logic.analyze(checksum, virusDefinitions);
        if (idx == -1) {
            System.out.println("Clean File !!");
            JOptionPane.showMessageDialog(null,
                file.getName() + " is clean. MD5: " + checksum,
                "No Virus", JOptionPane.INFORMATION_MESSAGE);
            return false;
        } else {
            System.out.println("Virus Detected !!");
            JOptionPane.showMessageDialog(null,
                file.getName() + " contains a virus. MD5: " + checksum +
                "\nName: " + virusNames.get(idx) +
                "\nType: " + virusTypes.get(idx),
                "Virus Detected", JOptionPane.WARNING_MESSAGE);
            return true;
        }
    }

    // liste statiques existantes
    public static java.util.ArrayList<String> virusDefinitions = new java.util.ArrayList<>();
    public static java.util.ArrayList<String> virusNames = new java.util.ArrayList<>();
    public static java.util.ArrayList<String> virusTypes = new java.util.ArrayList<>();
}
