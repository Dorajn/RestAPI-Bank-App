package com.bank.bank_app.service;

import com.bank.bank_app.model.TransferRequest;
import com.bank.bank_app.model.Users;
import com.bank.bank_app.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    private UserRepo repo;

    public TransferService(UserRepo repo) {
        this.repo = repo;
    }

    @Transactional
    public ResponseEntity<?> transferMoney(int uid, TransferRequest request) {
        int senderId = request.getSenderId();
        int receiverId = request.getReceiverId();
        int amount = request.getAmount();

        if(repo.existsById(senderId) && repo.existsById(receiverId)){
            Users sender = repo.findById(senderId).orElseThrow();
            Users receiver = repo.findById(receiverId).orElseThrow();

            int senderBalance = sender.getBalance();
            int receiverBalance = receiver.getBalance();
            if(senderBalance - amount >= 0){
                sender.setBalance(senderBalance - amount);
                receiver.setBalance(receiverBalance + amount);
                repo.save(sender);
                repo.save(receiver);
                return new ResponseEntity<>(
                        String.format("[From %s to %s] Transfer accepted", sender.getUsername(), receiver.getUsername()),
                        HttpStatus.OK
                );
            }
            else{
                return new ResponseEntity<>("Not sufficient balance.", HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>("No such user in database.", HttpStatus.NOT_FOUND);
        }

    }
}
