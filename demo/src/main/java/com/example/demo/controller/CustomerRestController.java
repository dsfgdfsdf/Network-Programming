package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.service.CustomerService;
import org.springframework.http.ResponseEntity;
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
    public List<Order> getOrdersByCustomerId(@PathVariable Long id) {
        return customerService.getOrdersByCustomerId(id);
    }

    @PostMapping("/{id}/order/new")
    public Order addOrderToCustomer(@PathVariable Long id, @RequestBody Order order) {
        return customerService.addOrderToCustomer(id, order);
    }

    @PutMapping("/{id}/order/{orderId}/edit")
    public Order updateOrder(
            @PathVariable Long id,
            @PathVariable Long orderId,
            @RequestBody Order updatedOrder) {
        return customerService.updateOrder(id, orderId, updatedOrder);
    }
}
