package com.luisf.salesApp.repository;

import com.luisf.salesApp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    Page<Order> findAllByCustomerId(Long customerId, Pageable pageable);
}
