package com.bank.bank_app.service;

import com.bank.bank_app.model.TransferRequest;
import com.bank.bank_app.model.Transfers;
import com.bank.bank_app.model.Users;
import com.bank.bank_app.repo.TransfersRepo;
import com.bank.bank_app.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransferService {

    private UserRepo userRepo;
    private TransfersRepo transfersRepo;

    public TransferService(UserRepo userRepo, TransfersRepo transfersRepo) {
        this.userRepo = userRepo;
        this.transfersRepo = transfersRepo;
    }

    @Transactional
    public ResponseEntity<?> transferMoney(int uid, TransferRequest request) {
        int senderId = uid;
        int receiverId = request.getReceiverId();
        int amount = request.getAmount();

        if(userRepo.existsById(senderId) && userRepo.existsById(receiverId)){
            Users sender = userRepo.findById(senderId).orElseThrow();
            Users receiver = userRepo.findById(receiverId).orElseThrow();

            int senderBalance = sender.getBalance();
            int receiverBalance = receiver.getBalance();
            if(senderBalance - amount >= 0){
                sender.setBalance(senderBalance - amount);
                receiver.setBalance(receiverBalance + amount);
                userRepo.save(sender);
                userRepo.save(receiver);
                createTransferLog(sender, receiver, amount);
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

    private void createTransferLog(Users sender, Users receiver, int amount){
        Transfers transfer = Transfers.builder()
                .senderId(sender)
                .receiverId(receiver)
                .amount(BigDecimal.valueOf(amount))
                .transferTimestamp(LocalDateTime.now())
                .build();

        transfersRepo.save(transfer);
    }
}
