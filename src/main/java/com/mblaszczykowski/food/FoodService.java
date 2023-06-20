package com.mblaszczykowski.food;

import com.mblaszczykowski.exception.NotValidResourceException;
import com.mblaszczykowski.exception.ResourceNotFoundException;
import com.mblaszczykowski.restaurant.Restaurant;
import com.mblaszczykowski.restaurant.RestaurantDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FoodService {
    private final FoodDAO foodDAO;
    private final RestaurantDAO restaurantDAO;

    public FoodService(@Qualifier("food-jpa") FoodDAO foodDAO, RestaurantDAO restaurantDAO) {
        this.foodDAO = foodDAO;
        this.restaurantDAO = restaurantDAO;
    }

    public List<Food> getAllFood() {
        return foodDAO.getAllFood();
    }

    public Food getFoodById(Integer id) {
        return foodDAO.getFoodById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food with id [%s] not found".formatted(id))
                );
    }

    public List<Food> getFoodByRestaurantID(Integer restaurantID) {
        return foodDAO.getFoodByRestaurantID(restaurantID);
    }

    public List<Food> getFoodByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return foodDAO.getFoodByPriceRange(minPrice, maxPrice);
    }

    public List<Food> getFoodByCategory(String category) {
        return foodDAO.getFoodByCategory(category);
    }

    public List<Food> getVegetarianFood() {
        return foodDAO.getVegetarianFood();
    }

    public void addFood(FoodRegistrationRequest foodRegistrationRequest) {
        String name = foodRegistrationRequest.name();
        String description = foodRegistrationRequest.description();
        String category = foodRegistrationRequest.category();
        BigDecimal price = BigDecimal.valueOf(foodRegistrationRequest.price());
        boolean isVegetarian = foodRegistrationRequest.isVegetarian();
        Restaurant restaurant = foodRegistrationRequest.restaurant();

        if (name == null || description == null || category == null || price.compareTo(BigDecimal.ZERO) <= 0 || restaurant == null) {
            throw new NotValidResourceException("Missing or invalid data");
        }

        // Sprawdź czy restauracja istnieje
        Integer restaurantId = restaurant.getId();
        if (!restaurantDAO.existsById(restaurantId)) {
            throw new ResourceNotFoundException("Restaurant with id [%s] not found".formatted(restaurantId));
        }

        Food food = new Food(name, description, category, price, isVegetarian, restaurant);
        foodDAO.addFood(food);
    }

    public void deleteFood(Integer id) {
        Food food = foodDAO.getFoodById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food with id [%s] not found".formatted(id))
                );

        foodDAO.deleteFood(food);
    }

    public List<Food> getFoodByName(String name) {
        return foodDAO.getFoodByName(name);
    }

    public void updateFood(Integer id, FoodUpdateRequest foodUpdateRequest) {
        Food food = foodDAO.getFoodById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Food with id [%s] not found".formatted(id))
                );

        if (foodUpdateRequest.price() != 0) {
            BigDecimal price = BigDecimal.valueOf(foodUpdateRequest.price());

            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new NotValidResourceException("Price must be a positive value");
            }

            food.setPrice(price);
        }

        if (foodUpdateRequest.name() != null) {
            food.setName(foodUpdateRequest.name());
        }

        if (foodUpdateRequest.description() != null) {
            food.setDescription(foodUpdateRequest.description());
        }

        if (foodUpdateRequest.category() != null) {
            food.setCategory(foodUpdateRequest.category());
        }

        if (foodUpdateRequest.restaurant() != null) {
            food.setRestaurant(foodUpdateRequest.restaurant());
        }

        foodDAO.updateFood(food);
    }
}
