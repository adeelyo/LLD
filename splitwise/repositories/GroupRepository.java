package com.LLD.splitwise.repositories;

import com.LLD.splitwise.db.DatabaseConnection;
import com.LLD.splitwise.model.Group;
import com.LLD.splitwise.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GroupRepository {
    private static final String TAG = "GroupRepository";
    public static Group findGroupById(int id) {
        Connection connection = DatabaseConnection.getDatabaseConnectionInstance();
        String query = " Select * from Groups"+
                " Where groupId = ?";
        return executeQuery(query, connection, null, String.valueOf(id));
    }
    public static Group findGroupByAdmin(User admin) {
        Connection connection = DatabaseConnection.getDatabaseConnectionInstance();
        String query = " Select * from Groups"+
                " Where userId = ?";
        return executeQuery(query, connection, admin, String.valueOf(admin.getUserId()));
    }
    public static Group findGroupByTitle(String title) {
        Connection connection = DatabaseConnection.getDatabaseConnectionInstance();
        String query = " Select * from Groups"+
                " Where title = ?";
        return executeQuery(query, connection, null, title);
    }
    private static Group executeQuery(String query, Connection connection, User admin, String value) {
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, value);
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                int groupId = result.getInt("groupId");
                String title = result.getString("title");
                if(admin==null){
                    return new Group(groupId, title, UserRepository.findUserById(result.getInt("userId")));
                }
                return new Group(groupId, title, admin);
            }
        } catch(Exception e) {
            System.out.println(TAG+": Error in getting Expense from database");
            e.printStackTrace();
        }
        return null;
    }
}
