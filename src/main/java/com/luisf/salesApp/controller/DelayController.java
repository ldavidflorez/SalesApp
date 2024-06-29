package com.luisf.salesApp.controller;

import com.luisf.salesApp.dto.DelayInsertDto;
import com.luisf.salesApp.model.Delay;
import com.luisf.salesApp.service.DelayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/delays")
public class DelayController {
    @Autowired
    private DelayService delayService;

    @GetMapping
    public Page<Delay> getAll(@RequestParam(defaultValue = "0") int pageNo,
                              @RequestParam(defaultValue = "10") int pageSize) {
        return delayService.getAll(pageNo, pageSize);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Delay>> getById(@PathVariable Long id) {
        Optional<Delay> delayOptional = delayService.getById(id);

        if (delayOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(delayOptional);
    }

    @PostMapping
    public ResponseEntity<Delay> createDelay(@RequestBody DelayInsertDto delayInsertDto) {
        Optional<Delay> delayOptional = delayService.save(delayInsertDto);
        return delayOptional.map(delay -> new ResponseEntity<>(delay, null, HttpStatus.CREATED)).orElseGet(() -> ResponseEntity.internalServerError().build());
    }
}
