package com.luisf.salesApp.repository;

import java.math.BigDecimal;
import java.util.Map;

public interface PaymentRepositoryCustom {
    Map<String, Object> insertNewPayment(Long orderId, Long customerId, BigDecimal payQuantity);
}
