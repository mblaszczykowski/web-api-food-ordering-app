package com.mblaszczykowski.food;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/v1/food")
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public List<Food> getAllFood() {
        return foodService.getAllFood();
    }

    @GetMapping("{id}")
    public Food getFood(@PathVariable("id") Integer id) {
        return foodService.getFoodById(id);
    }

    @GetMapping("name/{name}")
    public List<Food> getFoodByName(@PathVariable("name") String name) {
        return foodService.getFoodByName(name);
    }

    @GetMapping("restaurant/{restaurantID}")
    public List<Food> getFoodByRestaurant(@PathVariable("restaurantID") Integer restaurantID) {
        return foodService.getFoodByRestaurantID(restaurantID);
    }

    @GetMapping("price-range")
    public List<Food> getFoodByPriceRange(
            @RequestParam("minPrice") BigDecimal minPrice,
            @RequestParam("maxPrice") BigDecimal maxPrice
    ) {
        return foodService.getFoodByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("category/{category}")
    public List<Food> getFoodByCategory(@PathVariable("category") String category) {
        return foodService.getFoodByCategory(category);
    }

    @GetMapping("type/vegetarian")
    public List<Food> getVegetarianFood() {
        return foodService.getVegetarianFood();
    }

    @PostMapping
    public void addFood(@RequestBody FoodRegistrationRequest request) {
        foodService.addFood(request);
    }

    @DeleteMapping("{id}")
    public void deleteFood(@PathVariable("id") Integer id) {
        foodService.deleteFood(id);
    }

    @PutMapping("{id}")
    public void updateFood(
            @PathVariable("id") Integer id,
            @RequestBody FoodUpdateRequest request
    ) {
        foodService.updateFood(id, request);
    }
}
