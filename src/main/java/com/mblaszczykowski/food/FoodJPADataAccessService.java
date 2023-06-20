package com.mblaszczykowski.food;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository("food-jpa")
public class FoodJPADataAccessService implements FoodDAO {
    private final FoodRepository foodRepository;

    public FoodJPADataAccessService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public List<Food> getAllFood() {
        return foodRepository.findAll();
    }

    @Override
    public Optional<Food> getFoodById(Integer id) {
        return foodRepository.findById(id);
    }

    @Override
    public List<Food> getFoodByCategory(String category) {
        return foodRepository.findByCategory(category);
    }

    @Override
    public List<Food> getVegetarianFood() {
        return foodRepository.findAll().stream()
                .filter(Food::isVegetarian)
                .collect(Collectors.toList());
    }

    @Override
    public void addFood(Food food) {
        foodRepository.save(food);
    }

    @Override
    public void updateFood(Food food) {
        foodRepository.save(food);
    }

    @Override
    public void deleteFood(Food food) {
        foodRepository.delete(food);
    }

    @Override
    public List<Food> findByRestaurantID(Integer restaurantID) {
        return foodRepository.findByRestaurantId(restaurantID);
    }

    @Override
    public List<Food> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return foodRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Food> findByName(String name) {
        return foodRepository.findByName(name);
    }


    @Override
    public List<Food> getFoodByRestaurantID(Integer restaurantID) {
        return foodRepository.findByRestaurantId(restaurantID);
    }

    @Override
    public List<Food> getFoodByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return foodRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Food> getFoodByName(String name) {
        return foodRepository.findByName(name);
    }

    @Override
    public Optional<Food> getById(Integer foodId) {
        return foodRepository.findById(foodId);
    }


}
