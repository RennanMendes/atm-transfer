package com.atm.transfer.domain.service;

import com.atm.transfer.domain.dto.transaction.TransactionDTO;
import com.atm.transfer.domain.dto.transaction.TransactionRequestDTO;
import com.atm.transfer.domain.dto.user.TransactionUserDTO;
import com.atm.transfer.domain.enumerated.Type;
import com.atm.transfer.domain.enumerated.UserType;
import com.atm.transfer.domain.model.Transaction;
import com.atm.transfer.domain.model.User;
import com.atm.transfer.domain.repository.TransactionalRepository;
import com.atm.transfer.domain.repository.UserRepository;
import com.atm.transfer.domain.validations.TransactionalValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private List<TransactionalValidator> validators;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionalRepository transactionalRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(transactionalRepository, userRepository, validators);
    }

    @Test
    void shouldReturn200_WhenValidTransfer() {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(1L, 2L, new BigDecimal(300));
        User payer = createUser(1L,"Payer", "52956225852", "payer@email.com");
        User payee = createUser(2L,"Payee", "52956225853", "payee@email.com");
        Transaction lastTransaction = createTransaction(payer, payee);
        TransactionDTO dtoExpected = createTransactionDTO();

        when(userRepository.findById(1L)).thenReturn(Optional.of(payer));
        when(userRepository.findById(2L)).thenReturn(Optional.of(payee));
        when(transactionalRepository.findLastByUserId(any())).thenReturn(lastTransaction);

        TransactionDTO transactionDTO = transactionService.transfer(requestDTO);
        assertEquals(dtoExpected, transactionDTO);
    }

    private User createUser(Long id,String fullName, String cpf, String email) {
        return new User(id,fullName, cpf, email,"1234", UserType.STANDARD,true);
    }

    private Transaction createTransaction(User payer, User payee) {
        return new Transaction(1L, payer, payee, Type.SAIDA, new BigDecimal(100), new BigDecimal(200));
    }

    private TransactionDTO createTransactionDTO() {
        return new TransactionDTO(
                null,
                new TransactionUserDTO(1L, "Payer", "52956225852"),
                new TransactionUserDTO(2L, "Payee", "52956225853"),
                Type.SAIDA,
                new BigDecimal(100),
                new BigDecimal(100));
    }

}