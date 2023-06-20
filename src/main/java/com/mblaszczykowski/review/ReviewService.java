package com.mblaszczykowski.review;

import com.mblaszczykowski.customer.Customer;
import com.mblaszczykowski.customer.CustomerDAO;
import com.mblaszczykowski.exception.NotValidResourceException;
import com.mblaszczykowski.exception.ResourceNotFoundException;
import com.mblaszczykowski.food.Food;
import com.mblaszczykowski.order.Order;
import com.mblaszczykowski.order.OrderDAO;
import com.mblaszczykowski.restaurant.Restaurant;
import com.mblaszczykowski.restaurant.RestaurantDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewDAO reviewDAO;
    private final CustomerDAO customerDAO;
    private final RestaurantDAO restaurantDAO;
    private final OrderDAO orderDAO;

    public ReviewService(@Qualifier("review-jpa") ReviewDAO reviewDAO,
                         CustomerDAO customerDAO,
                         RestaurantDAO restaurantDAO,
                         OrderDAO orderDAO) {
        this.reviewDAO = reviewDAO;
        this.customerDAO = customerDAO;
        this.restaurantDAO = restaurantDAO;
        this.orderDAO = orderDAO;
    }

    public Review getById(Integer id) {
        return reviewDAO.getById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Review with id [%s] not found".formatted(id))
                );
    }

    public List<Review> getAll() {
        return reviewDAO.getAll();
    }

    public List<Review> getReviewsByRestaurantId(Integer restaurantId) {
        return reviewDAO.getReviewsByRestaurantId(restaurantId);
    }

    public List<Review> getReviewsByDate(LocalDateTime date) {
        return reviewDAO.getReviewsByDate(date);
    }

    public List<Review> getReviewsByCustomerId(Integer customerId) {
        return reviewDAO.getReviewsByCustomerId(customerId);
    }

    public void add(ReviewRegistrationRequest reviewRegistrationRequest) {
        if (reviewRegistrationRequest.customer() == null ||
                reviewRegistrationRequest.order() == null ||
                reviewRegistrationRequest.restaurant() == null ||
                reviewRegistrationRequest.name() == null ||
                reviewRegistrationRequest.rating() == null ||
                reviewRegistrationRequest.description() == null) {
            throw new NotValidResourceException("Missing data");
        }

        Customer customer = reviewRegistrationRequest.customer();
        Order order = reviewRegistrationRequest.order();
        Restaurant restaurant = reviewRegistrationRequest.restaurant();
        String name = reviewRegistrationRequest.name();
        Integer rating = reviewRegistrationRequest.rating();
        String description = reviewRegistrationRequest.description();

        // Sprawdź czy recenzja dla zamówienia już istnieje
        Integer orderId = order.getId();
        if (reviewDAO.existsByOrderId(orderId)) {
            throw new NotValidResourceException("Review for order with id [%s] already exists".formatted(orderId));
        }

        // Sprawdź czy klient istnieje
        Integer customerId = customer.getId();
        if (!customerDAO.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer with id [%s] not found".formatted(customerId));
        }

        // Sprawdź czy restauracja istnieje
        Integer restaurantId = restaurant.getId();
        if (!restaurantDAO.existsById(restaurantId)) {
            throw new ResourceNotFoundException("Restaurant with id [%s] not found".formatted(restaurantId));
        }

        // Sprawdź czy zamówienie istnieje
        if (!orderDAO.existsById(orderId)) {
            throw new ResourceNotFoundException("Order with id [%s] not found".formatted(orderId));
        }

        Review review = new Review(customer, restaurant, order, name, rating, description);
        reviewDAO.add(review);
    }

    public void update(Integer id, ReviewUpdateRequest reviewUpdateRequest) {
        Review review = reviewDAO.getById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Review with id [%s] not found".formatted(id))
                );

        if (reviewUpdateRequest.name() != null) {
            review.setName(reviewUpdateRequest.name());
        }

        if (reviewUpdateRequest.rating() != null) {
            review.setRating(reviewUpdateRequest.rating());
        }

        if (reviewUpdateRequest.description() != null) {
            review.setDescription(reviewUpdateRequest.description());
        }

        reviewDAO.update(review);
    }

    public void deleteById(Integer id) {
        reviewDAO.getById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Review with id [%s] not found".formatted(id))
                );
    }
}
