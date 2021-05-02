package com.LLD.splitwise.repositories;
import com.LLD.splitwise.db.DatabaseConnection;
import com.LLD.splitwise.model.User;

import java.sql.*;

public class UserRepository {
    private static final String TAG ="UserRepository";
    public static User findUserById(int userId){
        Connection connection = DatabaseConnection.getDatabaseConnectionInstance();
        String query = " Select * from User"+
                " Where userId = ?";
        return executeQuery(query, connection, String.valueOf(userId));
    }
    public static User findUserByName(String name){
        Connection connection = DatabaseConnection.getDatabaseConnectionInstance();
        String query = " Select * from User"+
                " Where userName = ?";
        return executeQuery(query, connection, name);
    }
    public static User findUserByPhoneNumber(String phoneNumber) {
        Connection connection = DatabaseConnection.getDatabaseConnectionInstance();
        String query = " Select * from User"+
                " Where phoneNumber = ?";
        return executeQuery(query, connection, phoneNumber);
    }
    private static User executeQuery(String query, Connection connection, String value) {
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,value);
            ResultSet result = statement.executeQuery(query);
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
