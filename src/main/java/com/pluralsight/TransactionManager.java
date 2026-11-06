package com.pluralsight;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TransactionManager {

    private final List<Transaction> transactions = new ArrayList<>();

    private final Path path = Path.of(
            "src/main/java/com/pluralsight/transactions.csv"
    );

    public void readTransactions() throws IOException {

        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.writeString(path,
                    "date|time|description|vendor|amount"
                            + System.lineSeparator());
        }

        List<String> lines = Files.readAllLines(path);

        for (String line : lines) {

            if (line.isBlank() || line.startsWith("date|")) {
                continue;
            }

            try {
                String[] parts = line.split("\\|", -1);

                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Transaction transaction = new Transaction(
                        date, time, description, vendor, amount
                );

                transactions.add(transaction);

            } catch (Exception e) {
                System.out.println("Could not read transaction: " + line);
            }
        }

        sortTransactions();
    }

    private void sortTransactions() {

        transactions.sort(
                Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed()
        );
    }

    public void addTransaction(Transaction transaction) throws IOException {

        Files.writeString(
                path,
                transaction.toCsvLine() + System.lineSeparator(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );

        transactions.add(transaction);
        sortTransactions();
    }

    public void displayLedger(Scanner scanner) {

        boolean running = true;

        while (running) {

            System.out.println("\n========== LEDGER ==========");
            System.out.println("A) All Transactions");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("> ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {

                case "A":
                    displayTransactions(transactions);
                    break;

                case "D":
                    displayTransactions(
                            transactions.stream()
                                    .filter(t -> t.getAmount() > 0)
                                    .toList()
                    );
                    break;

                case "P":
                    displayTransactions(
                            transactions.stream()
                                    .filter(t -> t.getAmount() < 0)
                                    .toList()
                    );
                    break;

                case "R":
                    displayReports(scanner);
                    break;

                case "H":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void displayTransactions(List<Transaction> list) {

        System.out.println("\n========== TRANSACTIONS ==========");

        if (list.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (Transaction transaction : list) {
            System.out.println(transaction);
        }
    }

    private void displayReports(Scanner scanner) {

        boolean running = true;

        while (running) {

            System.out.println("\n========== REPORTS ==========");
            System.out.println("M) Month To Date");
            System.out.println("P) Previous Month");
            System.out.println("Y) Year To Date");
            System.out.println("L) Previous Year");
            System.out.println("V) Search By Vendor");
            System.out.println("H) Back");
            System.out.print("> ");

            String choice = scanner.nextLine().trim().toUpperCase();

            LocalDate today = LocalDate.now();

            switch (choice) {

                case "M":
                    YearMonth currentMonth = YearMonth.now();

                    displayDateRange(
                            currentMonth.atDay(1),
                            today
                    );
                    break;

                case "P":
                    YearMonth previousMonth =
                            YearMonth.now().minusMonths(1);

                    displayDateRange(
                            previousMonth.atDay(1),
                            previousMonth.atEndOfMonth()
                    );
                    break;

                case "Y":
                    displayDateRange(
                            LocalDate.of(today.getYear(), 1, 1),
                            today
                    );
                    break;

                case "L":
                    int previousYear = today.getYear() - 1;

                    displayDateRange(
                            LocalDate.of(previousYear, 1, 1),
                            LocalDate.of(previousYear, 12, 31)
                    );
                    break;

                case "V":
                    System.out.print("Enter vendor name: ");
                    String vendor = scanner.nextLine().trim();

                    List<Transaction> results = transactions.stream()
                            .filter(t -> t.getVendor()
                                    .equalsIgnoreCase(vendor))
                            .toList();

                    displayTransactions(results);
                    break;

                case "H":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void displayDateRange(LocalDate start, LocalDate end) {

        List<Transaction> results = transactions.stream()
                .filter(t -> !t.getDate().isBefore(start))
                .filter(t -> !t.getDate().isAfter(end))
                .toList();

        System.out.println("\n========== REPORT ==========");

        if (results.isEmpty()) {
            System.out.println("No transactions found for this period.");
            return;
        }

        double deposits = 0;
        double payments = 0;

        for (Transaction transaction : results) {
            if (transaction.getAmount() > 0) {
                deposits += transaction.getAmount();
            } else {
                payments += transaction.getAmount();
            }

            System.out.println(transaction);
        }

        System.out.println("\nTotal deposits: $" + String.format("%.2f", deposits));
        System.out.println("Total payments: $" + String.format("%.2f", Math.abs(payments)));
        System.out.println("Net total: $" + String.format("%.2f", deposits + payments));
    }
}