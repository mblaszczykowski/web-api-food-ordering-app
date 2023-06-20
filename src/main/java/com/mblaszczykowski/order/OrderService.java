package com.mblaszczykowski.order;

import com.mblaszczykowski.customer.Customer;
import com.mblaszczykowski.customer.CustomerDAO;
import com.mblaszczykowski.exception.NotValidResourceException;
import com.mblaszczykowski.exception.ResourceNotFoundException;
import com.mblaszczykowski.food.Food;
import com.mblaszczykowski.food.FoodDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderDAO orderDAO;
    private final FoodDAO foodDAO;
    private final CustomerDAO customerDAO;


    public OrderService(@Qualifier("order-jpa") OrderDAO orderDAO, FoodDAO foodDAO, CustomerDAO customerDAO) {
        this.orderDAO = orderDAO;
        this.foodDAO = foodDAO;
        this.customerDAO = customerDAO;
    }

    public Order getById(Integer id) {
        return orderDAO.getById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order with id [%s] not found".formatted(id))
                );
    }

    public List<Order> getAll() {
        return orderDAO.getAll();
    }

    public List<Order> getOrdersByCustomerId(Integer customerId) {
        return orderDAO.getOrdersByCustomerId(customerId);
    }

    public List<Order> getOrdersByRestaurantId(Integer restaurantId) {
        return orderDAO.getOrdersByRestaurantId(restaurantId);
    }

    public void add(OrderRegistrationRequest orderRegistrationRequest) {
        if (orderRegistrationRequest.customer() == null || orderRegistrationRequest.foods() == null ||
                orderRegistrationRequest.address() == null || orderRegistrationRequest.deliveryType() == null) {
            throw new NotValidResourceException("Missing data");
        }

        Integer customerId = orderRegistrationRequest.customer().getId();
        Customer customer = customerDAO.getById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id [%s] not found".formatted(customerId)));

        List<Food> foods = new ArrayList<>();
        for (Food food : orderRegistrationRequest.foods()) {
            Integer foodId = food.getId();
            Food foodFromDb = foodDAO.getById(foodId)
                    .orElseThrow(() -> new ResourceNotFoundException("Food with id [%s] not found".formatted(foodId)));
            foods.add(foodFromDb);
        }

        var address = orderRegistrationRequest.address();
        var deliveryType = orderRegistrationRequest.deliveryType();

        Order order = new Order(customer, foods, address, deliveryType);
        orderDAO.add(order);
    }


    public void updateOrder(Integer id, OrderUpdateRequest orderUpdateRequest) {
        Order order = getById(id);

        if (orderUpdateRequest.foods() != null) {
            order.setFoods(orderUpdateRequest.foods());
        }

        if (orderUpdateRequest.address() != null) {
            order.setAddress(orderUpdateRequest.address());
        }

        if (orderUpdateRequest.deliveryType() != null) {
            order.setDeliveryType(orderUpdateRequest.deliveryType());
        }

        if (orderUpdateRequest.status() != null) {
            order.setStatus(orderUpdateRequest.status());
        }

        orderDAO.update(order);
    }
}