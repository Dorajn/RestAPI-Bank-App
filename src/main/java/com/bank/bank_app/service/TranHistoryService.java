package com.bank.bank_app.service;

import com.bank.bank_app.model.Transfers;
import com.bank.bank_app.model.Users;
import com.bank.bank_app.repo.TransfersRepo;
import com.bank.bank_app.repo.UserRepo;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranHistoryService {

    private TransfersRepo transfersRepo;
    private UserRepo userRepo;

    public TranHistoryService(UserRepo userRepo, TransfersRepo transfersRepo) {
        this.userRepo = userRepo;
        this.transfersRepo = transfersRepo;
    }

    public ResponseEntity<List<Transfers>> getTransactionsHistory(int id) {
        if(userRepo.existsById(id)){
            return new ResponseEntity<>(
                    transfersRepo.findTransactionsByUserId(id),
                    HttpStatus.OK
            );
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Transfers>> getLoadsInTransactionsHistory(int id) {
        if(userRepo.existsById(id)){
            return new ResponseEntity<>(
                    transfersRepo.findTLoadsInTransactionsByUserId(id),
                    HttpStatus.OK
            );
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Transfers>> getRecognitionInTransactionsHistory(int id) {
        if(userRepo.existsById(id)){
            return new ResponseEntity<>(
                    transfersRepo.findRecognitionInTransactionsByUserId(id),
                    HttpStatus.OK
            );
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
