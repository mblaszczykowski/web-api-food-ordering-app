package com.mblaszczykowski.review;

import com.mblaszczykowski.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByReviewTime(LocalDateTime date);

    List<Review> findByRestaurantId(Integer restaurantId);

    List<Review> findByCustomerId(Integer customerId);

    boolean existsByOrderId(Integer orderId);
}
