package com.atm.transfer.domain.validations;

import com.atm.transfer.domain.dto.transaction.TransactionRequestDTO;

public interface TransactionalValidator {
    void validate(TransactionRequestDTO transactionRequestDTO);
}
