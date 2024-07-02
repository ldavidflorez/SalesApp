package com.luisf.salesApp.service;

import com.luisf.salesApp.dto.DelayInsertDto;
import com.luisf.salesApp.dto.DelaySaveInternalDto;
import com.luisf.salesApp.model.Delay;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface DelayService {
    Page<Delay> getAll(int pageNo, int pageSize);

    Page<Delay> getAllDelaysByCustomer(Long customerId, int pageNo, int pageSize);

    Optional<Delay> getById(Long id);

    DelaySaveInternalDto save(DelayInsertDto delay);
}
