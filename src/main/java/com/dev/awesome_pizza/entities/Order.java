package com.dev.awesome_pizza.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "pizza_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    @Enumerated(EnumType.STRING)
    private Status status;

    private List<Long> pizzas;

    public enum Status {
        PLACED,
        IN_PROGRESS,
        COMPLETED
    }

    public Order (List<Long> pizzas, String customerName) {
        this.pizzas = pizzas;
        this.customerName = customerName;
        this.status = Status.PLACED;
    }

    public void addPizza(Pizza pizza) {
        this.pizzas.add(pizza.getId());
    }

    public void addPizzaById(Long pizzaId) {
        this.pizzas.add(pizzaId);
    }
}