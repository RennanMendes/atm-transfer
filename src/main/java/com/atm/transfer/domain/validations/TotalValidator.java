package com.atm.transfer.domain.validations;

import com.atm.transfer.domain.dto.transaction.TransactionRequestDTO;
import com.atm.transfer.domain.repository.TransactionalRepository;
import com.atm.transfer.domain.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TotalValidator implements TransactionalValidator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionalRepository transactionalRepository;

    public void validate(TransactionRequestDTO transactionRequestDTO) {
        var user = userRepository.findById(transactionRequestDTO.payerId()).get();
        var lastTransaction = transactionalRepository.findLastByUserId(user);

        if (transactionRequestDTO.value().compareTo(lastTransaction.getTotal()) >= 0) {
            throw new ValidationException("O usuário não possui saldo sulficiente!");
        }
    }
}
