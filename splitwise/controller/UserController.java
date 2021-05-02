package com.LLD.splitwise.controller;

import com.LLD.splitwise.repositories.GroupRepository;
import com.LLD.splitwise.db.DatabaseConnection;
import com.LLD.splitwise.model.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserController {
    private static final String TAG = "UserController";
    private static final String REGISTER="Register";
    private static final String UPDATE_PROFILE ="UpdateProfile";
    private static final String ADD_GROUP="AddGroup";
    private static final String ADD_MEMBER="AddMember";
    private static final String MY_TOTAL="MyTotal";
    private static final String HISTORY ="History";
    private static final String GROUPS = "Groups";
    private static final String EXPENSE= "Expense";
    private static final Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
    public static void handleInput(String userInput){
        String[] input = userInput.split(" ");
        int userId = getUser(input[0]);
        String operation = input[1];
        if(operation.equals(REGISTER)){
            registerUser(input[2], input[3], input[4]);
        }else if(operation.equals(UPDATE_PROFILE)){
            updatePassword(userId, input[2]);
        }else if(operation.equals(ADD_GROUP)){
            addGroup(userId, input[2]);
        }else if(operation.equals(ADD_MEMBER)){
            addMembersToGroup(getUser(input[2]), getUser(input[3]));
        }else if(operation.equals(MY_TOTAL)){
            checkHistory(userId);
        }else if(operation.equals(HISTORY)){
            checkTransactionHistory(userId);
        }else if(operation.equals(GROUPS)){
            checkGroup(userId);
        }else if(operation.equals(EXPENSE)){
            addExpense(userInput);
        }
    }
    private static void registerUser(String name, String phoneNumber, String password) {
        String query = "Insert into User (?, ?, ?)"
                +"Values (?, ?, ?);";
        System.out.println(TAG+": insert query: "+query);
        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, "userName");
            statement.setString(2, "phoneNumber");
            statement.setString(3, "password");
            statement.setString(4, name);
            statement.setString(5, phoneNumber);
            statement.setString(6, password);
            ResultSet result = statement.executeQuery(query);
            System.out.println(TAG+": result of insertion: "+result);
        }catch(Exception e) {
            System.out.println(TAG+": cannot insert");
            e.printStackTrace();
        }
    }

    private static void updatePassword(int userId, String newPassword) {
        String query = "Update User"
                +"Set password = ?"
                +"Where userId = ?;";
        System.out.println(TAG+": update query: "+query);
        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setInt(2, userId);
            ResultSet result = statement.executeQuery(query);
            System.out.println(TAG+": result of update: "+result);

        }catch(Exception e) {
            System.out.println(TAG+": cannot udpate");
            e.printStackTrace();
        }
    }

    private static void addGroup(int userId, String title) {
        String query = "Insert into Groups (?, ?)"
                +"Values (?, ?);";
        System.out.println(TAG+": addGroup "+query);
        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, "title");
            statement.setString(2, "userId");
            statement.setString(3, title);
            statement.setInt(4, userId);
            ResultSet result = statement.executeQuery(query);
            System.out.println(TAG+": result of inserting in group: "+result);

        }catch(Exception e) {
            System.out.println(TAG+": cannot insert");
            e.printStackTrace();
        }
    }

    private static void addMembersToGroup(int groupId, int member) {
        String query = "Insert INTO GroupMembers (?, ?)"
                +"Values (?, ?);";
        System.out.println(TAG+": addMemberToGroup "+query);
        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, "groupId");
            statement.setString(2, "userId");
            statement.setInt(3, groupId);
            statement.setInt(4, member);
            ResultSet result = statement.executeQuery(query);
            System.out.println(TAG+": result of adding member in group: "+result);

        }catch(Exception e) {
            System.out.println(TAG+": cannot insert");
            e.printStackTrace();
        }
    }
    private static void checkTransactionHistory(int userId) {
        String query = "Select * from UserExpense "
                +"Where userId = ?;";
        System.out.println(TAG+": check transaction "+query);
        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                System.out.println("paid: "+result.getInt("paid")+", owed: "+result.getInt("owe"));
            }

        }catch(Exception e) {
            System.out.println(TAG+": cannot check history");
            e.printStackTrace();
        }
    }

    private static void checkHistory(int userId) {
        String query = "Select SUM(?) from UserExpense AS \"Amount Owed\""
                +"Where userId = ?;";
        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, "owe");
            statement.setInt(2, userId);
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                System.out.println("Total amount owe: "+result.getInt("Amount Owed"));
            }

        }catch(Exception e) {
            System.out.println(TAG+": cannot check history");
            e.printStackTrace();
        }
    }

    private static void checkGroup(int userId) {
        String query = "Select * from GroupMembers "
                +"Where userId = ?;";
        System.out.println(TAG+": check groups: "+query);
        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery(query);
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

    private static void addExpense(String string) {
        ExpenseController.createExpense(string.split(" "));
    }

    private static int getUser(String u){
        u = u.substring(1);
        System.out.println("user: "+Integer.parseInt(u));
        return Integer.parseInt(u);
    }
}
