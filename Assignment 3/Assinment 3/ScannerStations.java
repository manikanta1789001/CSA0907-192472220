
class ScannerStation extends Thread {
    private final int stationId;
    private final int scripts;

    ScannerStation(int stationId, int scripts) {
        this.stationId = stationId;
        this.scripts = scripts;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= scripts; i++) {
                Thread.sleep(100);
            }

            System.out.println("Station " + stationId +
                    " scanned " + scripts + " scripts.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Station " + stationId +
                    " was interrupted.");
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Thread[] stations = new Thread[4];

        for (int i = 0; i < stations.length; i++) {
            stations[i] = new ScannerStation(i + 1, 5);
            stations[i].start();
        }

        for (Thread station : stations) {
            station.join();
        }

        System.out.println(
            "All stations finished. Total scripts scanned = 20"
        );

        Runnable demoTask = () ->
            System.out.println("Runnable task executed by " +
                    Thread.currentThread().getName());

        Thread runnableThread = new Thread(demoTask);
        runnableThread.start();
        runnableThread.join();
    }
}
