package com.mblaszczykowski.review;

import com.mblaszczykowski.order.Order;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getReviews() {
        return reviewService.getAll();
    }

    @GetMapping("{id}")
    public Review getReview(@PathVariable("id") Integer id) {
        return reviewService.getById(id);
    }

    @GetMapping("restaurant/{restaurantId}")
    public List<Review> getReviewsByRestaurantId(@PathVariable("restaurantId") Integer restaurantId) {
        return reviewService.getReviewsByRestaurantId(restaurantId);
    }

    @GetMapping("date/{date}")
    public List<Review> getReviewsByDate(@PathVariable("date") LocalDateTime date) {
        return reviewService.getReviewsByDate(date);
    }

    @GetMapping("user/{customerId}")
    public List<Review> getReviewsByCustomerId(@PathVariable("customerId") Integer customerId) {
        return reviewService.getReviewsByCustomerId(customerId);
    }

    @PostMapping
    public void addReview(@RequestBody ReviewRegistrationRequest request) {
        reviewService.add(request);
    }

    @PutMapping("{id}")
    public void updateReview(@PathVariable("id") Integer id, @RequestBody ReviewUpdateRequest request) {
        reviewService.update(id, request);
    }

    @DeleteMapping("{id}")
    public void deleteReview(@PathVariable("id") Integer id) {
        reviewService.deleteById(id);
    }
}
