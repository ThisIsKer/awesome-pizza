package com.dev.awesome_pizza.controllers;

import com.dev.awesome_pizza.entities.Order;
import com.dev.awesome_pizza.entities.wrappers.OrderCompleteInfoWrapper;
import com.dev.awesome_pizza.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody List<Long> pizzas, @RequestHeader("customerName") String customerName) {
        Order newOrder = new Order(pizzas, customerName);
        return orderService.createOrder(newOrder);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/complete-info")
    public List<OrderCompleteInfoWrapper> getAllOrdersCompleteInfo() {
        return orderService.getAllOrdersCompleteInfo();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/{id}/complete-info")
    public OrderCompleteInfoWrapper getOrderCompleteInfoById(@PathVariable Long id) {
        return orderService.getOrderCompleteInfoById(id);
    }

    @PutMapping("/{id}/in-progress")
    public void setOrderInProgress(@PathVariable Long id) {
        orderService.setOrderInProgress(id);
    }

    @PutMapping("/{id}/completed")
    public void setOrderCompleted(@PathVariable Long id) {
        orderService.setOrderCompleted(id);
    }

    @PutMapping("/{id}/status")
    public Order updateOrderStatus(@PathVariable Long id, @RequestBody Order.Status status) {
        return orderService.updateOrderStatus(id, status);
    }
}