package com.luisf.salesApp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DelayRepositoryImpl implements DelayRepositoryCustom{
    @Autowired
    private EntityManager entityManager;

    @Override
    public Map<String, Object> saveNewDelay(Long customerId, Long orderId, BigDecimal surchargePercent, int wayDays) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SaveNewDelay")
                .registerStoredProcedureParameter("customerId", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("orderId", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("surchargePercent", BigDecimal.class, ParameterMode.IN)
                .registerStoredProcedureParameter("wayDays", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("spResult", Long.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("spMessage", String.class, ParameterMode.OUT);

        query.setParameter("customerId", customerId);
        query.setParameter("orderId", orderId);
        query.setParameter("surchargePercent", surchargePercent);
        query.setParameter("wayDays", wayDays);

        query.execute();

        Long spResult = (Long) query.getOutputParameterValue("spResult");
        String spMessage = (String) query.getOutputParameterValue("spMessage");

        Map<String, Object> result = new HashMap<>();
        result.put("spResult", spResult);
        result.put("spMessage", spMessage);

        return result;
    }
}
