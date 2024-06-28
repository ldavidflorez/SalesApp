package com.luisf.salesApp.repository;

import com.luisf.salesApp.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Procedure(name = "InsertNewPayment")
    Long insertNewPayment(@Param("p_orderId") Long orderId, @Param("p_customerId") Long customerId,
                        @Param("p_payQuantity") BigDecimal payQuantity);
}
