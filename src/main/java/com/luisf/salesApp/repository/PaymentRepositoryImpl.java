package com.luisf.salesApp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PaymentRepositoryImpl implements PaymentRepositoryCustom{
    @Autowired
    private EntityManager entityManager;

    @Override
    public Map<String, Object> insertNewPayment(Long orderId, Long customerId, BigDecimal payQuantity) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("InsertNewPayment")
                .registerStoredProcedureParameter("p_orderId", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_customerId", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_payQuantity", BigDecimal.class, ParameterMode.IN)
                .registerStoredProcedureParameter("spResult", Long.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("spMessage", String.class, ParameterMode.OUT);

        query.setParameter("p_orderId", orderId);
        query.setParameter("p_customerId", customerId);
        query.setParameter("p_payQuantity", payQuantity);

        query.execute();

        Long spResult = (Long) query.getOutputParameterValue("spResult");
        String spMessage = (String) query.getOutputParameterValue("spMessage");

        Map<String, Object> result = new HashMap<>();
        result.put("spResult", spResult);
        result.put("spMessage", spMessage);

        return result;
    }
}
