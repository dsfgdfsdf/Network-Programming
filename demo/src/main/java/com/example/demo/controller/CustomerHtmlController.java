package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerHtmlController {

    private final CustomerService customerService;

    public CustomerHtmlController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/html")
    public String getAllCustomersAndOrders(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customer_list";
    }
    @GetMapping("/upload")
    public String uploadPage() {
        return "upload_json";
    }

    @PostMapping("/upload")
    public String uploadJsonFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            Customer[] customers = objectMapper.readValue(file.getInputStream(), Customer[].class);

            for (Customer customer : customers) {
                for (Order order : customer.getOrders()) {

                    order.setCustomer(customer);
                }

                customerService.saveCustomer(customer);
            }

            model.addAttribute("message", "Файл успішно завантажено!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Помилка завантаження файлу: " + e.getMessage());
        }
        return "upload_json";
    }
}
