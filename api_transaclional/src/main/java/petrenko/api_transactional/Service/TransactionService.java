package petrenko.api_transactional.Service;

import org.springframework.stereotype.Service;
import petrenko.api_transactional.Entity.Transaction;
import petrenko.api_transactional.Entity.User;
import petrenko.api_transactional.Repository.TransactionRepository;
import petrenko.api_transactional.Repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Optional<User> authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password)); // НЕ БЕЗПЕЧНО (без шифрування)
    }

    public List<Transaction> getTransactions(User user, LocalDateTime startDate, LocalDateTime endDate, String type) {
        return transactionRepository.findTransactions(user, startDate, endDate,
                (type == null || type.isEmpty()) ? null : type);
    }
}
