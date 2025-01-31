package petrenko.api_transactional.Dto;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private Double amount;
    private String type;
    private LocalDateTime date;
    private Long userId;

    public TransactionDTO(Long id, Double amount, String type, LocalDateTime date, Long userId) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getUserId() {
        return userId;
    }
}
