package com.luisf.salesApp.controller;

import com.luisf.salesApp.model.Order;
import com.luisf.salesApp.model.User;
import com.luisf.salesApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getById(@PathVariable Long id) {
        Optional<User> user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<Order>> getAllOrders(@PathVariable Long id) {
        List<Order> orders = userService.getAllOrders(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<Optional<Order>> getOrderById(@PathVariable Long userId, @PathVariable Long orderId) {
        Optional<Order> order = userService.getOrderById(userId, orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<User>> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> updatedUser = userService.update(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
