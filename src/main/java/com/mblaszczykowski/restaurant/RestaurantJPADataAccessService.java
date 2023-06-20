package com.mblaszczykowski.restaurant;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("restaurant-jpa")
public class RestaurantJPADataAccessService implements RestaurantDAO {
    private final RestaurantRepository restaurantRepository;

    public RestaurantJPADataAccessService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public void addRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    @Override
    public Optional<Restaurant> getRestaurantById(Integer id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public Optional<Restaurant> getRestaurantByDistrict(String district) {
        return restaurantRepository.findByDistrict(district);
    }

    @Override
    public void deleteRestaurant(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }

    @Override
    public void updateRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> findByName(String name) {
        return restaurantRepository.findByName(name);
    }

    @Override
    public boolean existsById(Integer restaurantId) {
        return restaurantRepository.existsById(restaurantId);
    }
}
