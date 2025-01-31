package petrenko.api_transactional.Mapper;

import petrenko.api_transactional.Dto.TransactionDTO;
import petrenko.api_transactional.Dto.UserDTO;
import petrenko.api_transactional.Entity.Transaction;
import petrenko.api_transactional.Entity.User;

public class DTOMapper {

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }

    public static TransactionDTO toTransactionDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getDate(),
                transaction.getUser().getId()
        );
    }
}
