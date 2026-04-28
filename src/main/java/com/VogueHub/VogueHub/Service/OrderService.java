package com.VogueHub.VogueHub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.VogueHub.VogueHub.Entity.Order;
import com.VogueHub.VogueHub.Repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repo;

    public void saveOrder(Order order) {
        repo.save(order);
    }

    public List<Order> getOrdersByEmail(String email) {
        return repo.findByEmail(email);
    }
}