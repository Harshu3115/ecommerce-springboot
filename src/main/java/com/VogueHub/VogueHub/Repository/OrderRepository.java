package com.VogueHub.VogueHub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.VogueHub.VogueHub.Entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByEmail(String email);
}