package com.luisf.salesApp.repository;

import com.luisf.salesApp.model.Delay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface DelayRepository extends JpaRepository<Delay, Long> {
    Page<Delay> findAllByCustomerId(Long customerId, Pageable pageable);

    @Procedure(name = "SaveNewDelay")
    Long saveNewDelay(@Param("customerId") Long customerId, @Param("orderId") Long orderId,
                        @Param("surchargePercent") BigDecimal surchargePercent, @Param("wayDays") int wayDays);
}
