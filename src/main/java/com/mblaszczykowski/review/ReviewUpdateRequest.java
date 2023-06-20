package com.mblaszczykowski.review;

public record ReviewUpdateRequest(
        String name,
        Integer rating,
        String description
) {
}
