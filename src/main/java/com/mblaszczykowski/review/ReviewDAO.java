package com.mblaszczykowski.review;

import com.mblaszczykowski.order.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewDAO {
    List<Review> getAll();

    List<Review> getReviewsByRestaurantId(Integer restaurantId);

    List<Review> getReviewsByDate(LocalDateTime date);

    List<Review> getReviewsByCustomerId(Integer customerId);

    void add(Review review);

    void update(Review review);

    Optional<Review> getById(Integer id);

    void deleteById(Integer id);

    boolean existsByOrderId(Integer orderId);
}
