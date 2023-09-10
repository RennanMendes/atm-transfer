package com.atm.transfer.domain.service;

import com.atm.transfer.domain.dto.transaction.TransactionDTO;
import com.atm.transfer.domain.dto.transaction.TransactionRequestDTO;
import com.atm.transfer.domain.enumerated.Type;
import com.atm.transfer.domain.model.Transaction;
import com.atm.transfer.domain.model.User;
import com.atm.transfer.domain.repository.TransactionalRepository;
import com.atm.transfer.domain.repository.UserRepository;
import com.atm.transfer.domain.validations.TransactionalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionalRepository transactionalRepository;
    private final UserRepository userRepository;
    private final List<TransactionalValidator> validators;

    @Autowired
    public TransactionService(TransactionalRepository transactionalRepository, UserRepository userRepository, List<TransactionalValidator> validators) {
        this.transactionalRepository = transactionalRepository;
        this.userRepository = userRepository;
        this.validators = validators;
    }

    @Transactional
    public TransactionDTO transfer(TransactionRequestDTO transactionRequestDTO) {

        validators.forEach(v -> v.validate(transactionRequestDTO));

        User payerUser = userRepository.findById(transactionRequestDTO.payerId()).get();
        User payeeUser = userRepository.findById(transactionRequestDTO.payeeId()).get();

        Transaction payerTransaction = createTransaction(payerUser, payeeUser, Type.SAIDA, transactionRequestDTO);
        Transaction payeeTransaction = createTransaction(payeeUser, payerUser, Type.ENTRADA, transactionRequestDTO);

        this.transactionalRepository.save(payerTransaction);
        this.transactionalRepository.save(payeeTransaction);

        return new TransactionDTO(payerTransaction);
    }

    public Transaction createTransaction(User user, User userRef, Type type, TransactionRequestDTO transactionRequestDTO) {
        Transaction lastTransaction = transactionalRepository.findLastByUserId(user);
        BigDecimal total = calculateTotal(lastTransaction, transactionRequestDTO, type);

        return new Transaction(user, userRef, type, transactionRequestDTO.value(), total);
    }

    public BigDecimal calculateTotal(Transaction lastTransaction, TransactionRequestDTO transactionRequestDTO, Type type) {
        if (type == Type.ENTRADA) {
            return lastTransaction.calculatePayeeTotal(transactionRequestDTO.value());
        }

        return lastTransaction.calculatePayerTotal(transactionRequestDTO.value());
    }

}
