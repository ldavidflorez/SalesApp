package com.luisf.salesApp.service;

import com.luisf.salesApp.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    List<Payment> getAll();

    Optional<Payment> getById(Long id);

    Optional<Payment> save(Payment payment);
}
