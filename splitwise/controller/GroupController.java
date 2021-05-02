package com.LLD.splitwise.controller;

import com.LLD.splitwise.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GroupController {
    private static final String TAG = "GroupController";
    public static void createGroupExpense(int groupId, int expenseId) {
        Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Insert into GroupExpense (?, ?) "
                +"Values (?,?);";
        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, "groupId");
            statement.setString(2, "ExpenseId");
            statement.setInt(3, groupId);
            statement.setInt(4, expenseId);
            ResultSet result = conn.createStatement().executeQuery(query);
            System.out.println(TAG+": inserting groupexpense result: "+result);
        }catch(Exception e){
            System.out.println(TAG+": could not insert group expense");
            e.printStackTrace();
        }
    }
}
