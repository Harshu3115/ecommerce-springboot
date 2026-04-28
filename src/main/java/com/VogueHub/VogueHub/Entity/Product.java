package com.VogueHub.VogueHub.Entity;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String category;
    private double price;
    private String image;
    private String description;

    // getters & setters
}