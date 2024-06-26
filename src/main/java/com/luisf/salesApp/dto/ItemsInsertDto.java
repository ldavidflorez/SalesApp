package com.luisf.salesApp.dto;

public class ItemsInsertDto {
    private Long productId;

    private Integer unitNumber;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    @Override
    public String toString() {
        return "ItemsInsertDto{" +
                ", productId=" + productId +
                ", unitNumber=" + unitNumber +
                '}';
    }
}
