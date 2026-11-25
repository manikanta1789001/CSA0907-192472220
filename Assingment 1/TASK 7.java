
import java.util.Objects;

class Vehicle {
    private final String vehicleNo;

    Vehicle(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void display() {
        System.out.println("Vehicle Number: " + vehicleNo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vehicle)) return false;

        Vehicle other = (Vehicle) obj;
        return Objects.equals(vehicleNo, other.vehicleNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleNo);
    }

    @Override
    public String toString() {
        return "Vehicle{vehicleNo='" + vehicleNo + "'}";
    }
}

class Car extends Vehicle {
    Car(String vehicleNo) {
        super(vehicleNo);
    }

    @Override
    public void display() {
        System.out.println(
            "Car Registration: " + getVehicleNo()
        );
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle v1 = new Vehicle("TN01AB1234");
        Vehicle v2 = new Vehicle("TN01AB1234");
        Car car = new Car("TN02CD5678");

        car.display();

        System.out.println("Vehicles Equal: " + v1.equals(v2));
        System.out.println(
            "Same Hash Code: " +
            (v1.hashCode() == v2.hashCode())
        );

        System.out.println(v1);
    }
}
