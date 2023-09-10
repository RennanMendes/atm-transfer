package com.atm.transfer.domain.validations;

import com.atm.transfer.domain.dto.transaction.TransactionRequestDTO;
import com.atm.transfer.domain.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivePayeeValidator implements TransactionalValidator {

    @Autowired
    private UserRepository userRepository;

    public void validate(TransactionRequestDTO transactionRequestDTO) {
        if (!userRepository.existsById(transactionRequestDTO.payeeId())) {
            throw new ValidationException("Beneficiario não encontrado!");
        }
    }
}
