package com.mblaszczykowski.order;

import com.mblaszczykowski.food.Food;
import java.util.List;

public record OrderUpdateRequest(
        List<Food> foods, String address, Order.DeliveryType deliveryType, Order.OrderStatus status
) {
}
