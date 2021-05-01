package com.LLD.splitwise;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class ExactSplitStrategy implements SplitStrategy{
    private static final String TAG ="ExactSplitStrategy";
    public void calculateAmount(Expense expense, List<Integer> users, List<Integer> payments, List<Integer> percentages){
        int i=0;
        Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Insert Into UserExpense (userId, expenseId, paid, owe)"
                +"Values ("+users.get(i)+", "+expense.getExpenseId()+", "+payments.get(i)+", "+payments.get(i)+");";
        executeQuery(conn, query);
        for(i=1;i<users.size();i++) {
            query = "Insert Into UserExpense (userId, expenseId, paid, owe)"
                    +"Values ("+users.get(i)+", "+expense.getExpenseId()+", 0, "+payments.get(i)+");";
            executeQuery(conn, query);
        }
    }

    private void executeQuery(Connection connection, String query) {
        try{
            ResultSet result = connection.createStatement().executeQuery(query);
        }catch (Exception e) {
            System.out.println(TAG+": error in adding userExpense");
            e.printStackTrace();
        }
    }
}
