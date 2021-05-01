package com.LLD.splitwise;

import java.sql.Connection;
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

    private static void updatePassword(int userId, String newPassword) {
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

    private static void addGroup(int userId, String title) {
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

    private static void addMembersToGroup(int groupId, int member) {
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
    private static void checkTransactionHistory(int userId) {
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

    private static void checkHistory(int userId) {
        Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Select SUM(owe) from UserExpense AS \"Amount Owed\""
                +"Where userId = "+userId;
        try{
            ResultSet result = conn.createStatement().executeQuery(query);
            while(result.next()) {
                System.out.println("Total amount owe: "+result.getInt("Amount Owed"));
            }

        }catch(Exception e) {
            System.out.println(TAG+": cannot check history");
            e.printStackTrace();
        }
    }

    private static void checkGroup(int userId) {
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

    private static void addExpense(String string) {
        ExpenseController.createExpense(string.split(" "));
    }

    private static int getUser(String u){
        u = u.substring(1);
        System.out.println("user: "+Integer.parseInt(u));
        return Integer.parseInt(u);
    }
}
