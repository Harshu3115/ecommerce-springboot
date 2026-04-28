package com.VogueHub.VogueHub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.VogueHub.VogueHub.Entity.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
	
	Seller findByEmail(String email);
}