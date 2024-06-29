package com.luisf.salesApp.service;

import com.luisf.salesApp.dto.CustomerDto;
import com.luisf.salesApp.model.Customer;
import com.luisf.salesApp.model.Order;
import com.luisf.salesApp.model.Payment;
import com.luisf.salesApp.repository.CustomerRepository;
import com.luisf.salesApp.repository.OrderRepository;
import com.luisf.salesApp.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public CustomerDto save(Customer customer) {
        customerRepository.save(customer);

        CustomerDto customerDto = new CustomerDto();

        customerDto.setName(customer.getName());
        customerDto.setLastname(customer.getLastname());
        customerDto.setEmail(customer.getEmail());
        customerDto.setCreatedAt(customer.getCreatedAt());

        return customerDto;
    }

    @Override
    public List<CustomerDto> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<CustomerDto> customerDtos = new ArrayList<>();

        Page<Customer> customers = customerRepository.findAll(pageable);

        for(Customer customer : customers){
            CustomerDto customerDto = new CustomerDto();

            customerDto.setId(customer.getId());
            customerDto.setRole(customer.getRole());
            customerDto.setName(customer.getName());
            customerDto.setLastname(customer.getLastname());
            customerDto.setEmail(customer.getEmail());
            customerDto.setCreatedAt(customer.getCreatedAt());

            customerDtos.add(customerDto);
        }

        return customerDtos;
    }

    @Override
    public Optional<CustomerDto> getById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent())
        {
            Customer customer = optionalCustomer.get();
            CustomerDto customerDto = new CustomerDto();

            customerDto.setId(customer.getId());
            customerDto.setRole(customer.getRole());
            customerDto.setName(customer.getName());
            customerDto.setLastname(customer.getLastname());
            customerDto.setEmail(customer.getEmail());
            customerDto.setCreatedAt(customer.getCreatedAt());

            return Optional.of(customerDto);
        }

        return Optional.empty();
    }

    @Override
    public Optional<CustomerDto> update(Long id, Customer newCustomer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();

            customer.setName(newCustomer.getName());
            customer.setLastname(newCustomer.getLastname());
            customer.setPassword(newCustomer.getPassword());
            customer.setRole(newCustomer.getRole());
            customer.setEmail(newCustomer.getEmail());

            customerRepository.save(customer);

            CustomerDto customerDto = new CustomerDto();

            customerDto.setId(customer.getId());
            customerDto.setRole(customer.getRole());
            customerDto.setName(customer.getName());
            customerDto.setLastname(customer.getLastname());
            customerDto.setEmail(customer.getEmail());
            customerDto.setCreatedAt(customer.getCreatedAt());

            return Optional.of(customerDto);
        }

        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        if (customerRepository.existsById(id)){
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Page<Order> getAllOrders(Long customerId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        return orderRepository.findAllByCustomerId(customerId, pageable);
    }

    @Override
    public Optional<Order> getOrderById(Long customerId, Long orderId) {
        return Optional.ofNullable(customerRepository.getOrderById(customerId, orderId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found")));
    }

    @Override
    public Page<Payment> getAllPayments(Long customerId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Customer customer = customerRepository.findById(customerId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
        return paymentRepository.findAllByCustomerId(customerId, pageable);
    }

    @Override
    public Optional<Payment> getPaymentById(Long customerId, Long paymentId) {
        return Optional.ofNullable(customerRepository.getPaymentById(customerId, paymentId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found")));
    }
}
