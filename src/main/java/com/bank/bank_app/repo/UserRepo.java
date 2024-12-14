package com.bank.bank_app.repo;

import com.bank.bank_app.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
    boolean existsById(Integer id);
}
