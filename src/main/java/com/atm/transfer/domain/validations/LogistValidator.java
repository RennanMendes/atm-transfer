package com.atm.transfer.domain.validations;

import com.atm.transfer.domain.dto.transaction.TransactionRequestDTO;
import com.atm.transfer.domain.enumerated.UserType;
import com.atm.transfer.domain.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogistValidator implements TransactionalValidator {

    @Autowired
    private UserRepository userRepository;

    public void validate(TransactionRequestDTO transactionRequestDTO) {
        var user = userRepository.findById(transactionRequestDTO.payerId()).get();

        if (user.getUserType() == UserType.LOGIST) {
            throw new ValidationException("Logista não realiza transferência!");
        }
    }

}
