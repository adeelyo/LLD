package com.LLD.splitwise.splitstrategy;

import com.LLD.splitwise.model.Expense;
import com.LLD.splitwise.db.DatabaseConnection;
import com.LLD.splitwise.paymentmethods.PayMethodFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class PercentageSplitStrategy implements SplitStrategy{
    private static final String TAG ="RatioSplitStrategy";
    private static final Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
    public void calculateAmount(Expense expense, List<Integer> users, List<Integer> payments, List<Integer> percentages){
        int totalPayment =  payments.stream().mapToInt(Integer::intValue).sum();
        if(expense.getPayMethod().equals("iPay")){
            String query = "Insert Into UserExpense (?, ?, ?, ?)"
                    +"Values (?, ?, ?, ?);";
            List<Integer> payAmounts = PayMethodFactory.getSinglePayMethod().calculatePayAmount(users, payments);
            for(int i=0;i<users.size();i++) {
                executeQuery(query, users.get(i), expense.getExpenseId(), payAmounts.get(i), ((totalPayment*percentages.get(i))/100));
            }
        }else if(expense.getPayMethod().equals("MultiPay")){
            for(int i=0;i<users.size();i++) {
                String query = "Insert Into UserExpense (?, ?, ?, ?)"
                        +"Values (?, ?, ?, ?);";
                List<Integer> payAmounts = PayMethodFactory.getMultiPayMethod().calculatePayAmount(users, payments);
                executeQuery(query, users.get(i), expense.getExpenseId(), payAmounts.get(i), ((totalPayment*percentages.get(i))/100));
            }
        }
    }

    private void executeQuery(String query, int i1, int i2, int i3, int i4) {
        try{
            PreparedStatement statement = conn.prepareStatement(query);
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
