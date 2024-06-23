package com.luisf.salesApp.repository;

import com.luisf.salesApp.model.Order;
import com.luisf.salesApp.model.Payment;
import com.luisf.salesApp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT o FROM Order o WHERE o.id = :orderId AND o.customer.id = :customerId")
    Optional<Order> getOrderById(@Param("customerId") Long customerId, @Param("orderId") Long orderId);

    @Query("SELECT p FROM Payment p WHERE p.id = :paymentId AND p.customer.id = :customerId")
    Optional<Payment> getPaymentById(@Param("customerId") Long customerId, @Param("paymentId") Long paymentId);
}
