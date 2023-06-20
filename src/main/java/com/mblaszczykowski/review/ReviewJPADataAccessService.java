package com.mblaszczykowski.review;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository("review-jpa")
public class ReviewJPADataAccessService implements ReviewDAO {
    private final ReviewRepository reviewRepository;

    public ReviewJPADataAccessService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getReviewsByRestaurantId(Integer restaurantId) {
        return reviewRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<Review> getReviewsByDate(LocalDateTime date) {
        return reviewRepository.findByReviewTime(date);
    }

    @Override
    public List<Review> getReviewsByCustomerId(Integer customerId) {
        return reviewRepository.findByCustomerId(customerId);
    }

    @Override
    public void add(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public void update(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public Optional<Review> getById(Integer id) {
        return reviewRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public boolean existsByOrderId(Integer orderId) {
        return reviewRepository.existsByOrderId(orderId);
    }
}
