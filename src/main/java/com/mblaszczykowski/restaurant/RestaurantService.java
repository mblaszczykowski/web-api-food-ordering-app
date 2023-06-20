package com.mblaszczykowski.restaurant;

import com.mblaszczykowski.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RestaurantService {
    private final RestaurantDAO restaurantDAO;

    public RestaurantService(RestaurantDAO restaurantDAO) {
        this.restaurantDAO = restaurantDAO;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantDAO.getAllRestaurants();
    }

    public Restaurant getRestaurantById(Integer id) {
        return restaurantDAO.getRestaurantById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant with id [%s] not found".formatted(id))
                );
    }

    public List<Restaurant> getRestaurantsByName(String name) {
        return restaurantDAO.findByName(name);
    }

    public Restaurant getRestaurantsByDistrict(String district) {
        return restaurantDAO.getRestaurantByDistrict(district)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurants from district [%s] not found".formatted(district))
                );
    }

    public void addRestaurant(RestaurantRegistrationRequest restaurantRegistrationRequest) {
        Restaurant restaurant = new Restaurant(
                restaurantRegistrationRequest.name(),
                restaurantRegistrationRequest.description(),
                restaurantRegistrationRequest.address(),
                restaurantRegistrationRequest.district(),
                restaurantRegistrationRequest.phoneNumber()
        );
        restaurantDAO.addRestaurant(restaurant);
    }

    public void deleteRestaurant(Integer id) {
        Restaurant restaurant = restaurantDAO.getRestaurantById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant with id [%s] not found".formatted(id))
                );

        restaurantDAO.deleteRestaurant(restaurant);
    }

    public void updateRestaurant(Integer id, RestaurantUpdateRequest restaurantUpdateRequest) {
        Restaurant restaurant = restaurantDAO.getRestaurantById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant with id [%s] not found".formatted(id))
                );

        if (restaurantUpdateRequest.name() != null) {
            restaurant.setName(restaurantUpdateRequest.name());
        }

        if (restaurantUpdateRequest.description() != null) {
            restaurant.setDescription(restaurantUpdateRequest.description());
        }

        if (restaurantUpdateRequest.address() != null) {
            restaurant.setAddress(restaurantUpdateRequest.address());
        }

        if (restaurantUpdateRequest.district() != null) {
            restaurant.setDistrict(restaurantUpdateRequest.district());
        }

        if (restaurantUpdateRequest.phoneNumber() != null) {
            restaurant.setPhoneNumber(restaurantUpdateRequest.phoneNumber());
        }

        restaurantDAO.updateRestaurant(restaurant);
    }
}
