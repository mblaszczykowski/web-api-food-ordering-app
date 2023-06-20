package com.mblaszczykowski.restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantDAO {
    List<Restaurant> getAllRestaurants();

    void addRestaurant(Restaurant user);

    Optional<Restaurant> getRestaurantById(Integer id);
    Optional<Restaurant> getRestaurantByDistrict(String district);

    void deleteRestaurant(Restaurant restaurant);

    void updateRestaurant(Restaurant restaurant);

    List<Restaurant> findByName(String name);

    boolean existsById(Integer restaurantId);
}
