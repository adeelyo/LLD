package com.LLD.splitwise;

import java.sql.Connection;
import java.sql.ResultSet;

public class GroupController {
    private static final String TAG = "GroupController";
    public static void createGroupExpense(int groupId, int expenseId) {
        Connection conn = DatabaseConnection.getDatabaseConnectionInstance();
        String query = "Insert into GroupExpense (groupId, ExpenseId) "
                +"Values ('"+groupId+"', '"+expenseId+"');";
        try{
            ResultSet result = conn.createStatement().executeQuery(query);
            System.out.println(TAG+": inserting groupexpense result: "+result);
        }catch(Exception e){
            System.out.println(TAG+": could not insert group expense");
            e.printStackTrace();
        }
    }
}
