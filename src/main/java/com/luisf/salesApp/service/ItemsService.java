package com.luisf.salesApp.service;

import com.luisf.salesApp.model.Items;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ItemsService {
    Page<Items> getAll(int pageNo, int pageSize);

    Optional<Items> getById(Long id);

    List<Items> getItemsById(Long customerId);
}
