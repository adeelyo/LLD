package com.LLD.splitwise;

import java.sql.Connection;
import java.sql.ResultSet;

public class GroupRepository {
    private static final String TAG = "GroupRepository";
    public static Group findGroupById(int id) {
        Connection connection = DatabaseConnection.getDatabaseConnectionInstance();
        String query = " Select * from Groups"+
                " Where groupId = "+id;
        return executeQuery(query, connection, null);
    }
    public static Group findGroupByAdmin(User admin) {
        Connection connection = DatabaseConnection.getDatabaseConnectionInstance();
        String query = " Select * from Groups"+
                " Where userId = "+admin.getUserId();
        return executeQuery(query, connection, admin);
    }
    public static Group findGroupByTitle(String title) {
        Connection connection = DatabaseConnection.getDatabaseConnectionInstance();
        String query = " Select * from Groups"+
                " Where title = "+title;
        return executeQuery(query, connection, null);
    }
    private static Group executeQuery(String query, Connection connection, User admin) {
        try{
            ResultSet result = connection.createStatement().executeQuery(query);
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
