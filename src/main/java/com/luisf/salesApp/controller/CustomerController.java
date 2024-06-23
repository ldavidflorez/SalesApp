package com.luisf.salesApp.controller;

import com.luisf.salesApp.dto.CustomerDto;
import com.luisf.salesApp.model.Customer;
import com.luisf.salesApp.model.Order;
import com.luisf.salesApp.model.Payment;
import com.luisf.salesApp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<CustomerDto> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CustomerDto>> getById(@PathVariable Long id) {
        Optional<CustomerDto> customer = customerService.getById(id);

        if (customer.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<Order>> getAllOrders(@PathVariable Long id) {
        List<Order> orders = customerService.getAllOrders(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{customerId}/orders/{orderId}")
    public ResponseEntity<Optional<Order>> getOrderById(@PathVariable Long customerId, @PathVariable Long orderId) {
        Optional<Order> order = customerService.getOrderById(customerId, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}/payments")
    public ResponseEntity<List<Payment>> getAllPayments(@PathVariable Long id) {
        List<Payment> payments = customerService.getAllPayments(id);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{customerId}/payments/{paymentId}")
    public ResponseEntity<Optional<Payment>> getPaymentById(@PathVariable Long customerId, @PathVariable Long paymentId) {
        Optional<Payment> payment = customerService.getPaymentById(customerId, paymentId);
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    public CustomerDto createCustomer(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<CustomerDto>> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        Optional<CustomerDto> updatedCustomer = customerService.update(id, customerDetails);

        if (updatedCustomer.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        boolean isDeleted = customerService.delete(id);

        if (!isDeleted){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
