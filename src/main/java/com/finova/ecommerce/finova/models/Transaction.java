package com.finova.ecommerce.finova.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId; // 👈 Replaced @ManyToOne with a simple foreign key

    private String phase;
    private String amount;
    private String approvalCode;
}
