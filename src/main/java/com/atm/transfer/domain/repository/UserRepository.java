package com.atm.transfer.domain.repository;

import com.atm.transfer.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
