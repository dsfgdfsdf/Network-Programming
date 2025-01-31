package petrenko.api_transactional.Controllers;


import org.springframework.web.bind.annotation.*;
import petrenko.api_transactional.Entity.Transaction;
import petrenko.api_transactional.Entity.User;
import petrenko.api_transactional.Service.TransactionService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> getTransactions(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) String type) {

        Optional<User> user = transactionService.authenticate(username, password);
        if (user.isEmpty()) {
            throw new RuntimeException("Invalid credentials");
        }

        // Конвертація рядків у LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);

        return transactionService.getTransactions(user.get(), start, end, type);
    }
}