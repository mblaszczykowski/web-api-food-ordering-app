package com.mblaszczykowski.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("{id}")
    public Customer getCustomer(@PathVariable("id") Integer id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("email/{email}")
    public Customer getCustomerByEmail(@PathVariable("email") String email) {
        return customerService.getCustomerByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustomer(@RequestBody CustomerRegistrationRequest request) {
        customerService.addCustomer(request);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") Integer id) {
        customerService.deleteCustomer(id);
    }

    @PutMapping("{id}")
    public void updateCustomer(
            @PathVariable("id") Integer id,
            @RequestBody CustomerUpdateRequest request
    ) {
        customerService.updateCustomer(id, request);
    }
}
