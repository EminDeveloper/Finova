package com.finova.ecommerce.finova.repositories;

import com.finova.ecommerce.finova.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
