import com.mblaszczykowski.customer.Customer;
import com.mblaszczykowski.customer.CustomerDAO;
import com.mblaszczykowski.exception.NotValidResourceException;
import com.mblaszczykowski.exception.ResourceNotFoundException;
import com.mblaszczykowski.food.Food;
import com.mblaszczykowski.food.FoodDAO;
import com.mblaszczykowski.order.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    private OrderService orderService;

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private FoodDAO foodDAO;

    @Mock
    private CustomerDAO customerDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderDAO, foodDAO, customerDAO);
    }

    @Test
    void testGetById() {
        Order order = new Order(new Customer(), new ArrayList<>(), "Address", Order.DeliveryType.PICKUP);
        when(orderDAO.getById(anyInt())).thenReturn(Optional.of(order));

        Order result = orderService.getById(1);

        assertNotNull(result);
        assertEquals(order, result);
        verify(orderDAO, times(1)).getById(eq(1));
    }

    @Test
    void testGetByIdNotFound() {
        when(orderDAO.getById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getById(1));

        verify(orderDAO, times(1)).getById(eq(1));
    }

    @Test
    void testGetAll() {
        List<Order> orders = List.of(new Order(), new Order());
        when(orderDAO.getAll()).thenReturn(orders);

        List<Order> result = orderService.getAll();

        assertNotNull(result);
        assertEquals(orders, result);
        verify(orderDAO, times(1)).getAll();
    }

    @Test
    void testGetOrdersByCustomerId() {
        List<Order> orders = List.of(new Order(), new Order());
        when(orderDAO.getOrdersByCustomerId(anyInt())).thenReturn(orders);

        List<Order> result = orderService.getOrdersByCustomerId(1);

        assertNotNull(result);
        assertEquals(orders, result);
        verify(orderDAO, times(1)).getOrdersByCustomerId(eq(1));
    }

    @Test
    void testGetOrdersByRestaurantId() {
        List<Order> orders = List.of(new Order(), new Order());
        when(orderDAO.getOrdersByRestaurantId(anyInt())).thenReturn(orders);

        List<Order> result = orderService.getOrdersByRestaurantId(1);

        assertNotNull(result);
        assertEquals(orders, result);
        verify(orderDAO, times(1)).getOrdersByRestaurantId(eq(1));
    }



    @Test
    void testAddMissingData() {
        OrderRegistrationRequest registrationRequest = new OrderRegistrationRequest(
                null, null, null, null
        );

        assertThrows(NotValidResourceException.class, () -> orderService.add(registrationRequest));

        verify(customerDAO, never()).getById(anyInt());
        verify(foodDAO, never()).getById(anyInt());
        verify(orderDAO, never()).add(any(Order.class));
    }


    @Test
    void testUpdateOrder() {
        Order order = new Order();
        OrderUpdateRequest updateRequest = new OrderUpdateRequest(
                List.of(new Food()), "New Address", Order.DeliveryType.SHIPPING, Order.OrderStatus.PROCESSING
        );

        when(orderDAO.getById(anyInt())).thenReturn(Optional.of(order));

        orderService.updateOrder(1, updateRequest);

        verify(orderDAO, times(1)).getById(eq(1));
        verify(orderDAO, times(1)).update(any(Order.class));
    }

    @Test
    void testUpdateOrderNotFound() {
        OrderUpdateRequest updateRequest = new OrderUpdateRequest(
                List.of(new Food()), "New Address", Order.DeliveryType.SHIPPING, Order.OrderStatus.PROCESSING
        );

        when(orderDAO.getById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.updateOrder(1, updateRequest));

        verify(orderDAO, times(1)).getById(eq(1));
        verify(orderDAO, never()).update(any(Order.class));
    }
}
