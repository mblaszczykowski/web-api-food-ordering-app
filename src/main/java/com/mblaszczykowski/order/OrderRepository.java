package com.mblaszczykowski.order;

import com.mblaszczykowski.customer.Customer;
import com.mblaszczykowski.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAll();

    Optional<Order> findById(Integer id);

    List<Order> findByCustomer_Id(Integer customerId);

    List<Order> findByFoodsRestaurant_Id(Integer restaurantId);
}
