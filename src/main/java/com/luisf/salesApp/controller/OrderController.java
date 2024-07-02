package com.luisf.salesApp.controller;

import com.luisf.salesApp.dto.OrderInsertDto;
import com.luisf.salesApp.dto.OrderSaveInternalDto;
import com.luisf.salesApp.model.Order;
import com.luisf.salesApp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public Page<Order> getAll(@RequestParam(defaultValue = "0") int pageNo,
                              @RequestParam(defaultValue = "10") int pageSize) {
        return orderService.getAll(pageNo, pageSize);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Order>> getById(@PathVariable Long id) {
        Optional<Order> order = orderService.getById(id);

        if (order.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderInsertDto order) {
        OrderSaveInternalDto newOrderDto =  orderService.save(order);
        Order newOrder = newOrderDto.getOrder();
        String message = newOrderDto.getMessage();
        if (newOrder == null) {
            return ResponseEntity.internalServerError().body(message);
        }
        return new ResponseEntity<Order>(newOrder, null, HttpStatus.CREATED);
    }
}
