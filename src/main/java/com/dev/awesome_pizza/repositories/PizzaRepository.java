package com.dev.awesome_pizza.repositories;

import com.dev.awesome_pizza.entities.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {

}