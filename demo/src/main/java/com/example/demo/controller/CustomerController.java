package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/all")
    @ResponseBody //
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }


    @GetMapping("/{id}/order/all")
    @ResponseBody
    public List<Order> getCustomerOrders(@PathVariable Long id) {
        return customerService.getOrdersByCustomerId(id);
    }


    @PostMapping("/{id}/order/new")
    @ResponseBody
    public Order addOrder(@PathVariable Long id, @RequestBody Order order) {
        return customerService.addOrderToCustomer(id, order);
    }


    @PutMapping("/{id}/order/{orderId}/edit")
    @ResponseBody
    public Order updateOrder(@PathVariable Long id, @PathVariable Long orderId, @RequestBody Order updatedOrder) {
        return customerService.updateOrder(id, orderId, updatedOrder);
    }


    @GetMapping("/highlight")
    public String getHighlightedCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customer_highlight";
    }

}

