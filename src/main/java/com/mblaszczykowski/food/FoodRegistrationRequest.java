package com.mblaszczykowski.food;

import com.mblaszczykowski.restaurant.Restaurant;

public record FoodRegistrationRequest(
        Restaurant restaurant, String name, String description, String category, double price, boolean isVegetarian
) {
}
