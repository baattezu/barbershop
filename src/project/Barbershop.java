package project;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import project.booking.BarbershoppDAOImpl;
import project.user.*;

public class Barbershop {
    private BarbershoppDAOImpl barbershoppDAO = new BarbershoppDAOImpl();
    private ArrayList<Procedure> procedures;
    private ArrayList<User> users;
    private ArrayList<Booking> bookingHistory;


    public Barbershop() {
        procedures = new ArrayList<Procedure>();
        users = new ArrayList<User>();
        bookingHistory = new ArrayList<Booking>();
    }
    //-------------------------------------------SEARCH METHODS-------------------------------------------
    public User getUserByName(String name) {
        for (User user : users) {
            if (name.equals(user.getName())) {
                return user;
            }
        }
        System.out.println("User not found");
        return null; // User with given name not found
    }

    public Procedure getProcedureByName(String name) {
        for (Procedure procedure : procedures) {
            if (procedure.getName().equals(name)) {
                return procedure;
            }
        }
        System.out.println("Procedure not found");
        return null;
    }
    public int getNumOfBookingsByUser(User user){
        int numOfBookings = 0;
        for (Booking booking: bookingHistory){
            if (booking.getUserName().equals(user.getName())) {
                numOfBookings++;
            }
        }
        return numOfBookings;
    }

    public boolean makeBooking(String userName, String procedureName) {
        User user = getUserByName(userName);
        Procedure procedure = getProcedureByName(procedureName);
        double price = procedure.getPrice();
        if (user.calculateSum(procedure) == true) {
            return true;
        }
        return false;
    }


    public boolean cancelBooking(int bookingId) throws Exception {
        Connection connection = new Connection();
        connection.connect();
        String sql = "select * from bookinghistory";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            if (result.getInt("id") == bookingId) {
                String sql2 = "delete from bookinghistory where id = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setInt(1, bookingId);
                preparedStatement.executeUpdate();
                return true;
            }
        }
        return false;
    }

    //-------------------------------------------UPDATE LOCAL-DATA METHODS-------------------------------------------
    public void refreshLocalData() throws Exception {
        barbershoppDAO.updateUsers(users);
        users.clear();
        procedures.clear();
        bookingHistory.clear();
        barbershoppDAO.updateLocalProceduresList(procedures);
        barbershoppDAO.updateLocalUserList(users);
        barbershoppDAO.updateLocalBookingHistory(bookingHistory);
    }

    //-------------------------------------------SHOW METHODS-------------------------------------------
    public void showUsers () throws Exception {
        if (users.size() == 0) {
            System.out.println("No users have been added yet.");
            return;
        }
        for (User user: users){
            System.out.println((user.getId()) + ") "
                    + user.getName() + " - "
                    + user.getBalance() + " - VIP: "
                    + user.getVip());
        }
    }
    public void showBookingHistory() throws Exception{
        if (bookingHistory.size() == 0){
            System.out.println("No bookings have been added yet.");
            return;
        }
        for (Booking booking: bookingHistory){
            System.out.println((booking.getId()) + ") "
                    + booking.getUserName().toUpperCase() + " is booked for "
                    + booking.getProcedureName().toUpperCase() + " on: "
                    + booking.getDatetime() + " (year/month/day \"T\" time)");
        }
    }
    public void showProcedures() throws Exception {
        if (procedures.size() == 0) {
            System.out.println("No procedures have been added yet.");
            return;
        }
        for (int i = 0; i < procedures.size(); i++) {
            Procedure procedure = procedures.get(i);
            System.out.println((i + 1) + ") " + procedure.getName() + " - " + procedure.getPrice() + " - "
                    + procedure.getDescription());
        }
    }
}



