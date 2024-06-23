package com.luisf.salesApp.service;

import com.luisf.salesApp.dto.CustomerDto;
import com.luisf.salesApp.model.Customer;
import com.luisf.salesApp.model.Order;
import com.luisf.salesApp.model.Payment;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    CustomerDto save(Customer customer);

    List<CustomerDto> getAll();

    Optional<CustomerDto> getById(Long id);

    Optional<CustomerDto> update(Long id, Customer newCustomer);

    boolean delete(Long id);

    List<Order> getAllOrders(Long customerId);

    Optional<Order> getOrderById(Long customerId, Long orderId);

    List<Payment> getAllPayments(Long customerId);

    Optional<Payment> getPaymentById(Long customerId, Long paymentId);
}
