package com.bank.bank_app.controller;

import com.bank.bank_app.model.Transfers;
import com.bank.bank_app.service.TranHistoryService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TranHistoryController {

    private TranHistoryService service;

    public TranHistoryController(TranHistoryService service) {
        this.service = service;
    }

    @GetMapping("/users/{uid}/account/history")
    public ResponseEntity<List<Transfers>> getTransactionsHistory(@PathVariable int uid){
        return service.getTransactionsHistory(uid);
    }
}
