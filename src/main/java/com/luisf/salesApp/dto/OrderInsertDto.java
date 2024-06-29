package com.luisf.salesApp.dto;

import java.util.List;

public class OrderInsertDto {
    private Long customerId;

    private String type;

    private Integer timeToPayInDays;

    private List<ItemsInsertDto> items;

    public List<ItemsInsertDto> getItems() {
        return items;
    }

    public void setItems(List<ItemsInsertDto> items) {
        this.items = items;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTimeToPayInDays() {
        return timeToPayInDays;
    }

    public void setTimeToPayInDays(Integer timeToPayInDays) {
        this.timeToPayInDays = timeToPayInDays;
    }

    @Override
    public String toString() {
        return "OrderInsertDto{" +
                ", customerId=" + customerId +
                ", type='" + type + '\'' +
                ", timeToPayInDays=" + timeToPayInDays +
                '}';
    }
}
