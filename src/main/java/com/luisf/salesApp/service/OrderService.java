package com.luisf.salesApp.service;

import com.luisf.salesApp.dto.OrderInsertDto;
import com.luisf.salesApp.dto.OrderSaveDto;
import com.luisf.salesApp.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderSaveDto save(OrderInsertDto orderInsertDto);

    Page<Order> getAll(int pageNo, int pageSize);

    Optional<Order> getById(Long id);
}
