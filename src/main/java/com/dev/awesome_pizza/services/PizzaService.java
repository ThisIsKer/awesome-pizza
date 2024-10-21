package com.dev.awesome_pizza.services;

import com.dev.awesome_pizza.entities.Pizza;
import com.dev.awesome_pizza.repositories.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    public Pizza createPizza(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public List<Pizza> getAllPizza() {
        return pizzaRepository.findAll();
    }

    public Pizza getPizzaById(Long pizzaId) {
        return pizzaRepository.findById(pizzaId).orElseThrow(() -> new RuntimeException("Pizza not found"));
    }

    public Pizza updatePizza(Long pizzaId, Pizza updatedPizza) {
        Pizza pizza = getPizzaById(pizzaId);
        pizza.setName(updatedPizza.getName());
        pizza.setIngredients(updatedPizza.getIngredients());
        pizza.setStatus(updatedPizza.getStatus());
        return pizzaRepository.save(pizza);
    }

    public void deletePizza(Long pizzaId) {
        Pizza pizza = getPizzaById(pizzaId);
        pizzaRepository.delete(pizza);
    }

    public void setPizzaAsDiscontinued(Long pizzaId) {
        Pizza pizza = getPizzaById(pizzaId);
        pizza.setStatus(Pizza.Status.DISCONTINUED);
        pizzaRepository.save(pizza);
    }

    public void setPizzaAsActive(Long pizzaId) {
        Pizza pizza = getPizzaById(pizzaId);
        pizza.setStatus(Pizza.Status.ACTIVE);
        pizzaRepository.save(pizza);
    }
}