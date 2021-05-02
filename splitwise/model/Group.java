package com.LLD.splitwise.model;

public class Group {
    private int groupId;
    private String title;
    private User admin;
    public Group(int groupId, String title, User admin) {
        this.groupId = groupId;
        this.title = title;
        this.admin = admin;
    }
    public String getTitle(){
        return this.title;
    }
}
