package com.LLD.splitwise;
import java.sql.*;

public class DatabaseConnection {
    private static final String TAG = "DatabaseConnection";
    public static Connection connection=null;
    private static final String DATABASE_URL = "jdbc:sqlite:C://Users/Laptop/IdeaProjects/LLD/splitwiseApplication";
    private DatabaseConnection() {

    }
    public static Connection getDatabaseConnectionInstance(){
        if(connection==null){
            synchronized (DatabaseConnection.class){
                if(connection==null) {
                    try{
                        connection = DriverManager.getConnection(DATABASE_URL);
                        System.out.println(TAG+": connection to database successful");
                    }catch(Exception e) {
                        System.out.println(TAG+": Error in getting the connection to database");
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }
}
