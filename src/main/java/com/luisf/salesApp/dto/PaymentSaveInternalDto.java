package com.luisf.salesApp.dto;

import com.luisf.salesApp.model.Payment;

public class PaymentSaveInternalDto {
    private Payment payment;

    private String message;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PaymentSaveInternalDto{" +
                "payment=" + payment +
                ", message='" + message + '\'' +
                '}';
    }
}
