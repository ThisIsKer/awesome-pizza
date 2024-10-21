package com.dev.awesome_pizza.repositories;

import com.dev.awesome_pizza.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}