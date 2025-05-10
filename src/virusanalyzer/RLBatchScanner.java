package virusanalyzer;

import java.io.File;
import java.util.*;

public class RLBatchScanner {
    private Map<String, Double> Q = new HashMap<>();
    private Map<String, Integer> N = new HashMap<>();
    private Map<String, Queue<File>> filesByExt = new HashMap<>();
    private double epsilon;
    private Random random = new Random();

    public RLBatchScanner(File rootDir, double epsilon) {
        this.epsilon = epsilon;
        gatherFilesByExtension(rootDir);
        for (String ext : filesByExt.keySet()) {
            Q.put(ext, 0.0);
            N.put(ext, 0);
        }
    }

    private void gatherFilesByExtension(File dir) {
        File[] list = dir.listFiles();
        if (list == null) return;
        for (File f : list) {
            if (f.isDirectory()) {
                gatherFilesByExtension(f);
            } else {
                String name = f.getName();
                String ext = "";
                int i = name.lastIndexOf('.');
                if (i > 0 && i < name.length() - 1) {
                    ext = name.substring(i + 1).toLowerCase();
                }
                filesByExt.computeIfAbsent(ext, k -> new LinkedList<>()).add(f);
            }
        }
    }

    public void scanAll() throws Exception {
        int total = filesByExt.values().stream().mapToInt(Queue::size).sum();
        int scanned = 0;
        while (scanned < total) {
            String chosenExt = chooseExtension();
            File file = filesByExt.get(chosenExt).poll();
            if (file == null) continue;
            scanned++;
            // scan file using existing logic
            AnalyzingLogic logic = new AnalyzingLogic();
            String checksum = logic.md5Generator(file.getPath());
            FileHandler fh = new FileHandler();
            boolean ok = fh.readVirusDefinition();
            boolean infected = false;
            if (ok) {
                int idx = logic.analyze(checksum, VirusAnalyzer.virusDefinitions);
                infected = (idx != -1);
                System.out.printf("[RL] %s -> %s\n", file.getName(), infected ? "VIRUS" : "CLEAN");
            }
            // update Q
            int count = N.get(chosenExt) + 1;
            N.put(chosenExt, count);
            double reward = infected ? 1.0 : 0.0;
            double oldQ = Q.get(chosenExt);
            Q.put(chosenExt, oldQ + (reward - oldQ) / count);
        }
        System.out.println("[RL] Q-values finales: " + Q);
    }

    private String chooseExtension() {
        if (random.nextDouble() < epsilon) {
            List<String> avail = new ArrayList<>();
            for (Map.Entry<String, Queue<File>> e : filesByExt.entrySet()) {
                if (!e.getValue().isEmpty()) avail.add(e.getKey());
            }
            return avail.get(random.nextInt(avail.size()));
        } else {
            return filesByExt.keySet().stream()
                .filter(ext -> !filesByExt.get(ext).isEmpty())
                .max(Comparator.comparingDouble(Q::get))
                .orElseThrow();
        }
    }
}