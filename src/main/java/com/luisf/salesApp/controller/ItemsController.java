package com.luisf.salesApp.controller;

import com.luisf.salesApp.model.Items;
import com.luisf.salesApp.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemsController {
    @Autowired
    private ItemsService itemsService;

    @GetMapping
    List<Items> getAll(){
        return itemsService.getAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Optional<Items>> getById(@PathVariable("id") Long id){
        Optional<Items> optionalItems = itemsService.getById(id);

        if (optionalItems.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalItems);
    }

    @GetMapping("users/{customerId}")
    List<Items> getItemsById(@PathVariable("customerId") Long customerId){
        return itemsService.getItemsById(customerId);
    }
}
