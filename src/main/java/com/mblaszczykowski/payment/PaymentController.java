package com.mblaszczykowski.payment;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/payments")
@Validated
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> getPayments() {
        return paymentService.getAll();
    }

    @GetMapping("{id}")
    public Payment getPayment(@PathVariable("id") Integer id) {
        return paymentService.getById(id);
    }

    @PostMapping
    public void addPayment(@RequestBody @Valid PaymentRegistrationRequest request) {
        paymentService.add(request);
    }

    @PutMapping("{id}")
    public void updatePayment(@PathVariable("id") Integer id, @RequestBody @Valid PaymentUpdateRequest request) {
        paymentService.updatePayment(id, request);
    }
}
