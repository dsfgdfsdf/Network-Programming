package com.example.demo.Services;
import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final List<Customer> customers = new ArrayList<>();

    public List<Customer> getAllCustomers() {
        return customers;
    }

    public Customer getCustomerById(int id) {
        return customers.stream()
                .filter(customer -> customer.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addCustomer(Customer customer) {
        if (getCustomerById(customer.getId()) == null) {
            customers.add(customer);
        }
    }


    public void addOrder(int customerId, Order order) {
        Customer customer = getCustomerById(customerId);
        if (customer != null) {
            customer.getOrders().add(order);
        }
    }
    public void loadCustomersFromJson(String filePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Customer> loadedCustomers = List.of(mapper.readValue(new File(filePath), Customer[].class));
        customers.addAll(loadedCustomers);
    }
}
