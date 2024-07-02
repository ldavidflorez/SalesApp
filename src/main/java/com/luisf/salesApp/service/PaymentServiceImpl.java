package com.luisf.salesApp.service;

import com.luisf.salesApp.dto.PaymentDto;
import com.luisf.salesApp.dto.PaymentInsertDto;
import com.luisf.salesApp.dto.PaymentSaveInternalDto;
import com.luisf.salesApp.model.Payment;
import com.luisf.salesApp.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<PaymentDto> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return paymentRepository.findAll(pageable).stream().map(p -> {
            PaymentDto pDto = new PaymentDto();

            pDto.setId(p.getId());
            pDto.setCustomerId(p.getCustomer().getId());
            pDto.setOrderId(p.getOrder().getId());
            pDto.setPayQuantity(p.getPayQuantity());
            pDto.setRemainingFees(p.getOrder().getRemainingFees());
            pDto.setNextCollectionDate(p.getOrder().getNextCollectionDate());
            pDto.setCreatedAt(p.getCreatedAt());

            return pDto;
        }).toList();
    }

    @Override
    public Optional<PaymentDto> getById(Long id) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);

        if (optionalPayment.isPresent()){
            Payment payment = optionalPayment.get();
            PaymentDto paymentDto = new PaymentDto();

            paymentDto.setId(payment.getId());
            paymentDto.setCustomerId(payment.getCustomer().getId());
            paymentDto.setOrderId(payment.getOrder().getId());
            paymentDto.setPayQuantity(payment.getPayQuantity());
            paymentDto.setRemainingFees(payment.getOrder().getRemainingFees());
            paymentDto.setNextCollectionDate(payment.getOrder().getNextCollectionDate());
            paymentDto.setCreatedAt(payment.getCreatedAt());

            return Optional.of(paymentDto);
        }

        return Optional.empty();
    }

    @Override
    public PaymentSaveInternalDto save(PaymentInsertDto payment) {
        Map<String, Object> result = paymentRepository.insertNewPayment(payment.getOrderId(),
                payment.getCustomerId(), payment.getPayQuantity());

        Long spResult = (Long) result.get("spResult");
        String spMessage = (String) result.get("spMessage");

        PaymentSaveInternalDto paymentSaveInternalDto = new PaymentSaveInternalDto();

        if (spResult < 0) {
            paymentSaveInternalDto.setPayment(null);
            paymentSaveInternalDto.setMessage(spMessage);
            return paymentSaveInternalDto;
        }

        Optional<Payment> optionalNewPayment = paymentRepository.findById(spResult);

        if (optionalNewPayment.isEmpty()) {
            paymentSaveInternalDto.setPayment(null);
            paymentSaveInternalDto.setMessage("Inserted order not found");
            return paymentSaveInternalDto;
        }

        paymentSaveInternalDto.setPayment(optionalNewPayment.get());
        paymentSaveInternalDto.setMessage(spMessage);
        return paymentSaveInternalDto;
    }
}
