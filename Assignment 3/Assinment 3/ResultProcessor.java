
import java.util.*;
import java.util.concurrent.*;

public class Main {

    static class ResultTask implements Callable<String> {
        private final String fileName;
        private final String content;

        ResultTask(String fileName, String content) {
            this.fileName = fileName;
            this.content = content;
        }

        @Override
        public String call() throws Exception {
            try {
                String scoreText = content.trim();

                if (!scoreText.matches("\\d{1,3}")) {
                    throw new IllegalArgumentException(
                        "Invalid score format"
                    );
                }

                int score = Integer.parseInt(scoreText);

                if (score < 0 || score > 100) {
                    throw new IllegalArgumentException(
                        "Score must be between 0 and 100"
                    );
                }

                return fileName + ": SUCCESS, result = " +
                       (score >= 50 ? "PASS" : "FAIL");

            } catch (IllegalArgumentException e) {
                throw new Exception(
                    fileName + ": " + e.getMessage()
                );
            }
        }
    }

    public static void main(String[] args) throws Exception {

        String[] fileNames = {
            "student1.txt",
            "student2.txt",
            "student3.txt",
            "student4.txt"
        };

        String[] scores = {
            "78",
            "42",
            "bad-score",
            "105"
        };

        ExecutorService executor =
            Executors.newFixedThreadPool(4);

        List<Future<String>> futures = new ArrayList<>();

        int successful = 0;
        int failed = 0;

        try {
            for (int i = 0; i < fileNames.length; i++) {
                futures.add(
                    executor.submit(
                        new ResultTask(fileNames[i], scores[i])
                    )
                );
            }

            for (Future<String> future : futures) {
                try {
                    System.out.println(future.get());
                    successful++;

                } catch (ExecutionException e) {
                    System.out.println("ERROR: " +
                        e.getCause().getMessage());
                    failed++;
                }
            }

        } finally {
            executor.shutdown();
        }

        System.out.println("\n--- Final Summary ---");
        System.out.println("Successful files: " + successful);
        System.out.println("Failed files: " + failed);
        System.out.println("Summary total: " +
                           (successful + failed));
    }
}
