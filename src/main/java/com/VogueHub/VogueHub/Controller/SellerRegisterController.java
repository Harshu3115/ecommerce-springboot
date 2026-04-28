package com.VogueHub.VogueHub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.VogueHub.VogueHub.Entity.Seller;
import com.VogueHub.VogueHub.Repository.SellerRepository;

@Controller
public class SellerRegisterController {

    @Autowired
    private SellerRepository sellerRepository; // ✅ REQUIRED

    @GetMapping("/seller/register")
    public String showSellerForm(Model model) {
        model.addAttribute("seller", new Seller());
        return "sellerRegister";
    }

    @PostMapping("/seller/register")
    public String saveSeller(@ModelAttribute Seller seller) {
        sellerRepository.save(seller); // ✅ NOW WORKS
        return "redirect:/seller/login";
    }
}