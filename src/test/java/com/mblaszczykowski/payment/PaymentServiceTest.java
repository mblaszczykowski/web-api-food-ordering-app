package com.mblaszczykowski.payment;

import com.mblaszczykowski.exception.NotValidResourceException;
import com.mblaszczykowski.exception.ResourceNotFoundException;
import com.mblaszczykowski.order.Order;
import com.mblaszczykowski.order.OrderDAO;
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
class PaymentServiceTest {
    private PaymentService paymentService;

    @Mock
    private PaymentDAO paymentDAO;

    @Mock
    private OrderDAO orderDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService(orderDAO, paymentDAO);
    }

    @Test
    void testGetById_ExistingPayment_ReturnsPayment() {
        // Arrange
        Integer paymentId = 1;
        Payment payment = new Payment();
        payment.setId(paymentId);
        when(paymentDAO.getById(paymentId)).thenReturn(Optional.of(payment));

        // Act
        Payment result = paymentService.getById(paymentId);

        // Assert
        assertNotNull(result);
        assertEquals(paymentId, result.getId());
        verify(paymentDAO, times(1)).getById(paymentId);
    }

    @Test
    void testGetById_NonExistingPayment_ThrowsResourceNotFoundException() {
        // Arrange
        Integer paymentId = 1;
        when(paymentDAO.getById(paymentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> paymentService.getById(paymentId));
        verify(paymentDAO, times(1)).getById(paymentId);
    }

    @Test
    void testGetAll_ReturnsAllPayments() {
        // Arrange
        List<Payment> payments = List.of(new Payment(), new Payment());
        when(paymentDAO.getAll()).thenReturn(payments);

        // Act
        List<Payment> result = paymentService.getAll();

        // Assert
        assertEquals(payments.size(), result.size());
        assertEquals(payments, result);
        verify(paymentDAO, times(1)).getAll();
    }

    @Test
    void testAdd_ValidPaymentRegistrationRequest_AddsPayment() {
        // Arrange
        Order order = new Order();
        order.setId(1);
        PaymentRegistrationRequest registrationRequest = new PaymentRegistrationRequest(order, Payment.PaymentMethod.BLIK);
        when(orderDAO.getById(order.getId())).thenReturn(Optional.of(order));
        when(paymentDAO.existsByOrderId(order.getId())).thenReturn(false);

        // Act
        assertDoesNotThrow(() -> paymentService.add(registrationRequest));

        // Assert
        verify(orderDAO, times(1)).getById(order.getId());
        verify(paymentDAO, times(1)).existsByOrderId(order.getId());
        verify(paymentDAO, times(1)).add(any(Payment.class));
        assertTrue(order.isPaid());
    }

    @Test
    void testAdd_MissingOrderInPaymentRegistrationRequest_ThrowsNotValidResourceException() {
        // Arrange
        PaymentRegistrationRequest registrationRequest = new PaymentRegistrationRequest(null, Payment.PaymentMethod.BLIK);

        // Act & Assert
        assertThrows(NotValidResourceException.class, () -> paymentService.add(registrationRequest));
        verify(orderDAO, never()).getById(anyInt());
        verify(paymentDAO, never()).existsByOrderId(anyInt());
        verify(paymentDAO, never()).add(any(Payment.class));
    }

    @Test
    void testAdd_PaymentForOrderAlreadyExists_ThrowsNotValidResourceException() {
        // Arrange
        Integer orderId = 1;
        Order order = new Order();
        order.setId(orderId);
        PaymentRegistrationRequest registrationRequest = new PaymentRegistrationRequest(order, Payment.PaymentMethod.BLIK);
        when(orderDAO.getById(orderId)).thenReturn(Optional.of(order));
        when(paymentDAO.existsByOrderId(orderId)).thenReturn(true);

        // Act & Assert
        assertThrows(NotValidResourceException.class, () -> paymentService.add(registrationRequest));
        verify(orderDAO, times(1)).getById(orderId);
        verify(paymentDAO, times(1)).existsByOrderId(orderId);
        verify(paymentDAO, never()).add(any(Payment.class));
    }

    @Test
    void testUpdatePayment_ValidPaymentUpdateRequest_UpdatesPayment() {
        // Arrange
        Integer paymentId = 1;
        Payment existingPayment = new Payment();
        existingPayment.setId(paymentId);
        PaymentUpdateRequest updateRequest = new PaymentUpdateRequest(Payment.PaymentStatus.PAID);
        when(paymentDAO.getById(paymentId)).thenReturn(Optional.of(existingPayment));

        // Act
        assertDoesNotThrow(() -> paymentService.updatePayment(paymentId, updateRequest));

        // Assert
        verify(paymentDAO, times(1)).getById(paymentId);
        verify(paymentDAO, times(1)).update(existingPayment);
        assertEquals(updateRequest.paymentStatus(), existingPayment.getPaymentStatus());
    }

    @Test
    void testUpdatePayment_NonExistingPayment_ThrowsResourceNotFoundException() {
        // Arrange
        Integer paymentId = 1;
        PaymentUpdateRequest updateRequest = new PaymentUpdateRequest(Payment.PaymentStatus.PAID);
        when(paymentDAO.getById(paymentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> paymentService.updatePayment(paymentId, updateRequest));
        verify(paymentDAO, times(1)).getById(paymentId);
        verify(paymentDAO, never()).update(any(Payment.class));
    }
}
