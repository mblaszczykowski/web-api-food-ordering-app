package com.mblaszczykowski.customer;

public record CustomerRegistrationRequest(
        String firstname, String lastname, String email, String address, String phoneNumber
) {
}
