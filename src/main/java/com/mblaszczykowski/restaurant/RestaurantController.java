package com.mblaszczykowski.restaurant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<Restaurant> getRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("{id}")
    public Restaurant getRestaurant(@PathVariable("id") Integer id) {
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping("name/{name}")
    public List<Restaurant> getRestaurantsByName(@PathVariable("name") String name) {
        return restaurantService.getRestaurantsByName(name);
    }

    @GetMapping("district/{district}")
    public Restaurant getRestaurantByDistrict(@PathVariable("district") String district) {
        return restaurantService.getRestaurantsByDistrict(district);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addRestaurant(@Valid @RequestBody RestaurantRegistrationRequest request) {
        restaurantService.addRestaurant(request);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable("id") Integer id) {
        restaurantService.deleteRestaurant(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRestaurant(
            @PathVariable("id") Integer id,
            @Valid @RequestBody RestaurantUpdateRequest request
    ) {
        restaurantService.updateRestaurant(id, request);
    }
}
