
class Vehicle {
    private String vehicleNo;
    private String vehicleType;

    public Vehicle(String vehicleNo, String vehicleType) {
        setVehicleNo(vehicleNo);
        this.vehicleType = vehicleType;
    }

    public void setVehicleNo(String vehicleNo) {
        if (vehicleNo != null && !vehicleNo.trim().isEmpty()) {
            this.vehicleNo = vehicleNo;
        } else {
            System.out.println("Invalid vehicle number");
        }
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public String getVehicleType() {
        return vehicleType;
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle v = new Vehicle("TN01AB1234", "Car");

        System.out.println("Vehicle Number: " + v.getVehicleNo());
        System.out.println("Vehicle Type: " + v.getVehicleType());

        v.setVehicleNo("");
        System.out.println("After Update: " + v.getVehicleNo());
    }
}
