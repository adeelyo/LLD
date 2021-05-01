package com.LLD.splitwise;

import java.sql.Connection;
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
        int i;
        String payMethod = "";
        for(i=2;i<input.length;i++) {
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

    private static void addUserExpenses(Expense expense, List<Integer> users, List<Integer> payments, List<Integer> percentages){
        SplitStrategy splitStrategy = SplitStrategyFactory.getSplitStrategy(expense.getSplitMethod());
        splitStrategy.calculateAmount(expense, users, payments, percentages);
    }

    private static void addExpense(String description, String payMethod, String splitMethod){
        Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Insert into Expense (description, paidBy, splitMethod)"
                +"Values ('"+description+"', '"+payMethod+"', '"+splitMethod+"');";
        System.out.println(TAG+": adding expense query: "+query);
        try{
            ResultSet result = conn.createStatement().executeQuery(query);
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
