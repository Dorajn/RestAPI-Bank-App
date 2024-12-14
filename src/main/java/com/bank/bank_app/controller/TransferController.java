package com.bank.bank_app.controller;

import com.bank.bank_app.model.TransferRequest;
import com.bank.bank_app.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransferController {

    private TransferService service;

    public TransferController(TransferService service) {
        this.service = service;
    }

    @PostMapping("users/{uid}/account/transfer")
    public ResponseEntity<?> transferMoney(@PathVariable int uid,
                                           @RequestBody TransferRequest request){
        return service.transferMoney(uid, request);
    }
}
