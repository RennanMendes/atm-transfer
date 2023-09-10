package com.atm.transfer.domain.repository;

import com.atm.transfer.domain.model.Transaction;
import com.atm.transfer.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TransactionalRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.userId = :userId ORDER BY t.id DESC LIMIT 1")
    Transaction findLastByUserId(@Param("userId") User payerId);
}
