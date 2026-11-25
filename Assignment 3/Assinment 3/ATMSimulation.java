
import java.util.InputMismatchException;
import java.util.Scanner;

public class ATMSimulation {

    private static final double BALANCE = 5000.0;

    static void withdraw(String accountNumber, double amount) {

        if (amount < 0)
            throw new IllegalArgumentException(
                "Withdrawal amount cannot be negative");

        if (amount == 0)
            throw new IllegalArgumentException(
                "Withdrawal amount must be greater than zero");

        if (amount > BALANCE)
            throw new IllegalArgumentException(
                "Insufficient balance");

        System.out.printf(
            "Withdrawal successful for account %s. Remaining balance: %.2f%n",
            accountNumber, BALANCE - amount);
    }

    public static void main(String[] args) {

        try (Scanner input = new Scanner(System.in)) {

            System.out.print("Enter account number: ");
            String account = input.nextLine();

            boolean cardReaderAvailable = true;

            if (!cardReaderAvailable) {
                throw new IllegalStateException(
                    "Card-reader device is unavailable");
            }

            System.out.print("Enter withdrawal amount: ");

            if (!input.hasNextDouble()) {
                throw new InputMismatchException(
                    "Withdrawal amount must be numeric");
            }

            double amount = input.nextDouble();

            withdraw(account, amount);

        } catch (InputMismatchException e) {
            System.out.println("Input error: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            System.out.println("Transaction rejected: " + e.getMessage());

        } catch (IllegalStateException e) {
            System.out.println("Device error: " + e.getMessage());

        } catch (RuntimeException e) {
            System.out.println("Unexpected error: " + e.getMessage());

        } finally {
            System.out.println(
                "ATM session closed. Please collect your card.");
        }
    }
}
