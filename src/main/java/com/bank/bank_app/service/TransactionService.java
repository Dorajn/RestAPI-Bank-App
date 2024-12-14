package com.bank.bank_app.service;

import com.bank.bank_app.model.TransactionRequest;
import com.bank.bank_app.model.Users;
import com.bank.bank_app.repo.UserRepo;
import org.apache.catalina.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.geom.RectangularShape;

@Service
public class TransactionService {

    private UserRepo repo;

    public TransactionService(UserRepo repo) {
        this.repo = repo;
    }

    public ResponseEntity<?> depositMoney(int userId, TransactionRequest request){
        if(repo.existsById(userId)){
            Users user = repo.findById(userId).orElseThrow();

            int balance = user.getBalance();
            int requestDepositAmount = request.getAmount();

            if(requestDepositAmount > 0){
                int updatedBalance = balance + requestDepositAmount;
                user.setBalance(updatedBalance);
                repo.save(user);
                return new ResponseEntity<>(
                        String.format("[%s] Balance updated: %s", user.getUsername(), user.getBalance()),
                        HttpStatus.OK
                );
            }
            else{
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> withdrawMoney(int userId, TransactionRequest request){
        if(repo.existsById(userId)){
            Users user = repo.findById(userId).orElseThrow();

            int balance = user.getBalance();
            int requestWithdrawAmount = request.getAmount();

            if(balance - requestWithdrawAmount >= 0){
                int updatedBalance = balance - requestWithdrawAmount;
                user.setBalance(updatedBalance);
                repo.save(user);
                return new ResponseEntity<>(
                        String.format("[%s] Balance updated: %s", user.getUsername(), user.getBalance()),
                        HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
