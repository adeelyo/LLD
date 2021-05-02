package com.LLD.splitwise.paymentmethods;

import java.util.List;

public interface PayMethod {
    public List<Integer> calculatePayAmount(List<Integer> users, List<Integer> payments);
}
