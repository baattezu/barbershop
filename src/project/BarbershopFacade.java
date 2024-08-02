package project;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import project.booking.BarbershoppDAOImpl;
import project.user.*;
public class BarbershopFacade {
    private Barbershop barbershop;

    public BarbershopFacade() {
        this.barbershop = new Barbershop();
    }

    public User getUserByName(String name) {
        return barbershop.getUserByName(name);
    }

    public Procedure getProcedureByName(String name) {
        return barbershop.getProcedureByName(name);
    }

    public boolean makeBooking(String userName, String procedureName) {
        return barbershop.makeBooking(userName, procedureName);
    }

    public boolean cancelBooking(int bookingId) throws Exception {
        return barbershop.cancelBooking(bookingId);
    }

    public void showUsers() throws Exception {
        barbershop.showUsers();
    }

    public void showProcedures() throws Exception {
        barbershop.showProcedures();
    }

    public void showBookingHistory() throws Exception {
        barbershop.showBookingHistory();
    }

    public void refreshLocalData() throws Exception {
        barbershop.refreshLocalData();
    }
}
