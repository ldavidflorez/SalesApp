package com.luisf.salesApp.repository;

import java.math.BigDecimal;
import java.util.Map;

public interface OrderRepositoryCustom {
    Map<String, Object> createNewOrder(Long customerId, String orderType, String orderStatus,
                                       String initDate, int completeFees, int remainingFees,
                                       int timeToPayInDays, String itemsJson, BigDecimal increment);
}
