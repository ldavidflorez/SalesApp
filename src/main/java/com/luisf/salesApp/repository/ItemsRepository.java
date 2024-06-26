package com.luisf.salesApp.repository;

import com.luisf.salesApp.model.Items;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long> {
    @Query("SELECT i FROM Items i WHERE i.customer.id = :customerId")
    Page<Items> getItemsById(@Param("customerId") Long customerId, Pageable pageable);
}
