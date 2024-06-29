package com.luisf.salesApp.controller;

import com.luisf.salesApp.dto.PaymentDto;
import com.luisf.salesApp.dto.PaymentInsertDto;
import com.luisf.salesApp.model.Payment;
import com.luisf.salesApp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping
    List<PaymentDto> getAll(@RequestParam(defaultValue = "0") int pageNo,
                            @RequestParam(defaultValue = "10") int pageSize){
        return paymentService.getAll(pageNo, pageSize);
    }

    @GetMapping("/{id}")
    ResponseEntity<Optional<PaymentDto>> getById(@PathVariable("id") Long id){
        Optional<PaymentDto> optionalPayment = paymentService.getById(id);

        if (optionalPayment.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalPayment);
    }

    @PostMapping
    public Optional<Payment> createPayment(@RequestBody PaymentInsertDto payment) {
        return paymentService.save(payment);
    }
}
