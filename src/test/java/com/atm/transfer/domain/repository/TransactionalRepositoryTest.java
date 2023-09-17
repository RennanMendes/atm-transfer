package com.atm.transfer.domain.repository;


import com.atm.transfer.domain.dto.user.UserDTO;
import com.atm.transfer.domain.enumerated.Type;
import com.atm.transfer.domain.model.Transaction;
import com.atm.transfer.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransactionalRepositoryTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    TransactionalRepository repository;

    @Test
    void shouldReturnNull_WhenUserInvalid() {
        User joao = createUser("João Victor", "25955654287", "joao@email.com");
        User pedro = createUser("João Pedro", "25955654289", "pedro@email.com");

        createTransfer(joao, pedro, new BigDecimal(200));
        createTransfer(joao, pedro, new BigDecimal(100));

        Transaction returnedTransaction = repository.findLastByUserId(pedro);

        assertThat(returnedTransaction).isNull();
    }

    @Test
    void shouldReturnLastTransaction_WhenUserIsValid() {
        User joao = createUser("João Victor", "25955654287", "joao@email.com");
        User pedro = createUser("João Pedro", "25955654289", "pedro@email.com");

        createTransfer(joao, pedro, new BigDecimal(200));
        Transaction lastTransaction = createTransfer(joao, pedro, new BigDecimal(100));

        Transaction returnedTransaction = repository.findLastByUserId(joao);

        assertThat(returnedTransaction).isEqualTo(lastTransaction);
    }

    private User createUser(String nome, String cpf, String email) {
        UserDTO dto = new UserDTO(nome, cpf, email, "1234");
        User user = new User(dto);
        em.persist(user);
        return user;
    }

    private Transaction createTransfer(User userId, User refId, BigDecimal total) {
        Transaction transaction = new Transaction(null, userId, refId, Type.ENTRADA, new BigDecimal(100), total);
        repository.save(transaction);
        return transaction;
    }

}