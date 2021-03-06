package com.LLD.splitwise.controller;

import com.LLD.splitwise.repositories.ExpenseRepository;
import com.LLD.splitwise.db.DatabaseConnection;
import com.LLD.splitwise.model.Expense;
import com.LLD.splitwise.splitstrategy.SplitStrategy;
import com.LLD.splitwise.splitstrategy.SplitStrategyFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpenseController {
    private static final String TAG ="ExpenseController";
    private static Set<String> payMethod;
    private static Set<String> splitMethod;
    private static final String DESCRIPTION_START = "Desc";
    public ExpenseController(){
        payMethod = new HashSet<>();
        payMethod.add("iPay");
        payMethod.add("MultiPay");
        splitMethod = new HashSet<>();
        splitMethod.add("Exact");
        splitMethod.add("Equal");
        splitMethod.add("Percentage");
        splitMethod.add("Ratio");
    }
    public static void createExpense(String[] input) {
        List<Integer> users = new ArrayList<>();
        users.add(Integer.parseInt(input[0]));
        boolean isForGroup = isExpenseForGroup(input);
        int i=2;
        if(isForGroup)
            i++;
        String payMethod = "";
        for(;i<input.length;i++) {
            if(!payMethod.contains(input[i])){
                users.add(getUser(input[i]));
            }else{
                payMethod = input[i];
                break;
            }
        }
        i++;
        StringBuilder desc = new StringBuilder();
        String splitMethod = "";
        boolean start = false;
        List<Integer> payments = new ArrayList<>();
        for(;i<input.length;i++) {
            if(!splitMethod.contains(input[i])){
                payments.add(Integer.parseInt(input[i]));
            }else{
                splitMethod = input[i];
                break;
            }
        }
        int splitMethodIndex = i;
        i++;
        for(;i<input.length;i++) {
            if(input[i].equals(DESCRIPTION_START)){
                start = true;
            }
            if(start){
                desc.append(input[i]);
                desc.append(" ");
            }
        }
        desc.delete(desc.length()-1, desc.length());
        addExpense(desc.toString(), payMethod, splitMethod);
        Expense expense =  ExpenseRepository.findExpenseByDescription(desc.toString());
        if(isForGroup){
            //add group expense as well
            GroupController.createGroupExpense(getUser(input[2]), expense.getExpenseId());
        }

        List<Integer> percentages=null;
        if(splitMethod.equals("Percentage")){
            percentages = new ArrayList<>();
            for(i=splitMethodIndex+1;i<input.length;i++){
                if(input[i].equals(DESCRIPTION_START)){
                    break;
                }
                percentages.add(Integer.parseInt(input[i]));
            }
        }
        addUserExpenses(expense, users, payments, percentages);
    }

    private static boolean isExpenseForGroup(String[] input) {
        String s = input[2];
        if(s.charAt(0)=='g'){
            return true;
        }
        return false;
    }

    private static void addUserExpenses(Expense expense, List<Integer> users, List<Integer> payments, List<Integer> percentages){
        SplitStrategy splitStrategy = SplitStrategyFactory.getSplitStrategy(expense.getSplitMethod());
        splitStrategy.calculateAmount(expense, users, payments, percentages);
    }

    private static void addExpense(String description, String payMethod, String splitMethod){
        Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Insert into Expense (?, ?, ?)"
                +"Values (?,?,?);";
        System.out.println(TAG+": adding expense query: "+query);
        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, "description");
            statement.setString(2, "paidBy");
            statement.setString(3, "splitMethod");
            statement.setString(4, description);
            statement.setString(5, payMethod);
            statement.setString(6, splitMethod);
            ResultSet result = statement.executeQuery(query);
            System.out.println(TAG+": result of adding expense: "+result);
        }catch(Exception e) {
            System.out.println(TAG+": cannot insert");
            e.printStackTrace();
        }
    }
    private static int getUser(String u){
        u = u.substring(1);
        System.out.println("user: "+Integer.parseInt(u));
        return Integer.parseInt(u);
    }
}
