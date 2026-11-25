
interface FeeCalculable {
    double calculateFee();
}

abstract class Vehicle {
    String vehicleNo;

    Vehicle(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    abstract void displayDetails();
}

class Car extends Vehicle implements FeeCalculable {

    Car(String vehicleNo) {
        super(vehicleNo);
    }

    @Override
    public double calculateFee() {
        return 90.0;
    }

    @Override
    void displayDetails() {
        System.out.println("Vehicle Number: " + vehicleNo);
        System.out.println("Vehicle Type: Car");
        System.out.println("Parking Fee: Rs " + calculateFee());
    }
}

public class Main {
    public static void main(String[] args) {
        Car car = new Car("TN01AB1234");
        car.displayDetails();
    }
}
