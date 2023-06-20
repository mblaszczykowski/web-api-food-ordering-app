package com.mblaszczykowski.payment;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("payment-jpa")
public class PaymentJPADataAccessService implements PaymentDAO {
    private final PaymentRepository paymentRepository;

    public PaymentJPADataAccessService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @Override
    public void add(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> getById(Integer id) {
        return paymentRepository.findById(id);
    }

    @Override
    public void update(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public boolean existsByOrderId(Integer orderId) {
        return paymentRepository.existsByOrderId(orderId);
    }
}
