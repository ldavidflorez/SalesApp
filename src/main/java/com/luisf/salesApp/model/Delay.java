package com.luisf.salesApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "delays")
@JsonIgnoreProperties(value = {"customer", "order", "hibernateLazyInitializer"})
public class Delay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    @Column(name = "promised_date", nullable = false)
    private LocalDateTime promisedDate;

    @Column(name = "days_of_delay", nullable = false)
    private int daysOfDelay;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDateTime getPromisedDate() {
        return promisedDate;
    }

    public void setPromisedDate(LocalDateTime promisedDate) {
        this.promisedDate = promisedDate;
    }

    public int getDaysOfDelay() {
        return daysOfDelay;
    }

    public void setDaysOfDelay(int daysOfDelay) {
        this.daysOfDelay = daysOfDelay;
    }

    @Override
    public String toString() {
        return "Delay{" +
                "id=" + id +
                ", customer=" + customer +
                ", order=" + order +
                ", promisedDate=" + promisedDate +
                ", daysOfDelay=" + daysOfDelay +
                ", createdAt=" + createdAt +
                '}';
    }
}
