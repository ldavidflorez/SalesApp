package com.luisf.salesApp.repository;

import com.luisf.salesApp.model.Delay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface DelayRepository extends JpaRepository<Delay, Long>, DelayRepositoryCustom {
    Page<Delay> findAllByCustomerId(Long customerId, Pageable pageable);
}
