package com.bank.bank_app.service;

import com.bank.bank_app.model.Users;
import com.bank.bank_app.repo.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepo repo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public UserService(UserRepo repo) {
        this.repo = repo;
    }

    public List<Users> getAllUsers(){
        return repo.findAll();
    }

    public Users addUser(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return user;
    }

    public Users getAccountById(int uid) {
        if(repo.existsById(uid)){
            return repo.findById(uid).orElseThrow();
        }
        else{
            return null;
        }
    }
}
