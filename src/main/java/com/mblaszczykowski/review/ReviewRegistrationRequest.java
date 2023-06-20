package com.mblaszczykowski.review;

import com.mblaszczykowski.customer.Customer;
import com.mblaszczykowski.food.Food;
import com.mblaszczykowski.order.Order;
import com.mblaszczykowski.restaurant.Restaurant;

import java.time.LocalDateTime;

public record ReviewRegistrationRequest(
        Customer customer,
        Restaurant restaurant,
        Order order,
        String name,
        Integer rating,
        String description
) {
}
