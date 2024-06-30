package com.luisf.salesApp.controller;

import com.luisf.salesApp.dto.OrderInsertDto;
import com.luisf.salesApp.model.Order;
import com.luisf.salesApp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<Order> createOrder(@RequestBody OrderInsertDto order) {
        Optional<Order> newOrderOptional =  orderService.save(order);
        return newOrderOptional.map(value -> new ResponseEntity<>(value, null, HttpStatus.CREATED)).orElseGet(() -> ResponseEntity.internalServerError().build());
    }
}
