package com.mblaszczykowski.review;

import com.mblaszczykowski.customer.Customer;
import com.mblaszczykowski.customer.CustomerDAO;
import com.mblaszczykowski.exception.NotValidResourceException;
import com.mblaszczykowski.exception.ResourceNotFoundException;
import com.mblaszczykowski.order.Order;
import com.mblaszczykowski.order.OrderDAO;
import com.mblaszczykowski.restaurant.Restaurant;
import com.mblaszczykowski.restaurant.RestaurantDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReviewServiceTest {
    private ReviewService reviewService;

    @Mock
    private ReviewDAO reviewDAO;

    @Mock
    private CustomerDAO customerDAO;

    @Mock
    private RestaurantDAO restaurantDAO;

    @Mock
    private OrderDAO orderDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reviewService = new ReviewService(reviewDAO, customerDAO, restaurantDAO, orderDAO);
    }

    @Test
    void testGetById_ExistingReview_ReturnsReview() {
        // Arrange
        Integer reviewId = 1;
        Review review = new Review();
        review.setId(reviewId);
        when(reviewDAO.getById(reviewId)).thenReturn(Optional.of(review));

        // Act
        Review result = reviewService.getById(reviewId);

        // Assert
        assertNotNull(result);
        assertEquals(reviewId, result.getId());
        verify(reviewDAO, times(1)).getById(reviewId);
    }

    @Test
    void testGetById_NonExistingReview_ThrowsResourceNotFoundException() {
        // Arrange
        Integer reviewId = 1;
        when(reviewDAO.getById(reviewId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reviewService.getById(reviewId));
        verify(reviewDAO, times(1)).getById(reviewId);
    }

    @Test
    void testGetAll_ReturnsAllReviews() {
        // Arrange
        List<Review> reviews = List.of(new Review(), new Review());
        when(reviewDAO.getAll()).thenReturn(reviews);

        // Act
        List<Review> result = reviewService.getAll();

        // Assert
        assertEquals(reviews.size(), result.size());
        assertEquals(reviews, result);
        verify(reviewDAO, times(1)).getAll();
    }

    @Test
    void testGetReviewsByRestaurantId_ReturnsReviewsByRestaurantId() {
        // Arrange
        Integer restaurantId = 1;
        List<Review> reviews = List.of(new Review(), new Review());
        when(reviewDAO.getReviewsByRestaurantId(restaurantId)).thenReturn(reviews);

        // Act
        List<Review> result = reviewService.getReviewsByRestaurantId(restaurantId);

        // Assert
        assertEquals(reviews.size(), result.size());
        assertEquals(reviews, result);
        verify(reviewDAO, times(1)).getReviewsByRestaurantId(restaurantId);
    }

    @Test
    void testGetReviewsByDate_ReturnsReviewsByDate() {
        // Arrange
        LocalDateTime date = LocalDateTime.now();
        List<Review> reviews = List.of(new Review(), new Review());
        when(reviewDAO.getReviewsByDate(date)).thenReturn(reviews);

        // Act
        List<Review> result = reviewService.getReviewsByDate(date);

        // Assert
        assertEquals(reviews.size(), result.size());
        assertEquals(reviews, result);
        verify(reviewDAO, times(1)).getReviewsByDate(date);
    }

    @Test
    void testGetReviewsByCustomerId_ReturnsReviewsByCustomerId() {
        // Arrange
        Integer customerId = 1;
        List<Review> reviews = List.of(new Review(), new Review());
        when(reviewDAO.getReviewsByCustomerId(customerId)).thenReturn(reviews);

        // Act
        List<Review> result = reviewService.getReviewsByCustomerId(customerId);

        // Assert
        assertEquals(reviews.size(), result.size());
        assertEquals(reviews, result);
        verify(reviewDAO, times(1)).getReviewsByCustomerId(customerId);
    }

    @Test
    void testAdd_ValidReviewRegistrationRequest_AddsReview() {
        // Arrange
        Customer customer = new Customer();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        Order order = new Order();
        ReviewRegistrationRequest registrationRequest = new ReviewRegistrationRequest(
                customer,
                restaurant,
                order,
                "Review Name",
                5,
                "Review Description"
        );
        when(reviewDAO.existsByOrderId(order.getId())).thenReturn(false);
        when(customerDAO.existsById(customer.getId())).thenReturn(true);
        when(restaurantDAO.existsById(restaurant.getId())).thenReturn(true);
        when(orderDAO.existsById(order.getId())).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> reviewService.add(registrationRequest));

        // Assert
        verify(reviewDAO, times(1)).add(any(Review.class));
    }

    @Test
    void testAdd_ReviewForExistingOrder_ThrowsNotValidResourceException() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        Order order = new Order();
        order.setId(1);
        ReviewRegistrationRequest registrationRequest = new ReviewRegistrationRequest(
                customer,
                restaurant,
                order,
                "Review Name",
                5,
                "Review Description"
        );
        when(reviewDAO.existsByOrderId(order.getId())).thenReturn(true);

        // Act & Assert
        assertThrows(NotValidResourceException.class, () -> reviewService.add(registrationRequest));
        verify(reviewDAO, never()).add(any(Review.class));
    }

    @Test
    void testAdd_InvalidReviewRegistrationRequest_ThrowsNotValidResourceException() {
        // Arrange
        ReviewRegistrationRequest registrationRequest = new ReviewRegistrationRequest(
                null,
                null,
                null,
                null,
                null,
                null
        );

        // Act & Assert
        assertThrows(NotValidResourceException.class, () -> reviewService.add(registrationRequest));
        verify(reviewDAO, never()).add(any(Review.class));
    }

    @Test
    void testAdd_ReviewWithNonExistingCustomer_ThrowsResourceNotFoundException() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        Order order = new Order();
        order.setId(1);
        ReviewRegistrationRequest registrationRequest = new ReviewRegistrationRequest(
                customer,
                restaurant,
                order,
                "Review Name",
                5,
                "Review Description"
        );
        when(reviewDAO.existsByOrderId(order.getId())).thenReturn(false);
        when(customerDAO.existsById(customer.getId())).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reviewService.add(registrationRequest));
        verify(reviewDAO, never()).add(any(Review.class));
    }

    @Test
    void testAdd_ReviewWithNonExistingRestaurant_ThrowsResourceNotFoundException() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        Order order = new Order();
        order.setId(1);
        ReviewRegistrationRequest registrationRequest = new ReviewRegistrationRequest(
                customer,
                restaurant,
                order,
                "Review Name",
                5,
                "Review Description"
        );
        when(reviewDAO.existsByOrderId(order.getId())).thenReturn(false);
        when(customerDAO.existsById(customer.getId())).thenReturn(true);
        when(restaurantDAO.existsById(restaurant.getId())).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reviewService.add(registrationRequest));
        verify(reviewDAO, never()).add(any(Review.class));
    }

    @Test
    void testAdd_ReviewWithNonExistingOrder_ThrowsResourceNotFoundException() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        Order order = new Order();
        order.setId(1);
        ReviewRegistrationRequest registrationRequest = new ReviewRegistrationRequest(
                customer,
                restaurant,
                order,
                "Review Name",
                5,
                "Review Description"
        );
        when(reviewDAO.existsByOrderId(order.getId())).thenReturn(false);
        when(customerDAO.existsById(customer.getId())).thenReturn(true);
        when(restaurantDAO.existsById(restaurant.getId())).thenReturn(true);
        when(orderDAO.existsById(order.getId())).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reviewService.add(registrationRequest));
        verify(reviewDAO, never()).add(any(Review.class));
    }

    @Test
    void testUpdate_ValidReviewUpdateRequest_UpdatesReview() {
        // Arrange
        Integer reviewId = 1;
        ReviewUpdateRequest updateRequest = new ReviewUpdateRequest(
                "New Review Name",
                4,
                "New Review Description"
        );
        Review review = new Review();
        review.setId(reviewId);
        when(reviewDAO.getById(reviewId)).thenReturn(Optional.of(review));

        // Act
        assertDoesNotThrow(() -> reviewService.update(reviewId, updateRequest));

        // Assert
        verify(reviewDAO, times(1)).update(any(Review.class));
    }

    @Test
    void testUpdate_NonExistingReview_ThrowsResourceNotFoundException() {
        // Arrange
        Integer reviewId = 1;
        ReviewUpdateRequest updateRequest = new ReviewUpdateRequest(
                "New Review Name",
                4,
                "New Review Description"
        );
        when(reviewDAO.getById(reviewId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reviewService.update(reviewId, updateRequest));
        verify(reviewDAO, never()).update(any(Review.class));
    }


    @Test
    void testDeleteById_NonExistingReview_ThrowsResourceNotFoundException() {
        // Arrange
        Integer reviewId = 1;
        when(reviewDAO.getById(reviewId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reviewService.deleteById(reviewId));
        verify(reviewDAO, never()).deleteById(reviewId);
    }
}
