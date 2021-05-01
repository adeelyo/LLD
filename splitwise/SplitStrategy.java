package com.LLD.splitwise;

import java.util.List;

public interface SplitStrategy {
    public void calculateAmount(Expense expense, List<Integer> users, List<Integer> payments, List<Integer> percentages);
}
