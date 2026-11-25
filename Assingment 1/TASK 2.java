
public class Main {
    public static void main(String[] args) {
        int[] slotsPerFloor = {3, 2, 4};
        String[][] slots = new String[slotsPerFloor.length][];

        for (int i = 0; i < slotsPerFloor.length; i++) {
            slots[i] = new String[slotsPerFloor[i]];
        }

        slots[0][0] = "TN01AB1234";
        slots[1][0] = "TN02CD5678";

        System.out.println("Campus Parking Data");
        System.out.println("Total Floors: " + slots.length);

        for (int i = 0; i < slots.length; i++) {
            System.out.println("Floor " + (i + 1)
                    + " Slots: " + slots[i].length);
        }

        System.out.println("Floor 1, Slot 1: " + slots[0][0]);
        System.out.println("Floor 2, Slot 1: " + slots[1][0]);
        System.out.println("Floor 1, Slot 2: " + slots[0][1]);
    }
}
