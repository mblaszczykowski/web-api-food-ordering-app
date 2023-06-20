package com.mblaszczykowski.customer;

import com.mblaszczykowski.exception.DuplicateResourceException;
import com.mblaszczykowski.exception.NotValidResourceException;
import com.mblaszczykowski.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(
                new Customer("John", "Doe", "john@example.com", "123 Main St", "123456789"),
                new Customer("Jane", "Smith", "jane@example.com", "456 Elm St", "987654321")
        );

        when(customerDAO.getAllCustomers()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(customers.size(), result.size());
        assertEquals(customers.get(0), result.get(0));
        assertEquals(customers.get(1), result.get(1));

        verify(customerDAO, times(1)).getAllCustomers();
    }

    @Test
    void testGetCustomerById() {
        Customer customer = new Customer("John", "Doe", "john@example.com", "123 Main St", "123456789");

        when(customerDAO.getCustomerById(anyInt())).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(1);

        assertEquals(customer, result);

        verify(customerDAO, times(1)).getCustomerById(anyInt());
    }

    @Test
    void testGetCustomerByIdNotFound() {
        when(customerDAO.getCustomerById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(1));

        verify(customerDAO, times(1)).getCustomerById(anyInt());
    }

    @Test
    void testGetCustomerByEmail() {
        Customer customer = new Customer("John", "Doe", "john@example.com", "123 Main St", "123456789");

        when(customerDAO.getCustomerByEmail(anyString())).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerByEmail("john@example.com");

        assertEquals(customer, result);

        verify(customerDAO, times(1)).getCustomerByEmail(anyString());
    }

    @Test
    void testGetCustomerByEmailNotFound() {
        when(customerDAO.getCustomerByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerByEmail("john@example.com"));

        verify(customerDAO, times(1)).getCustomerByEmail(anyString());
    }

    @Test
    void testAddCustomer() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest(
                "John", "Doe", "john@example.com", "123 Main St", "123456789"
        );

        customerService.addCustomer(registrationRequest);

        verify(customerDAO, times(1)).existsCustomerWithEmail(eq("john@example.com"));
        verify(customerDAO, times(1)).addCustomer(any(Customer.class));
    }

    @Test
    void testAddCustomerInvalidData() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest(
                null, null, null, null, null
        );

        assertThrows(NotValidResourceException.class, () -> customerService.addCustomer(registrationRequest));

        verify(customerDAO, never()).existsCustomerWithEmail(anyString());
        verify(customerDAO, never()).addCustomer(any(Customer.class));
    }

    @Test
    void testAddCustomerEmailAlreadyTaken() {
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest(
                "John", "Doe", "john@example.com", "123 Main St", "123456789"
        );

        when(customerDAO.existsCustomerWithEmail(anyString())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> customerService.addCustomer(registrationRequest));

        verify(customerDAO, times(1)).existsCustomerWithEmail(eq("john@example.com"));
        verify(customerDAO, never()).addCustomer(any(Customer.class));
    }

    @Test
    void testDeleteCustomer() {
        Customer customer = new Customer("John", "Doe", "john@example.com", "123 Main St", "123456789");

        when(customerDAO.getCustomerById(anyInt())).thenReturn(Optional.of(customer));

        customerService.deleteCustomer(1);

        verify(customerDAO, times(1)).getCustomerById(anyInt());
        verify(customerDAO, times(1)).deleteCustomer(eq(customer));
    }

    @Test
    void testDeleteCustomerNotFound() {
        when(customerDAO.getCustomerById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(1));

        verify(customerDAO, times(1)).getCustomerById(anyInt());
        verify(customerDAO, never()).deleteCustomer(any(Customer.class));
    }

    @Test
    void testUpdateCustomer() {
        Customer customer = new Customer("John", "Doe", "john@example.com", "123 Main St", "123456789");
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "Jane", "Smith", "jane@example.com", "456 Elm St", "987654321"
        );

        when(customerDAO.getCustomerById(anyInt())).thenReturn(Optional.of(customer));
        when(customerDAO.existsCustomerWithEmail(anyString())).thenReturn(false);

        customerService.updateCustomer(1, updateRequest);

        verify(customerDAO, times(1)).getCustomerById(anyInt());
        verify(customerDAO, times(1)).existsCustomerWithEmail(eq("jane@example.com"));
        verify(customerDAO, times(1)).updateCustomer(eq(customer));
    }

    @Test
    void testUpdateCustomerNotFound() {
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "Jane", "Smith", "jane@example.com", "456 Elm St", "987654321"
        );

        when(customerDAO.getCustomerById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(1, updateRequest));

        verify(customerDAO, times(1)).getCustomerById(anyInt());
        verify(customerDAO, never()).existsCustomerWithEmail(anyString());
        verify(customerDAO, never()).updateCustomer(any(Customer.class));
    }

    @Test
    void testUpdateCustomerEmailAlreadyTaken() {
        Customer customer = new Customer("John", "Doe", "john@example.com", "123 Main St", "123456789");
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "Jane", "Smith", "jane@example.com", "456 Elm St", "987654321"
        );

        when(customerDAO.getCustomerById(anyInt())).thenReturn(Optional.of(customer));
        when(customerDAO.existsCustomerWithEmail(anyString())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> customerService.updateCustomer(1, updateRequest));

        verify(customerDAO, times(1)).getCustomerById(anyInt());
        verify(customerDAO, times(1)).existsCustomerWithEmail(eq("jane@example.com"));
        verify(customerDAO, never()).updateCustomer(any(Customer.class));
    }
}
