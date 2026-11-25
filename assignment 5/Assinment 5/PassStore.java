import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PassStore {
    private final Path file;
    public PassStore(Path file) { this.file = file; }

    public void add(String passId, String regNo, String name, int route,
                   String boardingStop, String validityDate, double amount) throws IOException {
        Files.createDirectories(file.toAbsolutePath().getParent());
        try (BufferedWriter w = Files.newBufferedWriter(file,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            w.write(String.join(",", passId, regNo, name, String.valueOf(route),
                    boardingStop, validityDate, String.valueOf(amount)));
            w.newLine();
        }
    }

    public String[] find(String passId) throws IOException {
        if (!Files.exists(file)) return null;
        try (BufferedReader r = Files.newBufferedReader(file)) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = line.split(",", -1);
                if (fields.length != 7) continue; 
                try {
                    Integer.parseInt(fields[3]);
                    Double.parseDouble(fields[6]);
                } catch (NumberFormatException ex) {
                    continue;
                }
                if (fields[0].equals(passId)) return fields;
            }
        }
        return null;
    }

    public List<String[]> listByRoute(int route) throws IOException {
        List<String[]> matches = new ArrayList<>();
        if (!Files.exists(file)) return matches;
        try (BufferedReader r = Files.newBufferedReader(file) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] f = line.split(",", -1);
                if (f.length != 7) continue;
                try {
                    if (Integer.parseInt(f[3]) == route) matches.add(f);
                } catch (NumberFormatException ignored) { /* damaged record */ }
            }
        }
        return matches;
    }

    public static void main(String[] args) throws IOException {
        Path f = Files.createTempFile("passes", ".csv");
        PassStore store = new PassStore(f);
        store.add("P1001", "192311001", "Arun", 12, "Central", "2027-03-31", 4500);
        Files.writeString(f, "P1002,DAMAGED LINE\\n", StandardOpenOption.APPEND);
        store.add("P1003", "192311003", "Divya", 7, "East Gate", "2027-03-31", 3800);
        System.out.println("Find P1003: " + Arrays.toString(store.find("P1003")));
        System.out.println("Find P1002: " + Arrays.toString(store.find("P1002")));
        System.out.println("Route 7 count: " + store.listByRoute(7).size());
        Files.deleteIfExists(f);
    }
}
