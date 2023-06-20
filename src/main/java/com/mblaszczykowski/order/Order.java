package com.mblaszczykowski.order;

import com.mblaszczykowski.customer.Customer;
import com.mblaszczykowski.food.Food;
import com.mblaszczykowski.restaurant.Restaurant;
import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {




    public enum OrderStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        CANCELLED,
        AWAITING_PICKUP,
        READY_FOR_SHIPPING,
        SHIPPED,
        DELIVERED
    }

    public enum DeliveryType {
        PICKUP,
        SHIPPING,
        OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "order_food",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    private List<Food> foods = new ArrayList<>();

    @Formula("(SELECT COALESCE(SUM(f.price), 0) FROM food f JOIN order_food of ON f.id = of.food_id WHERE of.order_id = id)")
    private BigDecimal totalAmount;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "delivery_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    public Order() {
    }

    public Order(Customer customer, List<Food> foods, String address, DeliveryType deliveryType) {
        this.customer = customer;
        this.foods = foods;
        this.address = address;
        this.deliveryType = deliveryType;
        this.orderTime = LocalDateTime.now();
        this.isPaid = false;
        this.status = OrderStatus.PENDING;
    }

    public Integer getId() {
        return id;
    }
    public void setId(int i) {
        this.id = i;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public String getAddress() {
        return address;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return isPaid == order.isPaid && Objects.equals(id, order.id) && Objects.equals(customer, order.customer) && Objects.equals(foods, order.foods) && Objects.equals(orderTime, order.orderTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, foods, orderTime, isPaid);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", foods=" + foods +
                ", address='" + address + '\'' +
                ", deliveryType=" + deliveryType +
                ", orderTime=" + orderTime +
                ", isPaid=" + isPaid +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                '}';
    }
}