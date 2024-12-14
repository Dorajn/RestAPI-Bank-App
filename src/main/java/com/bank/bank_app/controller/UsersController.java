package com.bank.bank_app.controller;

import com.bank.bank_app.model.TransactionRequest;
import com.bank.bank_app.model.Users;
import com.bank.bank_app.service.TransactionService;
import com.fasterxml.jackson.databind.deser.BuilderBasedDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import com.bank.bank_app.service.UserService;

import java.util.List;

@RestController()
@RequestMapping("/api")
public class UsersController {

    private UserService service;

    public UsersController(UserService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public ResponseEntity<?> greetBankUser(){
        return new ResponseEntity<>("Welcome on the bank website!", HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers(){
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Users> addUser(@RequestBody Users user){
        return new ResponseEntity<>(service.addUser(user), HttpStatus.OK);
    }

    @GetMapping("/users/{uid}/account")
    public ResponseEntity<?> getAccountById(@PathVariable int uid){
        Users user = service.getAccountById(uid);

        if(user != null){
            return new ResponseEntity<>(
                    String.format("[%s] Your balance: %s", user.getUsername(), user.getBalance()),
                    HttpStatus.OK
            );
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
