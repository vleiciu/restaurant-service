package com.org.rs.rest;

import com.org.rs.enums.OrderStatus;
import com.org.rs.persistance.Orders;
import com.org.rs.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrdersController {

    private OrderService orderService;

    @GetMapping("/approve/{orderId}")
    public ResponseEntity<Orders> approveOrder(@PathVariable("id") Integer orderId) {
        Orders orders = orderService.setOrderStatus(orderId, OrderStatus.APPROVED);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/reject/{orderId}")
    public ResponseEntity<Orders> rejectOrder(@PathVariable("id") Integer orderId) {
        Orders orders = orderService.setOrderStatus(orderId, OrderStatus.REJECTED);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/get/{restaurantId}")
    public ResponseEntity<List<Orders>> getOrdersList (@PathVariable("id") Integer restaurantId) {
        return ResponseEntity.ok(orderService.findAllActiveOrders(restaurantId));
    }

    @GetMapping("/history/{restaurantId}")
    public ResponseEntity<List<Orders>> getHistoricalOrdersList (@PathVariable("id") Integer restaurantId) {
        return ResponseEntity.ok(orderService.findAllNonActiveOrders(restaurantId));
    }
}
