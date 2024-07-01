package com.luisf.salesApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luisf.salesApp.dto.OrderInsertDto;
import com.luisf.salesApp.dto.OrderSaveDto;
import com.luisf.salesApp.model.Order;
import com.luisf.salesApp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Value("${app.create.order.fee15.increment-percent}")
    private BigDecimal incrementFee15;

    @Value("${app.create.order.fee15.increment.additional}")
    private BigDecimal additionalIncrementFee15;

    @Value("${app.create.order.fee30.increment-percent}")
    private BigDecimal incrementFee30;

    @Value("${app.create.order.fee30.increment.additional}")
    private BigDecimal additionalIncrementFee30;

    @Override
    public OrderSaveDto save(OrderInsertDto orderInsertDto) {
        String orderStatus = "Pending";

        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String initDate = currentDate.format(formatter);

        int completeFees = 0;
        int remainingFees = 0;
        BigDecimal increment = BigDecimal.valueOf(1.00);
        BigDecimal additionalIncrement;

        if (orderInsertDto.getType().equals("FEE_15")) {
            remainingFees = orderInsertDto.getTimeToPayInDays() / 15;
            additionalIncrement = additionalIncrementFee15.multiply(BigDecimal.valueOf(remainingFees));
            increment = incrementFee15.add(additionalIncrement);
        } else if (orderInsertDto.getType().equals("FEE_30")) {
            remainingFees = orderInsertDto.getTimeToPayInDays() / 30;
            additionalIncrement = additionalIncrementFee30.multiply(BigDecimal.valueOf(remainingFees));
            increment = incrementFee30.add(additionalIncrement);
        } else {
            orderStatus = "Completed";
            completeFees = 1;
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            String itemsJson = mapper.writeValueAsString(orderInsertDto.getItems());

            Map<String, Object> result = orderRepository.createNewOrder(orderInsertDto.getCustomerId(),
                    orderInsertDto.getType(), orderStatus, initDate, completeFees, remainingFees,
                    orderInsertDto.getTimeToPayInDays(),
                    itemsJson, increment);

            Long spResult = (Long) result.get("spResult");
            String spMessage = (String) result.get("spMessage");

            OrderSaveDto orderSaveDto = new OrderSaveDto();

            if (spResult < 0) {
                orderSaveDto.setOrder(null);
                orderSaveDto.setMessage(spMessage);
                return orderSaveDto;
            }

            Optional<Order> optionalNewOrder = orderRepository.findById(spResult);

            if (optionalNewOrder.isEmpty()) {
                orderSaveDto.setOrder(null);
                orderSaveDto.setMessage("Inserted order not found");
                return  orderSaveDto;
            }

            orderSaveDto.setOrder(optionalNewOrder.get());
            orderSaveDto.setMessage(spMessage);
            return  orderSaveDto;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Order> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return orderRepository.findAll(pageable);
    }

    @Override
    public Optional<Order> getById(Long id) {
        return orderRepository.findById(id);
    }
}
