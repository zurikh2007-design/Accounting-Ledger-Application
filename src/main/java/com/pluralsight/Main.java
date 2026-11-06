package com.pluralsight;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)  throws IOException {
        Scanner scanner = new Scanner(System.in);
        String opt = "";


        while (!opt.equals("x")) {
            System.out.println("Welcome to the Financial Tracker!");
            System.out.println("Options: ");
            System.out.println(" D) Withdraw");
            System.out.println(" P) Deposit");
            System.out.println(" L) Check balance");
            System.out.println(" X) Exit");

            System.out.print("> ");
            opt = scanner.nextLine();

            switch (opt) {
                case "d":
                    System.out.println("Deposit selected");
                    break;

                case "p":
                    System.out.println("Payment selected");
                    break;

                case "l":
                    System.out.println("Ledger selected");
                    break;

                case "x":
                    System.out.println("Exit selected");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }




        //        TransactionManager manager = new TransactionManager();
        //        manager.readTransactions();
        //        List<Transaction> list = manager.getTransactions();
        //        System.out.println("Number in List: " + list.size());
        //
        //        manager.displayTransactions();



    }
}