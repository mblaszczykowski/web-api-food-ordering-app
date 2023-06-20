package com.mblaszczykowski.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    Optional<Restaurant> findByDistrict(String district);
    List<Restaurant> findByName(String name);

    @Override
    Restaurant save(Restaurant restaurant); // Typ zwracany został zmieniony na Restaurant

    List<Restaurant> findAll();

    Optional<Restaurant> findById(Integer id);

    void delete(Restaurant restaurant);

    boolean existsById(Integer restaurantId);
}
