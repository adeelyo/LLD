package com.LLD.splitwise.repositories;

import com.LLD.splitwise.db.DatabaseConnection;
import com.LLD.splitwise.model.Expense;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExpenseRepository {
    private static final String TAG= "ExpenseRepository";
    public static Expense findExpenseById(int expenseId) {
        Connection connection = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Select * from Expense"
                +" Where ExpenseId = ?";
        return executeQuery(query, connection, String.valueOf(expenseId));
    }

    public static Expense findExpenseByDescription(String description) {
        Connection connection = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Select * from Expense"
                +" Where description = ?";
        return executeQuery(query, connection, description);
    }

    private static Expense executeQuery(String query, Connection connection, String value) {
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, value);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                int expenseId = result.getInt("ExpenseId");
                String description = result.getString("description");
                String paidBy = result.getString("paidBy");
                String splitMethod = result.getString("splitMethod");
                return new Expense(expenseId, description, paidBy, splitMethod);
            }
        } catch(Exception e) {
            System.out.println(TAG+": Error in getting Expense from database");
            e.printStackTrace();
        }
        return null;
    }
}
