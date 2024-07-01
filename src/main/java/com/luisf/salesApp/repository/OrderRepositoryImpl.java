package com.luisf.salesApp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Map<String, Object> createNewOrder(Long customerId, String orderType, String orderStatus,
                                              String initDate, int completeFees, int remainingFees,
                                              int timeToPayInDays, String itemsJson, BigDecimal increment) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("CreateNewOrder")
                .registerStoredProcedureParameter("customerId", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("orderType", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("orderStatus", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("initDate", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("completeFees", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("remainingFees", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("timeToPayInDays", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("itemsJson", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("increment", BigDecimal.class, ParameterMode.IN)
                .registerStoredProcedureParameter("spResult", Long.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("spMessage", String.class, ParameterMode.OUT);

        query.setParameter("customerId", customerId);
        query.setParameter("orderType", orderType);
        query.setParameter("orderStatus", orderStatus);
        query.setParameter("initDate", initDate);
        query.setParameter("completeFees", completeFees);
        query.setParameter("remainingFees", remainingFees);
        query.setParameter("timeToPayInDays", timeToPayInDays);
        query.setParameter("itemsJson", itemsJson);
        query.setParameter("increment", increment);

        query.execute();

        Long spResult = (Long) query.getOutputParameterValue("spResult");
        String spMessage = (String) query.getOutputParameterValue("spMessage");

        Map<String, Object> result = new HashMap<>();
        result.put("spResult", spResult);
        result.put("spMessage", spMessage);

        return result;
    }
}
