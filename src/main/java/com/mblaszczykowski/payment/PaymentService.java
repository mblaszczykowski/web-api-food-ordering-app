package com.mblaszczykowski.payment;

import com.mblaszczykowski.exception.NotValidResourceException;
import com.mblaszczykowski.exception.ResourceNotFoundException;
import com.mblaszczykowski.order.OrderDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Validated
public class PaymentService {
    private final PaymentDAO paymentDAO;
    private final OrderDAO orderDAO;

    public PaymentService(
            @Qualifier("order-jpa") OrderDAO orderDAO,
            @Qualifier("payment-jpa") PaymentDAO paymentDAO
    ) {
        this.paymentDAO = paymentDAO;
        this.orderDAO = orderDAO;
    }

    public Payment getById(Integer id) {
        return paymentDAO.getById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order with id [%s] not found".formatted(id))
                );
    }

    public List<Payment> getAll() {
        return paymentDAO.getAll();
    }

    public void add(@NotNull @Valid PaymentRegistrationRequest paymentRegistrationRequest) {
        if (paymentRegistrationRequest.order() == null || paymentRegistrationRequest.order().getId() == null) {
            throw new NotValidResourceException("Missing data");
        }

        var orderId = paymentRegistrationRequest.order().getId();
        var order = orderDAO.getById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("Order with id [%s] not found".formatted(orderId)));

        // Sprawdź czy płatność dla zamówienia już istnieje
        if (paymentDAO.existsByOrderId(orderId)) {
            throw new NotValidResourceException("Payment for order with id [%s] already exists".formatted(orderId));
        }

        // Update order status
        order.setPaid(true);
        orderDAO.update(order);

        // Add payment record
        Payment payment = new Payment(order, paymentRegistrationRequest.paymentMethod());
        paymentDAO.add(payment);
    }

    public void updatePayment(Integer id, @NotNull @Valid PaymentUpdateRequest paymentUpdateRequest) {
        var payment = paymentDAO.getById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment with id [%s] not found".formatted(id))
                );

        if (paymentUpdateRequest.paymentStatus() != null) {
            payment.setPaymentStatus(paymentUpdateRequest.paymentStatus());
        }

        paymentDAO.update(payment);
    }
}
