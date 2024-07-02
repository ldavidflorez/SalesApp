package com.luisf.salesApp.dto;

import com.luisf.salesApp.model.Order;

public class OrderSaveInternalDto {
    private Order order;

    private String message;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "OrderCreationResponse{" +
                "order=" + order +
                ", message='" + message + '\'' +
                '}';
    }
}