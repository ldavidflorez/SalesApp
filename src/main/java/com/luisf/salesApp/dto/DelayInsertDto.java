package com.luisf.salesApp.dto;

public class DelayInsertDto {
    private Long customerId;

    private Long orderId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "DelayInsertDto{" +
                "customerId=" + customerId +
                ", orderId=" + orderId +
                '}';
    }
}
