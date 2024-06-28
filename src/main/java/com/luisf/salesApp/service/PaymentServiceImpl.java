package com.luisf.salesApp.service;

import com.luisf.salesApp.model.Payment;
import com.luisf.salesApp.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> getById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Optional<Payment> save(Payment payment) {
        Long insertedPaymentId = paymentRepository.insertNewPayment(payment.getOrder().getId(),
                payment.getCustomer().getId(), payment.getPayQuantity());
        return paymentRepository.findById(insertedPaymentId);
    }
}
