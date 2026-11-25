
class Vehicle {
    String registrationNumber;
    String vehicleType;

    Vehicle(String regNo, String type) {
        registrationNumber = regNo;
        vehicleType = type;
    }

    void display() {
        System.out.println("Registration: " + registrationNumber);
        System.out.println("Vehicle Type: " + vehicleType);
    }
}

class ParkingTicket {
    int ticketNumber;

    ParkingTicket(int number) {
        ticketNumber = number;
    }

    void displayTicket() {
        System.out.println("Ticket Number: " + ticketNumber);
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle v = new Vehicle("TN01AB1234", "Car");
        ParkingTicket t = new ParkingTicket(101);

        System.out.println("Campus Smart-Parking System");
        v.display();
        t.displayTicket();
    }
}
