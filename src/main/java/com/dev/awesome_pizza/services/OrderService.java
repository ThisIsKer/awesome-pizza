package com.dev.awesome_pizza.services;

import com.dev.awesome_pizza.entities.Order;
import com.dev.awesome_pizza.entities.Pizza;
import com.dev.awesome_pizza.entities.wrappers.OrderCompleteInfoWrapper;
import com.dev.awesome_pizza.repositories.OrderRepository;
import com.dev.awesome_pizza.repositories.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PizzaRepository pizzaRepository;

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderCompleteInfoWrapper> getAllOrdersCompleteInfo() {
        List<Order> orders = orderRepository.findAll();
        ArrayList<OrderCompleteInfoWrapper> orderCompleteInfoWrappers = new ArrayList<>();
        orders.forEach(order -> {
            OrderCompleteInfoWrapper orderCompleteInfoWrapper = new OrderCompleteInfoWrapper();
            orderCompleteInfoWrapper.setOrder(order);
            orderCompleteInfoWrapper.setPizzas(new ArrayList<>());
            order.getPizzas().forEach(pizzaId -> {
                Pizza pizza = pizzaRepository.findById(pizzaId).orElseThrow(() -> new RuntimeException("Pizza not found"));
                orderCompleteInfoWrapper.getPizzas().add(pizza);
            });
            orderCompleteInfoWrappers.add(orderCompleteInfoWrapper);
        });
        return orderCompleteInfoWrappers;
    }

    public void setOrderInProgress(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(Order.Status.IN_PROGRESS);
        orderRepository.save(order);
    }

    public void setOrderCompleted(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(Order.Status.COMPLETED);
        orderRepository.save(order);
    }

    public Order updateOrderStatus(Long orderId, Order.Status status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}