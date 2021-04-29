package com.LLD.splitwise;

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
}
