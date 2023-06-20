package com.mblaszczykowski.payment;

import com.mblaszczykowski.order.Order;

public record PaymentRegistrationRequest(
        Order order, Payment.PaymentMethod paymentMethod
) {
}
