package com.luisf.salesApp.service;

import com.luisf.salesApp.dto.PaymentDto;
import com.luisf.salesApp.dto.PaymentInsertDto;
import com.luisf.salesApp.dto.PaymentSaveInternalDto;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    List<PaymentDto> getAll(int pageNo, int pageSize);

    Optional<PaymentDto> getById(Long id);

    PaymentSaveInternalDto save(PaymentInsertDto payment);
}
