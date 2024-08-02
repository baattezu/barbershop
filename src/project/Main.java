package project;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    private static BarbershopFacade barbershop;
    private static Scanner scanner;
    public static void main(String[] args) throws Exception {
        barbershop = new BarbershopFacade();
        scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            printMenu();
            barbershop.refreshLocalData();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline left-over
            switch (choice) {
                case 1:
                    barbershop.showProcedures();
                    break;
                case 2:
                    addProcedure();
                    break;
                case 3:
                    addUser();
                    break;
                case 4:
                    barbershop.showUsers();
                    break;
                case 5:
                    makeBooking();
                    break;
                case 6:
                    barbershop.showBookingHistory();
                    break;
                case 7:
                    cancelBooking();
                    break;
                case 8:
                    System.out.println("Good bye, and don't get back!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }
    private static void printMenu() {
        System.out.println("");
        System.out.println("Welcome to the Barbeshop booking system!");
        System.out.println("");
        System.out.println("Please choose from the following options:");
        System.out.println("");
        System.out.println("1) Show available procedures list");
        System.out.println("2) Add a procedure");
        System.out.println("3) Add a new user");
        System.out.println("4) Show added users");
        System.out.println("5) Book for a procedure");
        System.out.println("6) Show booking history");
        System.out.println("7) Cancel booking for a procedure");
        System.out.println("8) Exit");
        System.out.println("");
    }


    private static void addProcedure() throws Exception {
        Connection connection = new Connection();
        connection.connect();
        String sql = "insert into procedures (name,description,price) values (?,?,?);";// CREATE
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("Enter the name of the procedure:");
        String name = scanner.nextLine();
        System.out.println("Enter the price of the procedure:");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline left-over
        System.out.println("Enter a brief description of the procedure:");
        String description = scanner.nextLine();
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, description);
        preparedStatement.setDouble(3, price);
        preparedStatement.executeUpdate(); // adding procedure in Database
    }
    private static void addUser() throws Exception {
        Connection connection = new Connection();
        connection.connect();
        String sql = "insert into users (name,balance,vip) values (?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("Enter the name of the user:");
        String name = scanner.nextLine();
        System.out.println("Enter the balance of the user:");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // consume newline left-over
        System.out.println("Enter the userType (Are you VIP ? (true/false) : ):");
        boolean vip = scanner.nextBoolean();
        preparedStatement.setString(1, name);
        preparedStatement.setDouble(2, balance);
        preparedStatement.setBoolean(3, vip);
        preparedStatement.executeUpdate();
        if (vip == true) {
            System.out.println("VIP user " + name + " added successfully with balance " + balance);
        } else {
            System.out.println("Ordinary user " + name + " added successfully with balance" + balance);
        }
    }
    private static void makeBooking() throws Exception {
        System.out.println("Enter the name of the user:");
        String userName = scanner.nextLine();
        System.out.println("Enter the name of the procedure:");
        String procedureName = scanner.nextLine();
        System.out.println("Enter a date and time (format: dd.MM.yyyy - HH:mm):");
        String datetimeStr = scanner.nextLine();
        if (barbershop.makeBooking(userName, procedureName)) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");
                LocalDateTime datetime = LocalDateTime.parse(datetimeStr, formatter);
                try {
                    Connection connection = new Connection();
                    connection.connect();
                    String sql = "insert into bookinghistory (username,procedure,datetime) values (?,?,?);";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, userName);
                    preparedStatement.setString(2, procedureName);
                    preparedStatement.setTimestamp(3, Timestamp.valueOf(datetime));
                    preparedStatement.executeUpdate();
                    System.out.println("Booking succeed");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Booking failed");
                }
            } catch (Exception e) {
                System.out.println("Invalid date and time input: " + datetimeStr);
                System.out.println("Please enter a valid date and time in the format: dd.MM.yyyy HH.mm ");
                System.out.println("Booking failed");
            }
        }
    }

    private static void cancelBooking() throws Exception {
        System.out.println("Enter the id of the booking to cancel:");
        int bookingId = scanner.nextInt();
        if (barbershop.cancelBooking(bookingId)) {
            System.out.println("Booking cancelled successfully.");
        } else {
            System.out.println("Booking could not be found.");
        }
    }
}