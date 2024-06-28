package com.luisf.salesApp.repository;

import com.luisf.salesApp.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long> {
    @Query("SELECT i FROM Items i WHERE i.customer.id = :customerId")
    List<Items> getItemsById(@Param("customerId") Long customerId);
}
