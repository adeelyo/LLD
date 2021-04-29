package com.LLD.splitwise;

public class User {
    private int userId;
    private String userName;
    private String phoneNumber;
    private String password;
    public User(int userId, String userName, String phoneNumber, String password) {
        this.userId = userId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
    public int getUserId(){
        return this.userId;
    }
}
