package com.luisf.salesApp.service;

import com.luisf.salesApp.model.Items;

import java.util.List;
import java.util.Optional;

public interface ItemsService {
    List<Items> getAll();

    Optional<Items> getById(Long id);

    List<Items> getItemsById(Long customerId);
}
