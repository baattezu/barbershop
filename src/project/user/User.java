package project.user;

import project.Procedure;

import java.util.ArrayList;

public abstract class User implements UserType {
    private static int nextId = 1;
    private int id;
    private String name;
    private double balance;
    private boolean vip;
    public ArrayList<String> takenProcedures;

    public static void resetNextId() {
        nextId = 1;
    }

    public User() {}

    public User(String name, double balance, boolean vip) {
        this.id = nextId++;
        this.name = name;
        this.balance = balance;
        this.vip = vip;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public boolean getVip() {
        return vip;
    }
    public void setVip(boolean vip){ this.vip = vip;}

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public abstract boolean calculateSum(Procedure procedure);

    public static class VIPUser extends User {
        public VIPUser(String name, double balance, boolean vip) {
            super(name, balance, vip);
        }

        @Override
        public boolean calculateSum(Procedure procedure) {
            // VIP users don't pay for procedures
            return true;
        }
    }

    public static class OrdinaryUser extends User {
        public OrdinaryUser(String name, double balance, boolean vip) {
            super(name, balance, vip);
        }

        @Override
        public boolean calculateSum(Procedure procedure) {
            if (getBalance() >= procedure.getPrice()) {
                setBalance(getBalance() - procedure.getPrice());
                return true;
            } else {
                System.out.println("Not enough balance for Ordinary client");
                return false;
            }
        }
    }
}
