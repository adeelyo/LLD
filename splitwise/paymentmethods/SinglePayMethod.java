package com.LLD.splitwise.paymentmethods;

import java.util.ArrayList;
import java.util.List;

public class SinglePayMethod implements PayMethod{
    public List<Integer> calculatePayAmount(List<Integer> users, List<Integer> payments) {
        int size = users.size();
        List<Integer> payAmounts = new ArrayList<>(size);
        payAmounts.add(payments.get(0));
        for(int i=1;i<size;i++) {
            payAmounts.add(0);
        }
        return payAmounts;
    }
}
