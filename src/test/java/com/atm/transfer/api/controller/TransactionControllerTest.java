package com.atm.transfer.api.controller;

import com.atm.transfer.domain.dto.transaction.TransactionDTO;
import com.atm.transfer.domain.dto.transaction.TransactionRequestDTO;
import com.atm.transfer.domain.dto.user.TransactionUserDTO;
import com.atm.transfer.domain.enumerated.Type;
import com.atm.transfer.domain.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;


@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    private static final String BASE_URL = "/transaction";
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    TransactionService transactionService;

    @Autowired
    public TransactionControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void shouldReturnStatus400_WhenTransactionInvalid() throws Exception {
        ResultActions payerNull = createTransaction(null, 2L, BigDecimal.TEN);
        ResultActions payeeNull = createTransaction(1L, null, BigDecimal.TEN);
        ResultActions valueNull = createTransaction(1L, 2L, null);

        assertEquals(HttpStatus.BAD_REQUEST.value(), payerNull.andReturn().getResponse().getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.value(), payeeNull.andReturn().getResponse().getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.value(), valueNull.andReturn().getResponse().getStatus());
    }

    @Test
    void shouldReturnStatus200_WhenTransactionIsValid() throws Exception {
        TransactionDTO expectedDTO = createTrasactionDTO();
        when(transactionService.transfer(any())).thenReturn(expectedDTO);

        ResultActions resultActions = createTransaction(1L, 2L, BigDecimal.TEN);
        String jsonResponse = resultActions.andReturn().getResponse().getContentAsString();

        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
        assertEquals(objectMapper.writeValueAsString(expectedDTO), jsonResponse);
    }

    private ResultActions createTransaction(Long payerId, Long payeeId, BigDecimal value) throws Exception {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(payerId, payeeId, value);

        return mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO))
        );
    }

    private TransactionDTO createTrasactionDTO() {
        return new TransactionDTO(
                3L,
                new TransactionUserDTO(1L, "User", "52988752625"),
                new TransactionUserDTO(2L, "User Teste", "52988752626"),
                Type.SAIDA,
                new BigDecimal(100),
                new BigDecimal(100));
    }

}