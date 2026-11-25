
class Vehicle {
    static int totalVehicles = 0;

    String vehicleNo;

    Vehicle(String vehicleNo) {
        this.vehicleNo = vehicleNo;
        totalVehicles++;
    }

    static void displayTotal() {
        System.out.println("Total Vehicles: " + totalVehicles);
    }
}

public class Main {

    static void updateVehicle(Vehicle v) {
        v.vehicleNo = "TN99ZZ9999";
    }

    public static void main(String[] args) {
        Vehicle v1 = new Vehicle("TN01AB1234");
        Vehicle v2 = new Vehicle("TN02CD5678");

        Vehicle.displayTotal();

        System.out.println("Before Update: " + v1.vehicleNo);
        updateVehicle(v1);
        System.out.println("After Update: " + v1.vehicleNo);

        v2 = null;
        System.out.println("Second vehicle reference set to null.");

        // v2's object may now be eligible for garbage collection.
    }
}
