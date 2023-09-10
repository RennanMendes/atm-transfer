package com.atm.transfer.domain.dto.transaction;

import com.atm.transfer.domain.dto.user.TransactionUserDTO;
import com.atm.transfer.domain.enumerated.Type;
import com.atm.transfer.domain.model.Transaction;

import java.math.BigDecimal;

public record TransactionDTO(
        Long id,
        TransactionUserDTO payer,
        TransactionUserDTO payee,
        Type type,
        BigDecimal amount,
        BigDecimal total
) {

    public TransactionDTO(Transaction transaction) {
        this(transaction.getId(),
                new TransactionUserDTO(transaction.getUserId()),
                new TransactionUserDTO(transaction.getRefUserId()),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getTotal()
        );
    }
}
