package com.mblaszczykowski.food;

import com.mblaszczykowski.restaurant.Restaurant;

public record FoodUpdateRequest(
        String name, String description, String category, double price, boolean isVegetarian, Restaurant restaurant
) {
}
