package com.luisf.salesApp.service;

import com.luisf.salesApp.dto.DelayInsertDto;
import com.luisf.salesApp.model.Customer;
import com.luisf.salesApp.model.Delay;
import com.luisf.salesApp.repository.CustomerRepository;
import com.luisf.salesApp.repository.DelayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class DelayServiceImpl implements DelayService {
    @Autowired
    private DelayRepository delayRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Value("${app.create.delay.surcharge-increment}")
    private BigDecimal surchargeIncrement;

    @Value("${app.create.delay.surcharge-way-days}")
    private int wayDays;

    @Override
    public Page<Delay> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return delayRepository.findAll(pageable);
    }

    @Override
    public Page<Delay> getAllDelaysByCustomer(Long customerId, int pageNo, int pageSize) {
        Customer customer = customerRepository.findById(customerId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return delayRepository.findAllByCustomerId(customerId, pageable);
    }

    @Override
    public Optional<Delay> getById(Long id) {
        return delayRepository.findById(id);
    }

    @Override
    public Optional<Delay> save(DelayInsertDto delay) {
        Long result = delayRepository.saveNewDelay(delay.getCustomerId(), delay.getOrderId(), surchargeIncrement, wayDays);
        if (result == -1) return Optional.empty();
        return  delayRepository.findById(result);
    }
}
