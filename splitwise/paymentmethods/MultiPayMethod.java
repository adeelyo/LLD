package com.LLD.splitwise.paymentmethods;

import java.util.List;

public class MultiPayMethod implements PayMethod{
    public List<Integer> calculatePayAmount(List<Integer> users, List<Integer> payments){
        return payments;
    }
}
