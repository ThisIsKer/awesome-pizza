package com.dev.awesome_pizza.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pizzas")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private List<String> ingredients;

    @Enumerated(EnumType.STRING)
    private Pizza.Status status;

    public enum Status {
        ACTIVE,
        DISCONTINUED
    }
}
