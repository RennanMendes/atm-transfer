package com.atm.transfer.api.controller;

import com.atm.transfer.domain.dto.UserDTO;
import com.atm.transfer.domain.model.User;
import com.atm.transfer.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<User> register(@RequestBody @Valid UserDTO userDTO, UriComponentsBuilder uriBuilder) {
        User user = userService.create(userDTO);
        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @GetMapping()
    public ResponseEntity<List<User>> findAll() throws Exception {
        List<User> user = userService.findAll();

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) throws Exception {
        User user = userService.findUserById(id);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id) throws Exception {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
