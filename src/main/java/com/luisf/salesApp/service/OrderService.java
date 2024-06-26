package com.luisf.salesApp.service;

import com.luisf.salesApp.dto.OrderInsertDto;
import com.luisf.salesApp.model.Order;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> save(OrderInsertDto orderInsertDto);

    List<Order> getAll();

    Optional<Order> getById(Long id);
}
