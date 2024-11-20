package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    // 1. Отримати всіх замовників
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAllWithOrders();

        // Розрахунок суми замовлень для кожного клієнта
        customers.forEach(customer -> {
            BigDecimal totalAmount = customer.getOrders().stream()
                    .map(order -> BigDecimal.valueOf(order.getAmount())) // Сума з бази
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            customer.setMonthlyOrderSum(totalAmount);
        });

        // Знаходимо максимальну суму замовлень
        BigDecimal maxMonthlySum = customers.stream()
                .map(Customer::getMonthlyOrderSum)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        // Встановлюємо прапори для маркування клієнтів
        customers.forEach(customer -> {
            customer.setMaxOrderCustomer(customer.getMonthlyOrderSum().compareTo(maxMonthlySum) == 0 && maxMonthlySum.compareTo(BigDecimal.ZERO) > 0);
            customer.setNoOrdersThisMonth(customer.getMonthlyOrderSum().compareTo(BigDecimal.ZERO) == 0);

            System.out.println("Customer: " + customer.getName() +
                    ", Monthly Order Sum: " + customer.getMonthlyOrderSum() +
                    ", Max Order: " + customer.isMaxOrderCustomer() +
                    ", No Orders: " + customer.isNoOrdersThisMonth());
        });


        return customers;
    }

    // 2. Отримати всі замовлення для певного замовника
    public List<Order> getOrdersByCustomerId(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.map(Customer::getOrders).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    // 3. Додати нове замовлення для певного замовника
    public Order addOrderToCustomer(Long customerId, Order order) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        order.setCustomer(customer);
        return orderRepository.save(order);
    }

    // 4. Оновити існуюче замовлення
    public Order updateOrder(Long customerId, Long orderId, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Перевірка, чи замовлення належить замовнику
        if (!existingOrder.getCustomer().getId().equals(customerId)) {
            throw new RuntimeException("Order does not belong to the specified customer");
        }

        // Оновлення полів
        existingOrder.setOrderDate(updatedOrder.getOrderDate());
        existingOrder.setAmount(updatedOrder.getAmount());
        existingOrder.setPaymentMethod(updatedOrder.getPaymentMethod());


        return orderRepository.save(existingOrder);
    }
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
