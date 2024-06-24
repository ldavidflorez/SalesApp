package com.luisf.salesApp.service;

import com.luisf.salesApp.model.Order;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    int save(Long customerId, String orderType, String orderStatus, String initDate, int completeFees,
             int remainingFees, int timeToPayInDays, String itemsJson);

    List<Order> getAll();

    Optional<Order> getById(Long id);

    boolean delete(Long id);
}
