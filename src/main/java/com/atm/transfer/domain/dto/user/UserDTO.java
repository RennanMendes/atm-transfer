package com.atm.transfer.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record UserDTO(
        @NotBlank
        String fullName,

        @NotBlank
        String cpf,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password
) {
}
