package com.LLD.splitwise.model;

public class Expense {
    private int expenseId;
    private String description;
    private String paidBy;
    private String splitMethod;
    public Expense(int expenseId, String description, String paidBy, String splitMethod) {
        this.expenseId = expenseId;
        this.description = description;
        this.paidBy = paidBy;
        this.splitMethod = splitMethod;
    }
    public String getPayMethod(){
        return this.paidBy;
    }
    public String getSplitMethod() {
        return this.splitMethod;
    }
    public int getExpenseId(){
        return this.expenseId;
    }
    public String getDescription() {
        return this.description;
    }
}
