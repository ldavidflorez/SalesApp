package com.luisf.salesApp.service;

import com.luisf.salesApp.model.Order;
import com.luisf.salesApp.model.Payment;
import com.luisf.salesApp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    List<User> getAll();

    Optional<User> getById(Long id);

    Optional<User> update(Long id, User newUser);

    void delete(Long id);

    List<Order> getAllOrders(Long userId);

    Optional<Order> getOrderById(Long userId, Long orderId);

    List<Payment> getAllPayments(Long userId);

    Optional<Payment> getPaymentById(Long userId, Long paymentId);
}
