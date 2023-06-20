package com.mblaszczykowski.order;

import com.mblaszczykowski.food.Food;
import com.mblaszczykowski.customer.Customer;
import com.mblaszczykowski.restaurant.Restaurant;

import java.util.List;

public record OrderRegistrationRequest(
        Customer customer, List<Food> foods, String address, Order.DeliveryType deliveryType
) {
}
