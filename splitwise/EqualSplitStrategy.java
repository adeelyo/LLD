package com.LLD.splitwise;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class EqualSplitStrategy implements SplitStrategy{
    private static final String TAG ="EqualSplitStrategy";
    public void calculateAmount(Expense expense, List<Integer> users, List<Integer> payments, List<Integer> percentages){
        if(expense.getPayMethod().equals("iPay")){
            double equalDoubleAmount=(double)payments.get(0)/users.size();
            int equalIntegerAmount = payments.get(0)/users.size();
            boolean equal = Math.ceil(equalDoubleAmount)==equalIntegerAmount;
            int i=0;
            Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
            String query = "Insert Into UserExpense (userId, expenseId, paid, owe)"
                    +"Values ("+users.get(i)+", "+expense.getExpenseId()+", "+payments.get(i)+", "+equalIntegerAmount+");";
            executeQuery(conn, query);
            for(i=1;i<users.size();i++) {
                if(i==users.size()-1 && !equal){
                    equalIntegerAmount++;
                }
                query = "Insert Into UserExpense (userId, expenseId, paid, owe)"
                        +"Values ("+users.get(i)+", "+expense.getExpenseId()+", 0, "+equalIntegerAmount+");";
                executeQuery(conn, query);
            }
        }else if(expense.getPayMethod().equals("MultiPay")){
            int totalPayment =  payments.stream().mapToInt(Integer::intValue).sum();
            double equalDoubleAmount=(double)totalPayment/users.size();
            int equalIntegerAmount = totalPayment/users.size();
            boolean equal = Math.ceil(equalDoubleAmount)==equalIntegerAmount;
            int i;
            Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
            for(i=0;i<users.size();i++) {
                if(i==users.size()-1 && !equal){
                    equalIntegerAmount++;
                }
                String query = "Insert Into UserExpense (userId, expenseId, paid, owe)"
                        +"Values ("+users.get(i)+", "+expense.getExpenseId()+", "+payments.get(i)+", "+equalIntegerAmount+");";
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
