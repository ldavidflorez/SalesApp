package com.luisf.salesApp.repository;

import java.math.BigDecimal;
import java.util.Map;

public interface DelayRepositoryCustom {
    Map<String, Object> saveNewDelay(Long customerId, Long orderId, BigDecimal surchargePercent, int wayDays);
}
