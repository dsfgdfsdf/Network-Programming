package petrenko.api_transactional.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import petrenko.api_transactional.Entity.Transaction;
import petrenko.api_transactional.Entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.user = :user AND t.date BETWEEN :startDate AND :endDate AND (:type IS NULL OR t.type = :type)")
    List<Transaction> findTransactions(
            @Param("user") User user,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("type") String type
    );
}
