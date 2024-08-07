package com.luisf.salesApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@JsonIgnoreProperties(value = {"items", "hibernateLazyInitializer"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ref;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @Column(name = "per_discount", nullable = false)
    private BigDecimal perDiscount;

    @Column(name = "products_in_stock")
    private int productsInStock;

    @Column(nullable = false)
    private boolean available;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Items> items;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getPerDiscount() {
        return perDiscount;
    }

    public void setPerDiscount(BigDecimal perDiscount) {
        this.perDiscount = perDiscount;
    }

    public int getProductsInStock() {
        return productsInStock;
    }

    public void setProductsInStock(int productsInStock) {
        this.productsInStock = productsInStock;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ref='" + ref + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", basePrice=" + basePrice +
                ", perDiscount=" + perDiscount +
                ", productsInStock=" + productsInStock +
                ", available=" + available +
                ", createdAt=" + createdAt +
                ", items=" + items +
                '}';
    }
}
