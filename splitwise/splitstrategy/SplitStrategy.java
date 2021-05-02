package com.LLD.splitwise.splitstrategy;

import com.LLD.splitwise.model.Expense;

import java.util.List;

public interface SplitStrategy {
    public void calculateAmount(Expense expense, List<Integer> users, List<Integer> payments, List<Integer> percentages);
}
