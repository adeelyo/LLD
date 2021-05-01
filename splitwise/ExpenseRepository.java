package com.LLD.splitwise;

import java.sql.Connection;
import java.sql.ResultSet;

public class ExpenseRepository {
    private static final String TAG= "ExpenseRepository";
    public static Expense findExpenseById(int expenseId) {
        Connection connection = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Select * from Expense"
                +" Where ExpenseId = "+expenseId;
        return executeQuery(query, connection);
    }

    public static Expense findExpenseByDescription(String description) {
        Connection connection = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Select * from Expense"
                +" Where description = "+description;
        return executeQuery(query, connection);
    }

    private static Expense executeQuery(String query, Connection connection) {
        try{
            ResultSet result = connection.createStatement().executeQuery(query);
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
