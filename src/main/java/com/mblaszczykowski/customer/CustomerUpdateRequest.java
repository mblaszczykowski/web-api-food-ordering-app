package com.mblaszczykowski.customer;

public record CustomerUpdateRequest(
        String firstname, String lastname, String email, String address, String phoneNumber
) {
}
