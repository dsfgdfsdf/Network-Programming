package com.example.demo.Controllers;

import com.example.demo.model.Customer;
import com.example.demo.Services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class FileUploadController {
    private final CustomerService customerService;

    public FileUploadController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(MultipartFile file, HttpServletResponse response) {
        try {
            if (file.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return "upload";
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            List<Customer> customers = List.of(mapper.readValue(file.getInputStream(), Customer[].class));


            customers.forEach(customerService::addCustomer);

            return "redirect:/customers";
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "upload";
        }
    }
}