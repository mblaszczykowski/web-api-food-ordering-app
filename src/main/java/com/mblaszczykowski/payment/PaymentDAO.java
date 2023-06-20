package com.mblaszczykowski.payment;

import java.util.List;
import java.util.Optional;

public interface PaymentDAO {
    List<Payment> getAll();

    void add(Payment payment);

    Optional<Payment> getById(Integer id);

    void update(Payment payment);

    boolean existsByOrderId(Integer orderId);
}
