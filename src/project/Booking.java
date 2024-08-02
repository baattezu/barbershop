package project;

import java.time.LocalDateTime;

public class Booking {
    private int id;
    private String userName;
    private String procedureName;
    private LocalDateTime datetime;

    public Booking(int id, String userName, String procedureName, LocalDateTime datetime) {
        this.id = id;
        this.userName = userName;
        this.procedureName = procedureName;
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public LocalDateTime getDatetime() {return datetime;}

}

