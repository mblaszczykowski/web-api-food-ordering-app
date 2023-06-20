package com.mblaszczykowski.payment;

public record PaymentUpdateRequest(
        Payment.PaymentStatus paymentStatus) {
}
