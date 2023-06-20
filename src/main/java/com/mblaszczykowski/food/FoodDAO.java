package com.mblaszczykowski.food;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface FoodDAO {
    List<Food> getAllFood();

    Optional<Food> getFoodById(Integer id);

    List<Food> getFoodByCategory(String category);

    List<Food> getVegetarianFood();

    void addFood(Food food);

    void updateFood(Food food);

    void deleteFood(Food food);

    List<Food> findByRestaurantID(Integer restaurantID);

    List<Food> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Food> findByName(String name);

    List<Food> getFoodByRestaurantID(Integer restaurantID);

    List<Food> getFoodByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    List<Food> getFoodByName(String name);

    Optional<Food> getById(Integer foodId);

}