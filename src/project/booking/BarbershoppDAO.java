package project.booking;

import project.Connection;
import project.Procedure;
import project.user.*;
import project.Booking;
import java.util.*;
public interface BarbershoppDAO {
    void updateUsers(List<User> users) throws Exception;
    void updateLocalBookingHistory(List<Booking> bookinghistory) throws Exception;
    void updateLocalUserList(List<User> users) throws Exception;
    void updateLocalProceduresList(List<Procedure> procedures) throws Exception;
}
