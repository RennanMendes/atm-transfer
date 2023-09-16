package com.atm.transfer.domain.service;

import com.atm.transfer.domain.dto.UserDTO;
import com.atm.transfer.domain.model.User;
import com.atm.transfer.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public User create(UserDTO userDTO) {
        User newUser = new User(userDTO);
        User user = save(newUser);

        return user;
    }

    private User save(User newUser) {
        User user = repository.save(newUser);
        return user;
    }

    public User findUserById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(()->new Exception("Usuario não encontrado"));
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void delete(Long id) throws Exception {
        User user = findUserById(id);
        user.setActiveStatus(false);
        save(user);
    }
}
