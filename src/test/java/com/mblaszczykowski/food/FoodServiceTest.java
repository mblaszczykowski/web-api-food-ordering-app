import com.mblaszczykowski.exception.NotValidResourceException;
import com.mblaszczykowski.exception.ResourceNotFoundException;
import com.mblaszczykowski.food.*;
import com.mblaszczykowski.restaurant.Restaurant;
import com.mblaszczykowski.restaurant.RestaurantDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodServiceTest {
    @Mock
    private FoodDAO foodDAO;

    @Mock
    private RestaurantDAO restaurantDAO;

    private FoodService foodService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        foodService = new FoodService(foodDAO, restaurantDAO);
    }

    @Test
    void testGetAllFood() {
        List<Food> foodList = new ArrayList<>();
        foodList.add(new Food("Pizza", "Delicious pizza", "Italian", BigDecimal.valueOf(12.99), true, new Restaurant()));
        foodList.add(new Food("Burger", "Juicy burger", "American", BigDecimal.valueOf(9.99), false, new Restaurant()));
        when(foodDAO.getAllFood()).thenReturn(foodList);

        List<Food> result = foodService.getAllFood();

        assertEquals(2, result.size());
        assertEquals("Pizza", result.get(0).getName());
        assertEquals("Burger", result.get(1).getName());
    }

    @Test
    void testGetFoodById_ExistingId_ReturnsFood() {
        int foodId = 1;
        Food expectedFood = new Food("Pizza", "Delicious pizza", "Italian", BigDecimal.valueOf(12.99), true, new Restaurant());
        when(foodDAO.getFoodById(foodId)).thenReturn(Optional.of(expectedFood));

        Food result = foodService.getFoodById(foodId);

        assertEquals(expectedFood, result);
    }

    @Test
    void testGetFoodById_NonExistingId_ThrowsResourceNotFoundException() {
        int foodId = 1;
        when(foodDAO.getFoodById(foodId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> foodService.getFoodById(foodId));
    }

    @Test
    void testGetFoodByRestaurantID_ExistingId_ReturnsFoodList() {
        int restaurantId = 1;
        List<Food> foodList = new ArrayList<>();
        foodList.add(new Food("Pizza", "Delicious pizza", "Italian", BigDecimal.valueOf(12.99), true, new Restaurant()));
        foodList.add(new Food("Pasta", "Tasty pasta", "Italian", BigDecimal.valueOf(10.99), true, new Restaurant()));
        when(foodDAO.getFoodByRestaurantID(restaurantId)).thenReturn(foodList);

        List<Food> result = foodService.getFoodByRestaurantID(restaurantId);

        assertEquals(2, result.size());
        assertEquals("Pizza", result.get(0).getName());
        assertEquals("Pasta", result.get(1).getName());
    }

    @Test
    void testGetFoodByPriceRange_ReturnsFoodList() {
        BigDecimal minPrice = BigDecimal.valueOf(10.0);
        BigDecimal maxPrice = BigDecimal.valueOf(20.0);
        List<Food> foodList = new ArrayList<>();
        foodList.add(new Food("Pizza", "Delicious pizza", "Italian", BigDecimal.valueOf(12.99), true, new Restaurant()));
        foodList.add(new Food("Burger", "Juicy burger", "American", BigDecimal.valueOf(15.99), false, new Restaurant()));
        when(foodDAO.getFoodByPriceRange(minPrice, maxPrice)).thenReturn(foodList);

        List<Food> result = foodService.getFoodByPriceRange(minPrice, maxPrice);

        assertEquals(2, result.size());
        assertEquals("Pizza", result.get(0).getName());
        assertEquals("Burger", result.get(1).getName());
    }

    @Test
    void testGetFoodByCategory_ReturnsFoodList() {
        String category = "Italian";
        List<Food> foodList = new ArrayList<>();
        foodList.add(new Food("Pizza", "Delicious pizza", "Italian", BigDecimal.valueOf(12.99), true, new Restaurant()));
        foodList.add(new Food("Pasta", "Tasty pasta", "Italian", BigDecimal.valueOf(10.99), true, new Restaurant()));
        when(foodDAO.getFoodByCategory(category)).thenReturn(foodList);

        List<Food> result = foodService.getFoodByCategory(category);

        assertEquals(2, result.size());
        assertEquals("Pizza", result.get(0).getName());
        assertEquals("Pasta", result.get(1).getName());
    }

    @Test
    void testGetVegetarianFood_ReturnsFoodList() {
        List<Food> foodList = new ArrayList<>();
        foodList.add(new Food("Pizza", "Delicious pizza", "Italian", BigDecimal.valueOf(12.99), true, new Restaurant()));
        foodList.add(new Food("Salad", "Fresh salad", "Healthy", BigDecimal.valueOf(8.99), true, new Restaurant()));
        when(foodDAO.getVegetarianFood()).thenReturn(foodList);

        List<Food> result = foodService.getVegetarianFood();

        assertEquals(2, result.size());
        assertEquals("Pizza", result.get(0).getName());
        assertEquals("Salad", result.get(1).getName());
    }

    @Test
    void testAddFood_InvalidRestaurant_ThrowsResourceNotFoundException() {
        FoodRegistrationRequest request = new FoodRegistrationRequest(new Restaurant(), "Pizza", "Delicious pizza", "Italian", 12.99, true);

        when(restaurantDAO.existsById(anyInt())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> foodService.addFood(request));
        verify(foodDAO, never()).addFood(any(Food.class));
    }

    @Test
    void testDeleteFood_ExistingId_DeletesFood() {
        int foodId = 1;
        Food existingFood = new Food();
        when(foodDAO.getFoodById(foodId)).thenReturn(Optional.of(existingFood));

        foodService.deleteFood(foodId);

        verify(foodDAO, times(1)).deleteFood(existingFood);
    }

    @Test
    void testDeleteFood_NonExistingId_ThrowsResourceNotFoundException() {
        int foodId = 1;
        when(foodDAO.getFoodById(foodId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> foodService.deleteFood(foodId));
        verify(foodDAO, never()).deleteFood(any(Food.class));
    }

}
