package com.LLD.splitwise.paymentmethods;

public class PayMethodFactory {
    public static PayMethod getSinglePayMethod() {
        return new SinglePayMethod();
    }
    public static PayMethod getMultiPayMethod() {
        return new MultiPayMethod();
    }
}
