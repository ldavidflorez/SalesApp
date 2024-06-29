package com.luisf.salesApp.controller;

import com.luisf.salesApp.model.Items;
import com.luisf.salesApp.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemsController {
    @Autowired
    private ItemsService itemsService;

    @GetMapping
    Page<Items> getAll(@RequestParam(defaultValue = "0") int pageNo,
                       @RequestParam(defaultValue = "10") int pageSize){
        return itemsService.getAll(pageNo, pageSize);
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
