package com.luisf.salesApp.service;

import com.luisf.salesApp.dto.PaymentDto;
import com.luisf.salesApp.dto.PaymentInsertDto;
import com.luisf.salesApp.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    List<PaymentDto> getAll();

    Optional<PaymentDto> getById(Long id);

    Optional<Payment> save(PaymentInsertDto payment);
}
