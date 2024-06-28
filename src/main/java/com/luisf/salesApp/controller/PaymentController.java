package com.luisf.salesApp.controller;

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
    List<Payment> getAll(){
        return paymentService.getAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Optional<Payment>> getById(@PathVariable("id") Long id){
        Optional<Payment> optionalPayment = paymentService.getById(id);

        if (optionalPayment.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalPayment);
    }

    @PostMapping
    public Optional<Payment> createPayment(@RequestBody Payment payment) {
        return paymentService.save(payment);
    }
}
