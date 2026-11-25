import java.io.*;
import java.util.*;

public class SalesReport {
    public static void main(String[] args) {
        List<String> stores = Arrays.asList("store1.txt", "store2.txt", "store3.txt");
        int total = 0;
        int successfulFiles = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sales-summary.txt"))) {
            for (String file : stores) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    int storeTotal = 0;
                    while ((line = reader.readLine()) != null) {
                        storeTotal += Integer.parseInt(line.trim());
                    }
                    total += storeTotal;
                    successfulFiles++;
                    writer.write(file + " total = " + storeTotal);
                    writer.newLine();
                } catch (IOException | NumberFormatException e) {
                    System.out.println("Could not process " + file + ": " + e.getMessage());
                }
            }
            writer.write("Grand total = " + total);
            writer.newLine();
            writer.write("Successful files = " + successfulFiles);
        } catch (IOException e) {
            System.out.println("Unable to write summary: " + e.getMessage());
        }
        System.out.println("Report completed. Total sales = " + total);
    }
}