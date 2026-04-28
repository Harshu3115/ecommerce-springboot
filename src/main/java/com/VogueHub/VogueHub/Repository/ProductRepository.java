package com.VogueHub.VogueHub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.VogueHub.VogueHub.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
}