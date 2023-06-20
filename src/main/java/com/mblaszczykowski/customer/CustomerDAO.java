package com.mblaszczykowski.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    List<Customer> getAllCustomers();

    void addCustomer(Customer customer);

    Optional<Customer> getCustomerById(Integer id);

    Optional<Customer> getCustomerByEmail(String email);

    boolean existsCustomerWithEmail(String email);

    void deleteCustomer(Customer customer);

    void updateCustomer(Customer customer);

    Optional<Customer> getById(Integer customerId);

    boolean existsById(Integer customerId);
}
