package com.LLD.splitwise;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class PercentageSplitStrategy implements SplitStrategy{
    private static final String TAG ="RatioSplitStrategy";
    public void calculateAmount(Expense expense, List<Integer> users, List<Integer> payments, List<Integer> percentages){
        int totalPayment =  payments.stream().mapToInt(Integer::intValue).sum();
        if(expense.getPayMethod().equals("iPay")){
            int i=0;
            Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
            String query = "Insert Into UserExpense (userId, expenseId, paid, owe)"
                    +"Values ("+users.get(i)+", "+expense.getExpenseId()+", "+payments.get(i)+", "+((totalPayment*percentages.get(i))/100)+");";
            executeQuery(conn, query);
            for(i=1;i<users.size();i++) {
                query = "Insert Into UserExpense (userId, expenseId, paid, owe)"
                        +"Values ("+users.get(i)+", "+expense.getExpenseId()+", 0, "+((totalPayment*percentages.get(i))/100)+");";
                executeQuery(conn, query);
            }
        }else if(expense.getPayMethod().equals("MultiPay")){
            int i;
            Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
            for(i=0;i<users.size();i++) {
                String query = "Insert Into UserExpense (userId, expenseId, paid, owe)"
                        +"Values ("+users.get(i)+", "+expense.getExpenseId()+", "+payments.get(i)+", "+((totalPayment*percentages.get(i))/100)+");";
                executeQuery(conn, query);
            }
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
