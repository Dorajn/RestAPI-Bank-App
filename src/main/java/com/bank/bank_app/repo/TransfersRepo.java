package com.bank.bank_app.repo;

import com.bank.bank_app.model.Transfers;
import com.bank.bank_app.model.Users;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransfersRepo extends JpaRepository<Transfers, Integer> {

    @Query(
            "SELECT t " +
            "FROM Transfers t " +
            "WHERE t.senderId.uid = :userId OR t.receiverId.uid = :userId"
    )
    List<Transfers> findTransactionsByUserId(@Param("userId") Integer id);
}
