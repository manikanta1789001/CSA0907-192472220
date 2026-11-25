import java.io.*;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

public class LineCounter {
    public static long countBuffered(Path file) throws IOException {
        long count = 0;
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int b;
            while ((b = in.read()) != -1) if (b == '\n') count++;
        }
        return count;
    }

    public static long countUnbuffered(Path file) throws IOException {
        long count = 0;
        try (FileInputStream in = new FileInputStream(file)) {
            int b;
            while ((b = in.read()) != -1) if (b == '\n') count++;
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        Path file = Files.createTempFile("line-count", ".txt");

        try (BufferedWriter out = Files.newBufferedWriter(file)) {
            for (int i = 0; i < 100_000; i++) {
                out.write("Sample attendance record " + i);
                out.newLine();
            }
        }
        long start = System.nanoTime();
        long unbuffered = countUnbuffered(file);
        long unbufferedMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);

        start = System.nanoTime();
        long buffered = countBuffered(file);
        long bufferedMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);

        System.out.println("Lines unbuffered: " + unbuffered + " | time: " + unbufferedMs + " ms");
        System.out.println("Lines buffered:   " + buffered + " | time: " + bufferedMs + " ms");
        Files.deleteIfExists(file);
    }
}
