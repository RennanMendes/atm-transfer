package com.atm.transfer.api.controller;

import com.atm.transfer.domain.dto.transaction.TransactionDTO;
import com.atm.transfer.domain.dto.transaction.TransactionRequestDTO;
import com.atm.transfer.domain.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    @Transactional
    public ResponseEntity<TransactionDTO> transfer(@RequestBody @Valid TransactionRequestDTO transactionRequestDTO){
        TransactionDTO dto = transactionService.transfer(transactionRequestDTO);

        return ResponseEntity.ok(dto);
    }
}
