package com.proj.Controller;

import com.proj.Bean.Payment;
import com.proj.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.createPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok().body(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") int id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        return payment.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentById(@PathVariable("id") int id) {
        paymentService.getPaymentById(id);
        return ResponseEntity.noContent().build();
    }
}
