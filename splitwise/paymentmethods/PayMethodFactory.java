package com.LLD.splitwise.paymentmethods;

public class PayMethodFactory {
    public static PayMethod getPayMethod(String payMethod) {
        if(payMethod.equals("iPay")){
            return new SinglePayMethod();
        }else{
            return new MultiPayMethod();
        }
    }
}
