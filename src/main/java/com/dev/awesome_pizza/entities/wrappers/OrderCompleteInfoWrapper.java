package com.dev.awesome_pizza.entities.wrappers;

import com.dev.awesome_pizza.entities.Order;
import com.dev.awesome_pizza.entities.Pizza;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderCompleteInfoWrapper {

    private Order order;
    private List<Pizza> pizzas;
}
