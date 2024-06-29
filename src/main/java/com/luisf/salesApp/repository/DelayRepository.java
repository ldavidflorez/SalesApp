package com.luisf.salesApp.repository;

import com.luisf.salesApp.model.Delay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelayRepository extends JpaRepository<Delay, Long> {
}
