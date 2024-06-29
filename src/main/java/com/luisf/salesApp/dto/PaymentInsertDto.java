package com.luisf.salesApp.dto;

import java.math.BigDecimal;

public class PaymentInsertDto {
    private Long customerId;

    private Long orderId;

    private BigDecimal payQuantity;

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

    public BigDecimal getPayQuantity() {
        return payQuantity;
    }

    public void setPayQuantity(BigDecimal payQuantity) {
        this.payQuantity = payQuantity;
    }

    @Override
    public String toString() {
        return "PaymentInsertDto{" +
                "customerId=" + customerId +
                ", orderId=" + orderId +
                ", payQuantity=" + payQuantity +
                '}';
    }
}
