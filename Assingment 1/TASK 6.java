
class Vehicle {
    String vehicleNo;

    Vehicle(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    void display() {
        System.out.println("Vehicle Number: " + vehicleNo);
    }
}

class Car extends Vehicle {
    int doors;

    Car(String vehicleNo, int doors) {
        super(vehicleNo);
        this.doors = doors;
    }

    void displayCar() {
        super.display();
        System.out.println("Vehicle Type: Car");
        System.out.println("Number of Doors: " + doors);
    }
}

class TwoWheeler extends Vehicle {
    boolean helmetRequired;

    TwoWheeler(String vehicleNo, boolean helmetRequired) {
        super(vehicleNo);
        this.helmetRequired = helmetRequired;
    }

    void displayTwoWheeler() {
        super.display();
        System.out.println("Vehicle Type: Two-Wheeler");
        System.out.println("Helmet Required: " + helmetRequired);
    }
}

public class Main {
    public static void main(String[] args) {
        Car car = new Car("TN01AB1234", 4);
        TwoWheeler bike =
            new TwoWheeler("TN02CD5678", true);

        car.displayCar();

        System.out.println();

        bike.displayTwoWheeler();
    }
}
