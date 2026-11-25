
class Vehicle {
    String vehicleNo;

    Vehicle(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    double calculateFee() {
        return 0.0;
    }

    void displayFee() {
        System.out.println(
            getClass().getSimpleName() + " " + vehicleNo
            + " -> Rs " + calculateFee()
        );
    }
}

class TwoWheeler extends Vehicle {
    TwoWheeler(String vehicleNo) {
        super(vehicleNo);
    }

    @Override
    double calculateFee() {
        return 30.0;
    }
}

class Car extends Vehicle {
    Car(String vehicleNo) {
        super(vehicleNo);
    }

    @Override
    double calculateFee() {
        return 90.0;
    }
}

class ElectricCar extends Car {
    ElectricCar(String vehicleNo) {
        super(vehicleNo);
    }

    @Override
    double calculateFee() {
        return 210.0;
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle[] vehicles = {
            new TwoWheeler("TN01A1"),
            new Car("TN02B2"),
            new ElectricCar("TN03C3")
        };

        for (Vehicle v : vehicles) {
            v.displayFee();
        }

        System.out.println(
            !TwoWheeler.class.equals(Car.class)
        );
    }
}
