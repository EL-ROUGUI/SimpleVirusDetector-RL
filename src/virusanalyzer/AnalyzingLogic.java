package virusanalyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class AnalyzingLogic {
    
    /**
     * Génère et retourne la somme de contrôle MD5 du fichier donné par son chemin.
     */
    public String md5Generator(String file_path) {
        String md5Checksum = null;
        try {
            // 1) Créer l’instance MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 2) Lire le fichier en un tableau d'octets
            byte[] fileBytes = Files.readAllBytes(Paths.get(file_path));
            // 3) Mettre à jour le digest
            md.update(fileBytes);
            // 4) Calculer le digest final
            byte[] digest = md.digest();
            // 5) Convertir en chaîne hexadécimale
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            md5Checksum = sb.toString();
            
        } catch (NoSuchAlgorithmException e) {
            System.err.println("MD5 algorithm not found");
        } catch (IOException e) {
            System.err.println("IO error reading file: " + e.getMessage());
        }
        return md5Checksum;
    }
    
    public int analyze(String fileChecksum, ArrayList<String> virusDefinitions) {
        int index = -1;
        for (int i = 0; i < virusDefinitions.size(); i++) {
            if (fileChecksum != null && fileChecksum.equals(virusDefinitions.get(i))) {
                return i;
            }
        }
        return index;
    }
}
