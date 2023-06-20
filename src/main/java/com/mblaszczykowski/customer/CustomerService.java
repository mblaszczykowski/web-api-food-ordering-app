package com.mblaszczykowski.customer;

import com.mblaszczykowski.exception.DuplicateResourceException;
import com.mblaszczykowski.exception.NotValidResourceException;
import com.mblaszczykowski.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class CustomerService {
    private final CustomerDAO customerDAO;

    @Autowired
    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public Customer getCustomerById(Integer id) {
        return customerDAO.getCustomerById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer with id [%s] not found".formatted(id))
                );
    }

    public Customer getCustomerByEmail(String email) {
        return customerDAO.getCustomerByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer with email [%s] not found".formatted(email))
                );
    }

    private void checkEmailExists(String email) {
        if (customerDAO.existsCustomerWithEmail(email)) {
            throw new DuplicateResourceException("Email already taken");
        }
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        if (customerRegistrationRequest.firstname() == null || customerRegistrationRequest.lastname() == null || customerRegistrationRequest.email() == null) {
            throw new NotValidResourceException("Missing data");
        }

        String firstname = customerRegistrationRequest.firstname();
        String lastname = customerRegistrationRequest.lastname();
        String email = customerRegistrationRequest.email();
        checkEmailExists(email);
        String address = customerRegistrationRequest.address();
        String phoneNumber = customerRegistrationRequest.phoneNumber();

        Customer customer = new Customer(firstname, lastname, email, address, phoneNumber);
        customerDAO.addCustomer(customer);
    }

    public void deleteCustomer(Integer id) {
        Customer customer = customerDAO.getCustomerById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer with id [%s] not found".formatted(id))
                );

        customerDAO.deleteCustomer(customer);
    }

    private boolean checkEmailValid(String email, String emailRegex) {
        return Pattern.compile(emailRegex)
                .matcher(email)
                .matches();
    }

    public void updateCustomer(Integer id, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = customerDAO.getCustomerById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer with id [%s] not found".formatted(id))
                );

        if (customerUpdateRequest.firstname() != null) {
            String firstname = customerUpdateRequest.firstname();
            customer.setFirstname(firstname);
        }

        if (customerUpdateRequest.lastname() != null) {
            String lastname = customerUpdateRequest.lastname();
            customer.setLastname(lastname);
        }

        if (customerUpdateRequest.email() != null && !customer.getEmail().equals(customerUpdateRequest.email())) {
            String email = customerUpdateRequest.email();
            checkEmailExists(email);
            if (!checkEmailValid(email, "^(.+)@(\\S+)$")) {
                throw new NotValidResourceException("Email not valid");
            }
            customer.setEmail(email);
        }

        if (customerUpdateRequest.address() != null) {
            String address = customerUpdateRequest.address();
            customer.setAddress(address);
        }

        if (customerUpdateRequest.phoneNumber() != null) {
            String phoneNumber = customerUpdateRequest.phoneNumber();
            customer.setPhoneNumber(phoneNumber);
        }

        customerDAO.updateCustomer(customer);
    }
}
