package com.VogueHub.VogueHub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.VogueHub.VogueHub.Entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}
