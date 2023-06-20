package com.mblaszczykowski.restaurant;

public record RestaurantUpdateRequest(
        String name, String description, String address, String district, String phoneNumber
) {
}
