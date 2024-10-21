package com.dev.awesome_pizza.controllers;

import com.dev.awesome_pizza.entities.Pizza;
import com.dev.awesome_pizza.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @PostMapping
    public ResponseEntity<Pizza> createPizza(@RequestBody Pizza pizza) {
        Pizza createdPizza = pizzaService.createPizza(pizza);
        return new ResponseEntity<>(createdPizza, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Pizza>> getAllPizza() {
        List<Pizza> pizza = pizzaService.getAllPizza();
        return new ResponseEntity<>(pizza, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pizza> getPizzaById(@PathVariable Long id) {
        Pizza pizza = pizzaService.getPizzaById(id);
        return new ResponseEntity<>(pizza, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pizza> updatePizza(@PathVariable Long id, @RequestBody Pizza updatedPizza) {
        Pizza pizza = pizzaService.updatePizza(id, updatedPizza);
        return new ResponseEntity<>(pizza, HttpStatus.OK);
    }

    @PutMapping("/{id}/discontinue")
    public ResponseEntity<Pizza> discontinuePizza(@PathVariable Long id) {
        pizzaService.setPizzaAsDiscontinued(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Pizza> activateePizza(@PathVariable Long id) {
        pizzaService.setPizzaAsActive(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}