package com.atm.transfer.domain.dto.user;

import com.atm.transfer.domain.model.User;

public record TransactionUserDTO(
        Long id,
        String fullName,
        String cpf) {

    public TransactionUserDTO(User user) {
        this(
                user.getId(),
                user.getFullName(),
                user.getCpf());
    }
}
