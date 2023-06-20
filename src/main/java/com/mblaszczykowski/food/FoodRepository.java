package com.mblaszczykowski.food;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Integer> {
    List<Food> findByCategory(String category);

    List<Food> findByRestaurantId(Integer restaurantID);

    List<Food> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Food> findByName(String name);

}
