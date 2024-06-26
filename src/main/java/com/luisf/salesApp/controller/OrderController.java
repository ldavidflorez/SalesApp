package com.luisf.salesApp.controller;

import com.luisf.salesApp.dto.OrderInsertDto;
import com.luisf.salesApp.model.Order;
import com.luisf.salesApp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Order> getAll() {
        return orderService.getAll();
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
    public ResponseEntity<Order> createProduct(@RequestBody OrderInsertDto order) {
        Optional<Order> newOrderOptional =  orderService.save(order);
        if (newOrderOptional.isEmpty()) return ResponseEntity.internalServerError().build();
        return new ResponseEntity<Order>(newOrderOptional.get(),null, HttpStatus.CREATED);
    }
}
