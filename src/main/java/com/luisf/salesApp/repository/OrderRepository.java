package com.luisf.salesApp.repository;

import com.luisf.salesApp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Procedure(name = "CreateNewOrder")
    Long createNewOrder(@Param("customerId") Long customerId, @Param("orderType") String orderType,
                        @Param("orderStatus") String orderStatus, @Param("initDate") String initDate,
                        @Param("completeFees") int completeFees, @Param("remainingFees") int remainingFees,
                        @Param("timeToPayInDays") int timeToPayInDays, @Param("itemsJson") String itemsJson,
                        @Param("increment") BigDecimal increment);
}
