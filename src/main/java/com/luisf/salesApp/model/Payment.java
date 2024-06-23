package com.luisf.salesApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column(name = "pay_quantity", nullable = false)
    private BigDecimal payQuantity;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPayQuantity() {
        return payQuantity;
    }

    public void setPayQuantity(BigDecimal payQuantity) {
        this.payQuantity = payQuantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @JsonProperty("order")
    public Long getOrderId() {
        return order != null ? order.getId() : null;
    }

    @JsonProperty("customer")
    public Long getCustomerId() {
        return customer != null ? customer.getId() : null;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", order=" + getOrderId() +
                ", customer=" + getCustomerId() +
                ", payQuantity=" + payQuantity +
                ", createdAt=" + createdAt +
                '}';
    }
}
