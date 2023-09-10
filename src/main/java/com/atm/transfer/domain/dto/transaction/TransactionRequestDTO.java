package com.atm.transfer.domain.dto.transaction;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionRequestDTO(
        @NotNull
        Long payerId,

        @NotNull
        Long payeeId,

        @NotNull
        BigDecimal value) {
}
