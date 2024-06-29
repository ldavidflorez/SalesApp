package com.luisf.salesApp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentDto {
    private Long id;

    private Long orderId;

    private Long customerId;

    private int remainingFees;

    private LocalDateTime nextCollectionDate;

    private BigDecimal payQuantity;

    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public int getRemainingFees() {
        return remainingFees;
    }

    public void setRemainingFees(int remainingFees) {
        this.remainingFees = remainingFees;
    }

    public LocalDateTime getNextCollectionDate() {
        return nextCollectionDate;
    }

    public void setNextCollectionDate(LocalDateTime nextCollectionDate) {
        this.nextCollectionDate = nextCollectionDate;
    }

    public BigDecimal getPayQuantity() {
        return payQuantity;
    }

    public void setPayQuantity(BigDecimal payQuantity) {
        this.payQuantity = payQuantity;
    }

    @Override
    public String toString() {
        return "PaymentDto{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", customerId=" + customerId +
                ", remainingFees=" + remainingFees +
                ", nextCollectionDate=" + nextCollectionDate +
                ", payQuantity=" + payQuantity +
                ", createdAt=" + createdAt +
                '}';
    }
}
