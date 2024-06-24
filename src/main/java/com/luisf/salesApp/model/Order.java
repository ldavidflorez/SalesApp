package com.luisf.salesApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties(value = {"customer", "payments", "hibernateLazyInitializer"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String type;

    @Column(name = "total_items", nullable = false)
    private Integer totalItems;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private String status;

    @Column(name = "init_date", nullable = false)
    private LocalDateTime initDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "complete_fees", nullable = false)
    private Integer completeFees;

    @Column(name = "remaining_fees", nullable = false)
    private Integer remainingFees;

    @Column(name = "time_to_pay_in_days", nullable = false)
    private Integer timeToPayInDays;

    @Column(name = "fee_value")
    private BigDecimal feeValue;

    @Column(name = "next_collection_date")
    private LocalDateTime nextCollectionDate;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<Items> items;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<Payment> payments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDateTime initDate) {
        this.initDate = initDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getCompleteFees() {
        return completeFees;
    }

    public void setCompleteFees(Integer completeFees) {
        this.completeFees = completeFees;
    }

    public Integer getRemainingFees() {
        return remainingFees;
    }

    public void setRemainingFees(Integer remainingFees) {
        this.remainingFees = remainingFees;
    }

    public Integer getTimeToPayInDays() {
        return timeToPayInDays;
    }

    public void setTimeToPayInDays(Integer timeToPayInDays) {
        this.timeToPayInDays = timeToPayInDays;
    }

    public BigDecimal getFeeValue() {
        return feeValue;
    }

    public void setFeeValue(BigDecimal feeValue) {
        this.feeValue = feeValue;
    }

    public LocalDateTime getNextCollectionDate() {
        return nextCollectionDate;
    }

    public void setNextCollectionDate(LocalDateTime nextCollectionDate) {
        this.nextCollectionDate = nextCollectionDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", createdAt=" + createdAt +
                ", type='" + type + '\'' +
                ", totalItems=" + totalItems +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", initDate=" + initDate +
                ", endDate=" + endDate +
                ", completeFees=" + completeFees +
                ", remainingFees=" + remainingFees +
                ", timeToPayInDays=" + timeToPayInDays +
                ", feeValue=" + feeValue +
                ", nextCollectionDate=" + nextCollectionDate +
                ", items=" + items +
                ", payments=" + payments +
                '}';
    }
}
