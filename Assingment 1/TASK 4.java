
class Vehicle {
    String vehicleNo;
    String vehicleType;

    // Default constructor
    Vehicle() {
        this("UNKNOWN", "Car");
    }

    // Parameterized constructor
    Vehicle(String vehicleNo, String vehicleType) {
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
    }

    // Copy constructor
    Vehicle(Vehicle other) {
        this(other.vehicleNo, other.vehicleType);
    }

    void display() {
        System.out.println("Vehicle Number: " + vehicleNo);
        System.out.println("Vehicle Type: " + vehicleType);
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle v1 = new Vehicle("TN01AB1234", "Car");
        Vehicle v2 = new Vehicle(v1);
        Vehicle v3 = new Vehicle();

        System.out.println("Original Vehicle:");
        v1.display();

        System.out.println("\nCopied Vehicle:");
        v2.display();

        System.out.println("\nDefault Vehicle:");
        v3.display();
    }
}
