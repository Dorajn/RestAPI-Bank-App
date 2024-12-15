package com.bank.bank_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Transfers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transferId;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Users senderId;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Users receiverId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "transfer_timestamp", nullable = false, updatable = false)
    private LocalDateTime transferTimestamp;

}
