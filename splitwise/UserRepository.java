package com.LLD.splitwise;
import java.sql.*;

public class UserRepository {
    private static final String TAG ="UserRepository";
    public static User findUserById(int userId){
        Connection connection = DatabaseConnection.getDatabaseConnectionInstance();
        String query = " Select * from User"+
                " Where userId = "+userId;
        return executeQuery(query, connection);
    }
    public static User findUserByName(String name){
        Connection connection = DatabaseConnection.getDatabaseConnectionInstance();
        String query = " Select * from User"+
                " Where userName = "+name;
        return executeQuery(query, connection);
    }
    public static User findUserByPhoneNumber(String phoneNumber) {
        Connection connection = DatabaseConnection.getDatabaseConnectionInstance();
        String query = " Select * from User"+
                " Where phoneNumber = "+phoneNumber;
        return executeQuery(query, connection);
    }
    private static User executeQuery(String query, Connection connection) {
        try{
            ResultSet result = connection.createStatement().executeQuery(query);
            while(result.next()){
                int userId = result.getInt("userId");
                String name = result.getString("userName");
                String phoneNumber = result.getString("phoneNumber");
                String password = result.getString("password");
                return new User(userId, name, phoneNumber, password);
            }
        } catch(Exception e) {
            System.out.println(TAG+": Error in getting user from database");
            e.printStackTrace();
        }
        return null;
    }
}
