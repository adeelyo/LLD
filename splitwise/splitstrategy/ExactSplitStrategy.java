package com.LLD.splitwise.splitstrategy;

import com.LLD.splitwise.model.Expense;
import com.LLD.splitwise.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ExactSplitStrategy implements SplitStrategy{
    private static final String TAG ="ExactSplitStrategy";
    public void calculateAmount(Expense expense, List<Integer> users, List<Integer> payments, List<Integer> percentages){
        int i=0;
        Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Insert Into UserExpense (?, ?, ?, ?)"
                +"Values (?, ?, ?, ?);";
        executeQuery(conn, query, users.get(i), expense.getExpenseId(), payments.get(i), payments.get(i));
        for(i=1;i<users.size();i++) {
            executeQuery(conn, query, users.get(i), expense.getExpenseId(), 0, payments.get(i));
        }
    }

    private void executeQuery(Connection connection, String query, int i1, int i2, int i3, int i4) {
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "userId");
            statement.setString(2, "expenseId");
            statement.setString(3, "paid");
            statement.setString(4, "owe");
            statement.setInt(5, i1);
            statement.setInt(6, i2);
            statement.setInt(7, i3);
            statement.setInt(8, i4);
            ResultSet result = statement.executeQuery(query);
        }catch (Exception e) {
            System.out.println(TAG+": error in adding userExpense");
            e.printStackTrace();
        }
    }
}
