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
import java.math.BigDecimal;

@Service
public class TransactionService {

    private UserRepo repo;

    public TransactionService(UserRepo repo) {
        this.repo = repo;
    }

    public ResponseEntity<?> depositMoney(int userId, TransactionRequest request){
        if(repo.existsById(userId)){
            Users user = repo.findById(userId).orElseThrow();

            BigDecimal balance = user.getBalance();
            BigDecimal requestDepositAmount = request.getAmount();

            if(requestDepositAmount.compareTo(BigDecimal.ZERO) > 0){
                BigDecimal updatedBalance = balance.add(requestDepositAmount);
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

            BigDecimal balance = user.getBalance();
            BigDecimal requestWithdrawAmount = request.getAmount();
            BigDecimal balanceAfterWithdraw = balance.subtract(requestWithdrawAmount);

            if(balanceAfterWithdraw.compareTo(BigDecimal.ZERO) >= 0){
                user.setBalance(balanceAfterWithdraw);
                repo.save(user);
                return new ResponseEntity<>(
                        String.format("[%s] Balance updated: %s", user.getUsername(), user.getBalance()),
                        HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
