package com.luisf.salesApp.repository;

import com.luisf.salesApp.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentRepositoryCustom {
    Page<Payment> findAllByCustomerId(Long customerId, Pageable pageable);
}
