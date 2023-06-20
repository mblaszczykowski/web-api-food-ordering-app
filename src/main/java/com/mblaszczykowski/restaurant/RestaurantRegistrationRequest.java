package com.mblaszczykowski.restaurant;

public record RestaurantRegistrationRequest(
        String name, String description, String address, String district, String phoneNumber
) {
}
