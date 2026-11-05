package com.pluralsight;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.List;

public class TransactionManager {

    public void readTransactions() throws IOException {
        System.out.println("Reading Transactions... ");
        // Put your file path here
        Path path = Path.of("src/main/java/com/pluralsight/transactions.csv");
        List<String> lines = Files.readAllLines(path);

        for(int i = 1; i < lines.size() ; i++) {
            String Line = lines.get(i);
            String[] parts = Line.split("\\|");

            System.out.println(parts[0]);
            System.out.println(parts[1]);
            System.out.println(parts[2]);
            System.out.println(parts[3]);
            System.out.println("Raw amount: [" + parts[4] + "]");
            Double amount = Double.parseDouble(parts[4]);
            System.out.println(amount);
        }

        System.out.println("Number of lines: " + lines.size());
    }








}
