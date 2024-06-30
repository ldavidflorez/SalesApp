package com.luisf.salesApp.service;

import com.luisf.salesApp.model.Items;
import com.luisf.salesApp.repository.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemsServiceImpl implements ItemsService{
    @Autowired
    private ItemsRepository itemsRepository;

    @Override
    public Page<Items> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return itemsRepository.findAll(pageable);
    }

    @Override
    public Optional<Items> getById(Long id) {
        return itemsRepository.findById(id);
    }

    @Override
    public Page<Items> getItemsById(Long customerId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return itemsRepository.getItemsById(customerId, pageable);
    }
}
