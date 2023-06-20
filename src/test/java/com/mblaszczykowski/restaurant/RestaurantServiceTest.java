package com.mblaszczykowski.restaurant;

import com.mblaszczykowski.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class RestaurantServiceTest {
    private RestaurantService restaurantService;

    @Mock
    private RestaurantDAO restaurantDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantService = new RestaurantService(restaurantDAO);
    }

    @Test
    void testGetAllRestaurants_ReturnsAllRestaurants() {
        // Arrange
        List<Restaurant> restaurants = List.of(new Restaurant(), new Restaurant());
        when(restaurantDAO.getAllRestaurants()).thenReturn(restaurants);

        // Act
        List<Restaurant> result = restaurantService.getAllRestaurants();

        // Assert
        assertEquals(restaurants.size(), result.size());
        assertEquals(restaurants, result);
        verify(restaurantDAO, times(1)).getAllRestaurants();
    }

    @Test
    void testGetRestaurantById_ExistingRestaurant_ReturnsRestaurant() {
        // Arrange
        Integer restaurantId = 1;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        when(restaurantDAO.getRestaurantById(restaurantId)).thenReturn(Optional.of(restaurant));

        // Act
        Restaurant result = restaurantService.getRestaurantById(restaurantId);

        // Assert
        assertNotNull(result);
        assertEquals(restaurantId, result.getId());
        verify(restaurantDAO, times(1)).getRestaurantById(restaurantId);
    }

    @Test
    void testGetRestaurantById_NonExistingRestaurant_ThrowsResourceNotFoundException() {
        // Arrange
        Integer restaurantId = 1;
        when(restaurantDAO.getRestaurantById(restaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> restaurantService.getRestaurantById(restaurantId));
        verify(restaurantDAO, times(1)).getRestaurantById(restaurantId);
    }

    @Test
    void testGetRestaurantsByName_ReturnsRestaurantsWithName() {
        // Arrange
        String restaurantName = "Restaurant";
        List<Restaurant> restaurants = List.of(new Restaurant(), new Restaurant());
        when(restaurantDAO.findByName(restaurantName)).thenReturn(restaurants);

        // Act
        List<Restaurant> result = restaurantService.getRestaurantsByName(restaurantName);

        // Assert
        assertEquals(restaurants.size(), result.size());
        assertEquals(restaurants, result);
        verify(restaurantDAO, times(1)).findByName(restaurantName);
    }

    @Test
    void testGetRestaurantsByDistrict_ExistingRestaurant_ReturnsRestaurant() {
        // Arrange
        String district = "District";
        Restaurant restaurant = new Restaurant();
        when(restaurantDAO.getRestaurantByDistrict(district)).thenReturn(Optional.of(restaurant));

        // Act
        Restaurant result = restaurantService.getRestaurantsByDistrict(district);

        // Assert
        assertNotNull(result);
        verify(restaurantDAO, times(1)).getRestaurantByDistrict(district);
    }

    @Test
    void testGetRestaurantsByDistrict_NonExistingRestaurant_ThrowsResourceNotFoundException() {
        // Arrange
        String district = "District";
        when(restaurantDAO.getRestaurantByDistrict(district)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> restaurantService.getRestaurantsByDistrict(district));
        verify(restaurantDAO, times(1)).getRestaurantByDistrict(district);
    }

    @Test
    void testAddRestaurant_ValidRestaurantRegistrationRequest_AddsRestaurant() {
        // Arrange
        RestaurantRegistrationRequest registrationRequest = new RestaurantRegistrationRequest(
                "Restaurant",
                "Description",
                "Address",
                "District",
                "123456789"
        );

        // Act
        assertDoesNotThrow(() -> restaurantService.addRestaurant(registrationRequest));

        // Assert
        verify(restaurantDAO, times(1)).addRestaurant(any(Restaurant.class));
    }

    @Test
    void testDeleteRestaurant_ExistingRestaurant_DeletesRestaurant() {
        // Arrange
        Integer restaurantId = 1;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        when(restaurantDAO.getRestaurantById(restaurantId)).thenReturn(Optional.of(restaurant));

        // Act
        assertDoesNotThrow(() -> restaurantService.deleteRestaurant(restaurantId));

        // Assert
        verify(restaurantDAO, times(1)).getRestaurantById(restaurantId);
        verify(restaurantDAO, times(1)).deleteRestaurant(restaurant);
    }

    @Test
    void testDeleteRestaurant_NonExistingRestaurant_ThrowsResourceNotFoundException() {
        // Arrange
        Integer restaurantId = 1;
        when(restaurantDAO.getRestaurantById(restaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> restaurantService.deleteRestaurant(restaurantId));
        verify(restaurantDAO, times(1)).getRestaurantById(restaurantId);
        verify(restaurantDAO, never()).deleteRestaurant(any(Restaurant.class));
    }

    @Test
    void testUpdateRestaurant_ValidRestaurantUpdateRequest_UpdatesRestaurant() {
        // Arrange
        Integer restaurantId = 1;
        Restaurant existingRestaurant = new Restaurant();
        existingRestaurant.setId(restaurantId);
        RestaurantUpdateRequest updateRequest = new RestaurantUpdateRequest(
                "New Name",
                "New Description",
                "New Address",
                "New District",
                "987654321"
        );
        when(restaurantDAO.getRestaurantById(restaurantId)).thenReturn(Optional.of(existingRestaurant));

        // Act
        assertDoesNotThrow(() -> restaurantService.updateRestaurant(restaurantId, updateRequest));

        // Assert
        verify(restaurantDAO, times(1)).getRestaurantById(restaurantId);
        verify(restaurantDAO, times(1)).updateRestaurant(existingRestaurant);
        assertEquals(updateRequest.name(), existingRestaurant.getName());
        assertEquals(updateRequest.description(), existingRestaurant.getDescription());
        assertEquals(updateRequest.address(), existingRestaurant.getAddress());
        assertEquals(updateRequest.district(), existingRestaurant.getDistrict());
        assertEquals(updateRequest.phoneNumber(), existingRestaurant.getPhoneNumber());
    }

    @Test
    void testUpdateRestaurant_NonExistingRestaurant_ThrowsResourceNotFoundException() {
        // Arrange
        Integer restaurantId = 1;
        RestaurantUpdateRequest updateRequest = new RestaurantUpdateRequest(
                "New Name",
                "New Description",
                "New Address",
                "New District",
                "987654321"
        );
        when(restaurantDAO.getRestaurantById(restaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> restaurantService.updateRestaurant(restaurantId, updateRequest));
        verify(restaurantDAO, times(1)).getRestaurantById(restaurantId);
        verify(restaurantDAO, never()).updateRestaurant(any(Restaurant.class));
    }
}
