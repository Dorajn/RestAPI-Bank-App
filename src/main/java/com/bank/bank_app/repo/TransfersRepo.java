package com.bank.bank_app.repo;

import com.bank.bank_app.model.Transfers;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransfersRepo extends JpaRepository<Transfers, Integer> {
}
