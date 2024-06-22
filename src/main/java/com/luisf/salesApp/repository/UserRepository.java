package com.luisf.salesApp.repository;

import com.luisf.salesApp.model.Order;
import com.luisf.salesApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT o FROM Order o WHERE o.id = :orderId AND o.user.id = :userId")
    Optional<Order> getOrderById(@Param("userId") Long userId, @Param("orderId") Long orderId);
}
