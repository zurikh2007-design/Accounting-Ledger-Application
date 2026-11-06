package com.pluralsight;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

public class TransactionManager {
    private List<Transaction> transactions = new ArrayList<>();

    public void readTransactions() throws IOException {
        System.out.println("Reading Transactions... ");
        // Put your file path here
        Path path = Path.of("src/main/java/com/pluralsight/transactions.csv");
        List<String> lines = Files.readAllLines(path);



        for(int i = 1; i < lines.size() ; i++) {

            String Line = lines.get(i);
            String[] parts = Line.split("\\|");

            LocalDate date = LocalDate.parse(parts[0]);
            System.out.println(date);
            LocalTime time = LocalTime.parse(parts[1]);
            System.out.println(time);

            System.out.println(parts[2]);

            System.out.println(parts[3]);

            System.out.println("Raw amount: [" + parts[4] + "]");

            Double amount = Double.parseDouble(parts[4]);
            System.out.println(amount);

            Transaction transaction = new Transaction(date, time, parts[2], parts[3], amount);

            transactions.add(transaction);


        }

        System.out.println("Number of lines: " + lines.size());
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void displayTransactions() {
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }

    }
}
