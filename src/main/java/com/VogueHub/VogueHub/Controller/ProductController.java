package com.VogueHub.VogueHub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.VogueHub.VogueHub.Entity.Product;
import com.VogueHub.VogueHub.Service.ProductService;

@Controller
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/product/{id}")
    public String productDetails(@PathVariable int id, Model model) {
        Product product = service.getProductById(id);
        model.addAttribute("product", product);
        return "product-details";
    }
    
    
}