package com.bank.bank_app.controller;

import com.bank.bank_app.model.TransactionRequest;
import com.bank.bank_app.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransactionsController {

    private TransactionService service;

    public TransactionsController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/users/{uid}/account/deposit")
    public ResponseEntity<?> depositMoney(@PathVariable int uid,
                                           @RequestBody TransactionRequest request){
        return service.depositMoney(uid, request);
    }

    @PostMapping("/users/{uid}/account/withdraw")
    public ResponseEntity<?> withdrawMoney(@PathVariable int uid,
                                           @RequestBody TransactionRequest request){
        return service.withdrawMoney(uid, request);
    }
}
