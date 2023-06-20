package com.mblaszczykowski.order;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {
    List<Order> getAll();

    void add(Order order);

    Optional<Order> getById(Integer id);

    void update(Order order);

    List<Order> getOrdersByCustomerId(Integer customerId);

    List<Order> getOrdersByRestaurantId(Integer restaurantId);

    boolean existsById(Integer orderId);
}