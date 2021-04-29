package com.LLD.splitwise;

import java.sql.Connection;
import java.sql.ResultSet;

public class UserController {
    private static final String TAG = "UserController";
    public static void registerUser(String name, String phoneNumber, String password) {
        Connection conn  = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Insert Into User (userName, phoneNumber, password)"
                +"Values ('"+name+"', '"+phoneNumber+"', '"+password+"');";
        System.out.println(TAG+": insert query: "+query);
        try{
            ResultSet result = conn.createStatement().executeQuery(query);
            System.out.println(TAG+": result of insertion: "+result);
        }catch(Exception e) {
            System.out.println(TAG+": cannot insert");
            e.printStackTrace();
        }
    }

    public static void updatePassword(String userId, String newPassword) {
        Connection conn  = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Update User"
                +"Set password = "+newPassword
                +"Where userId = "+userId;
        System.out.println(TAG+": update query: "+query);
        try{
            ResultSet result = conn.createStatement().executeQuery(query);
            System.out.println(TAG+": result of update: "+result);

        }catch(Exception e) {
            System.out.println(TAG+": cannot udpate");
            e.printStackTrace();
        }
    }

    public static void addGroup(int userId, String title) {
        Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Insert into Groups (title, userId)"
                +"Values ('"+title+"', "+userId+");";
        System.out.println(TAG+": addGroup "+query);
        try{
            ResultSet result = conn.createStatement().executeQuery(query);
            System.out.println(TAG+": result of inserting in group: "+result);

        }catch(Exception e) {
            System.out.println(TAG+": cannot insert");
            e.printStackTrace();
        }
    }

    public static void addMembersToGroup(int groupId, int member) {
        Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Insert into GroupMembers (groupId, userId)"
                +"Values ("+groupId+", "+member+");";
        System.out.println(TAG+": addMemberToGroup "+query);
        try{
            ResultSet result = conn.createStatement().executeQuery(query);
            System.out.println(TAG+": result of adding member in group: "+result);

        }catch(Exception e) {
            System.out.println(TAG+": cannot insert");
            e.printStackTrace();
        }
    }
    public static void checkTransactionHistory(int userId) {
        Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Select * from UserExpense "
                +"Where userId = "+userId;
        System.out.println(TAG+": check transaction "+query);
        try{
            ResultSet result = conn.createStatement().executeQuery(query);
            while(result.next()) {
                System.out.println("paid: "+result.getInt("paid")+", owed: "+result.getInt("owe"));
            }

        }catch(Exception e) {
            System.out.println(TAG+": cannot check history");
            e.printStackTrace();
        }
    }

    public static void checkGroup(int userId) {
        Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Select * from GroupMembers "
                +"Where userId = "+userId;
        System.out.println(TAG+": check groups: "+query);
        try{
            ResultSet result = conn.createStatement().executeQuery(query);
            while(result.next()) {
                int groupId = result.getInt("groupId");
                Group g = GroupRepository.findGroupById(groupId);
                System.out.println(g.getTitle());
            }

        }catch(Exception e) {
            System.out.println(TAG+": cannot check groups");
            e.printStackTrace();
        }
    }

    
}
