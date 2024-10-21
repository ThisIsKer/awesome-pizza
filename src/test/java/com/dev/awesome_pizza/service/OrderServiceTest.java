package com.dev.awesome_pizza.service;

import com.dev.awesome_pizza.entities.Order;
import com.dev.awesome_pizza.entities.Pizza;
import com.dev.awesome_pizza.entities.wrappers.OrderCompleteInfoWrapper;
import com.dev.awesome_pizza.repositories.OrderRepository;
import com.dev.awesome_pizza.repositories.PizzaRepository;
import com.dev.awesome_pizza.services.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PizzaRepository pizzaRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testCreateOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setCustomerName("Mario Rossi");
        order.setStatus(Order.Status.PLACED);

        when(orderRepository.save(order)).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);

        assertEquals(order.getId(), createdOrder.getId());
        assertEquals(order.getCustomerName(), createdOrder.getCustomerName());
        assertEquals(order.getStatus(), createdOrder.getStatus());
    }

    @Test
    void testGetAllOrders() {
        Order order1 = new Order();
        order1.setId(1L);
        order1.setCustomerName("Mario Rossi");
        order1.setStatus(Order.Status.PLACED);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setCustomerName("Pippo Franco");
        order2.setStatus(Order.Status.PLACED);

        List<Order> orders = Arrays.asList(order1, order2);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> allOrders = orderService.getAllOrders();

        assertEquals(2, allOrders.size());
        assertEquals("Mario Rossi", allOrders.get(0).getCustomerName());
        assertEquals("Pippo Franco", allOrders.get(1).getCustomerName());
    }

    @Test
    void testGetOrderCompleteInfoById() {
        Order order = new Order();
        order.setId(1L);
        order.setCustomerName("Mario Rossi");
        order.setPizzas(Arrays.asList(1L, 2L));

        Pizza pizza1 = new Pizza();
        pizza1.setId(1L);
        pizza1.setName("Pizza Margherita");

        Pizza pizza2 = new Pizza();
        pizza2.setId(2L);
        pizza2.setName("Pizza Marinara");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza1));
        when(pizzaRepository.findById(2L)).thenReturn(Optional.of(pizza2));

        OrderCompleteInfoWrapper orderCompleteInfo = orderService.getOrderCompleteInfoById(1L);

        assertNotNull(orderCompleteInfo);
        assertEquals(order.getId(), orderCompleteInfo.getOrder().getId());
        assertEquals(2, orderCompleteInfo.getPizzas().size());
        assertEquals("Pizza Margherita", orderCompleteInfo.getPizzas().get(0).getName());
        assertEquals("Pizza Marinara", orderCompleteInfo.getPizzas().get(1).getName());
        assertNotEquals("Pizza Pineapple and Bacon by Joe Bastianich", orderCompleteInfo.getPizzas().get(0).getName());
        assertNotEquals("Pizza Pineapple and Bacon by Joe Bastianich", orderCompleteInfo.getPizzas().get(1).getName());
    }

    @Test
    void testGetAllOrdersCompleteInfo() {
        // Arrange
        Order order1 = new Order();
        order1.setId(1L);
        order1.setPizzas(Arrays.asList(1L));

        Order order2 = new Order();
        order2.setId(2L);
        order2.setPizzas(Arrays.asList(2L));

        Pizza pizza1 = new Pizza();
        pizza1.setId(1L);
        pizza1.setName("Pizza Margherita");

        Pizza pizza2 = new Pizza();
        pizza2.setId(2L);
        pizza2.setName("Pizza Marinara");

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza1));
        when(pizzaRepository.findById(2L)).thenReturn(Optional.of(pizza2));

        List<OrderCompleteInfoWrapper> allOrdersCompleteInfo = orderService.getAllOrdersCompleteInfo();

        assertEquals(2, allOrdersCompleteInfo.size());
        assertEquals(1, allOrdersCompleteInfo.get(0).getPizzas().size());
        assertEquals("Pizza Margherita", allOrdersCompleteInfo.get(0).getPizzas().get(0).getName());
        assertEquals(1, allOrdersCompleteInfo.get(1).getPizzas().size());
        assertEquals("Pizza Marinara", allOrdersCompleteInfo.get(1).getPizzas().get(0).getName());
    }

    @Test
    void testSetOrderInProgress() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(Order.Status.PLACED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderService.setOrderInProgress(1L);

        assertEquals(Order.Status.IN_PROGRESS, order.getStatus());
        Mockito.verify(orderRepository).save(order);
    }

    @Test
    void testSetOrderCompleted() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(Order.Status.IN_PROGRESS);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderService.setOrderCompleted(1L);

        assertEquals(Order.Status.COMPLETED, order.getStatus());
        Mockito.verify(orderRepository).save(order);
    }

    @Test
    void testUpdateOrderStatus() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(Order.Status.PLACED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderService.updateOrderStatus(1L, Order.Status.COMPLETED);

        Order updatedOrder = orderService.getOrderById(1L);

        assertEquals(Order.Status.COMPLETED, updatedOrder.getStatus());
        Mockito.verify(orderRepository).save(order);
    }
}