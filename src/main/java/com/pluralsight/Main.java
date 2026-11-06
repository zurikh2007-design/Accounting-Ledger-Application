package com.pluralsight;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        TransactionManager manager = new TransactionManager();

        try {
            manager.readTransactions();

            boolean running = true;

            while (running) {

                System.out.println("\n===== FINANCIAL TRACKER =====");
                System.out.println("D) Add Deposit");
                System.out.println("P) Make Payment");
                System.out.println("L) Ledger");
                System.out.println("X) Exit");
                System.out.print("> ");

                String choice = scanner.nextLine()
                        .trim()
                        .toUpperCase();

                switch (choice) {

                    case "D":
                        addTransaction(scanner, manager, true);
                        break;

                    case "P":
                        addTransaction(scanner, manager, false);
                        break;

                    case "L":
                        manager.displayLedger(scanner);
                        break;

                    case "X":
                        running = false;
                        System.out.println("Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());

        } finally {
            scanner.close();
        }
    }

    private static void addTransaction(
            Scanner scanner,
            TransactionManager manager,
            boolean deposit
    ) throws IOException {

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine().trim();

        double amount;

        while (true) {

            System.out.print("Amount: ");

            try {
                amount = Double.parseDouble(scanner.nextLine());

                if (amount <= 0) {
                    System.out.println("Enter an amount greater than zero.");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        if (!deposit) {
            amount = -amount;
        }

        Transaction transaction = new Transaction(
                LocalDate.now(),
                LocalTime.now(),
                description,
                vendor,
                amount
        );

        manager.addTransaction(transaction);

        System.out.println("Transaction saved successfully!");
    }
}