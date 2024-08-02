package project.booking;

import java.sql.*;
import java.time.LocalDateTime;

import project.Barbershop;
import project.Booking;
import project.Connection;
import project.Procedure;
import project.user.*;

import java.util.*;

public class BarbershoppDAOImpl implements BarbershoppDAO {

    private Connection connection;

    public BarbershoppDAOImpl() {
        this.connection = connection;
    }

    @Override
    public void updateUsers(List<User> users) throws Exception {
        Connection connection = new Connection();
        connection.connect();
        String sql = "update users set name=?, balance=?, vip=? where id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        for (User user : users) {
            statement.setString(1, user.getName());
            statement.setDouble(2, user.getBalance());
            statement.setBoolean(3, user.getVip());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        }
    }


    @Override
    public void updateLocalBookingHistory(List<Booking> bookinghistory) throws Exception {
        Connection connection = new Connection();
        connection.connect();
        String sql = "select * from bookinghistory;";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
            String username = result.getString("username");
            String procedure = result.getString("procedure");
            LocalDateTime datetime = result.getTimestamp("datetime").toLocalDateTime();
            Booking booking = new Booking(id,username, procedure, datetime);
            bookinghistory.add(booking);
        }
        // do something with the booking history
    }

    @Override
    public void updateLocalUserList(List<User> users) throws Exception {
        Connection connection = new Connection();
        connection.connect();
        String sql = "select * from users order by id asc;";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);

        // find the maximum ID value in the existing users list
        int maxId = 0;
        for (User user : users) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }

        // use the maxId value to initialize the idgen variable
        int idgen = maxId + 1;

        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            double balance = result.getDouble("balance");
            boolean vip = result.getBoolean("vip");
            User user = UserFactory.createUser(name, balance, vip);

            // if the user already exists in the users list, update its properties
            // otherwise, generate a new ID for the user and add it to the list
            Optional<User> existingUser = users.stream().filter(u -> u.getId() == id).findFirst();
            if (existingUser.isPresent()) {
                existingUser.get().setName(name);
                existingUser.get().setBalance(balance);
                existingUser.get().setVip(vip);
            } else {
                user.setId(idgen++);
                users.add(user);
            }
        }
        // do something with the user list
    }

    @Override
    public void updateLocalProceduresList(List<Procedure> procedures) throws Exception {
        Connection connection = new Connection();
        connection.connect();
        String sql = "SELECT * FROM procedures ";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            String name = result.getString("name");
            String description = result.getString("description");
            double price = result.getDouble("price");
            Procedure procedure = new Procedure(name, price, description);
            procedures.add(procedure);
        }
        // do something with the procedures list
    }
}