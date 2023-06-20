package com.mblaszczykowski.payment;

import com.mblaszczykowski.order.Order;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "payment")
public class Payment {
    public void setId(Integer paymentId) {
        this.id = paymentId;
    }

    public enum PaymentMethod {
        BLIK,
        CREDIT_CARD,
        CASH,
        OTHER
    }

    public enum PaymentStatus {
        IN_PROGRESS,
        PAID,
        CANCELLED,
        ERROR
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "payment_time", nullable = false)
    private LocalDateTime paymentTime;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public Payment() {
    }

    public Payment(Order order, PaymentMethod paymentMethod) {
        this.order = order;
        this.paymentTime = LocalDateTime.now();
        this.paymentMethod = paymentMethod;
        this.paymentStatus = PaymentStatus.IN_PROGRESS;
    }

    public Integer getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", order=" + order +
                ", paymentTime=" + paymentTime +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatus=" + paymentStatus +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, paymentTime, paymentMethod, paymentStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment payment)) return false;
        return Objects.equals(id, payment.id) && Objects.equals(order, payment.order) &&
                Objects.equals(paymentTime, payment.paymentTime) &&
                paymentMethod == payment.paymentMethod && paymentStatus == payment.paymentStatus;
    }
}
