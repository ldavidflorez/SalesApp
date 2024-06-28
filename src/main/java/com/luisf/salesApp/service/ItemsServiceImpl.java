package com.luisf.salesApp.service;

import com.luisf.salesApp.model.Items;
import com.luisf.salesApp.repository.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemsServiceImpl implements ItemsService{
    @Autowired
    private ItemsRepository itemsRepository;

    @Override
    public List<Items> getAll() {
        return itemsRepository.findAll();
    }

    @Override
    public Optional<Items> getById(Long id) {
        return itemsRepository.findById(id);
    }

    @Override
    public List<Items> getItemsById(Long customerId) {
        return itemsRepository.getItemsById(customerId);
    }
}
