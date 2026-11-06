package com.pluralsight;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)  throws IOException {
        TransactionManager manager = new TransactionManager();
        manager.readTransactions();
        List<Transaction> list = manager.getTransactions();
        System.out.println("Number in List: " + list.size());

        manager.displayTransactions();

        list.toString();

    }
}