package com.finova.ecommerce.finova.entity;

import com.finova.ecommerce.finova.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String typeRid;

    @Column(nullable = false)
    private String amount;

    @Column(nullable = false)
    private String currency;

    private String language;

    @Column(length = 512)
    private String description;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String secret;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
