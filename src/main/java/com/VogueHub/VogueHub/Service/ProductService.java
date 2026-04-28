package com.VogueHub.VogueHub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.VogueHub.VogueHub.Entity.Product;
import com.VogueHub.VogueHub.Repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }
}