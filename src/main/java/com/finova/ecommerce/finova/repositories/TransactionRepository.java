package com.finova.ecommerce.finova.repositories;

import com.finova.ecommerce.finova.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {}
