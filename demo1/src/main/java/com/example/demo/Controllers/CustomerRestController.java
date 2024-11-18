package com.example.demo.Controllers;

import com.example.demo.Services.CustomerService;
import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {
    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}/order/all")
    public List<Order> getCustomerOrders(@PathVariable int id) {
        Customer customer = customerService.getCustomerById(id);
        return customer != null ? customer.getOrders() : null;
    }

    @PostMapping("/{id}/order/new")
    public void addOrderToCustomer(@PathVariable int id, @RequestBody Order order) {
        customerService.addOrder(id, order);
    }
}