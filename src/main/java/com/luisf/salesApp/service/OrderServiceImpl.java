package com.luisf.salesApp.service;

import com.luisf.salesApp.model.Order;
import com.luisf.salesApp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public int save(Long customerId, String orderType, String orderStatus, String initDate, int completeFees, int remainingFees, int timeToPayInDays, String itemsJson) {
        return 0;
    }

    @Override
    public List<Order> getAll() {
        return List.of();
    }

    @Override
    public Optional<Order> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
