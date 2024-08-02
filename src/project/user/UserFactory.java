package project.user;

public class UserFactory {
    public static User createUser(String name, double balance, boolean vip) {
        if (vip) {
            return new User.VIPUser(name, balance, vip);
        } else {
            return new User.OrdinaryUser(name, balance, vip);
        }
    }
}