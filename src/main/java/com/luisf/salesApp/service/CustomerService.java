package com.luisf.salesApp.service;

import com.luisf.salesApp.dto.CustomerDto;
import com.luisf.salesApp.model.Customer;
import com.luisf.salesApp.model.Order;
import com.luisf.salesApp.model.Payment;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    CustomerDto save(Customer customer);

    List<CustomerDto> getAll(int pageNo, int pageSize);

    Optional<CustomerDto> getById(Long id);

    Optional<CustomerDto> update(Long id, Customer newCustomer);

    boolean delete(Long id);

    Page<Order> getAllOrders(Long customerId, int pageNo, int pageSize);

    Optional<Order> getOrderById(Long customerId, Long orderId);

    Page<Payment> getAllPayments(Long customerId, int pageNo, int pageSize);

    Optional<Payment> getPaymentById(Long customerId, Long paymentId);
}
