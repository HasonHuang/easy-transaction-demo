package com.ihason.dtp.easytrans.demos.order.controller;

import com.ihason.dtp.easytrans.demos.order.entity.Order;
import com.ihason.dtp.easytrans.demos.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public Order create(@RequestParam Long userId, @RequestParam BigDecimal price) {
        return orderService.create(userId, price);
    }

}
