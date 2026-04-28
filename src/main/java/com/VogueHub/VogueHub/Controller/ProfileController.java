package com.VogueHub.VogueHub.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.VogueHub.VogueHub.Entity.Order;
import com.VogueHub.VogueHub.Entity.Product;
import com.VogueHub.VogueHub.Entity.User;
import com.VogueHub.VogueHub.Service.OrderService;
import com.VogueHub.VogueHub.Service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {
	
	

	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/profile")
	public String profilePage(HttpSession session, Model model) {

	    User user = (User) session.getAttribute("loggedUser");

	    if (user == null) {
	        return "redirect:/";
	    }

	    // ✅ wishlist
	    List<Integer> ids = (List<Integer>) session.getAttribute("wishlist");
	    List<Product> products = new ArrayList<>();

	    if (ids != null) {
	        for (int id : ids) {
	            Product p = productService.getProductById(id);
	            if (p != null) {
	                products.add(p);
	            }
	        }
	    }

	    // ✅ GET ORDERS FROM DB
	    List<Order> orders = orderService.getOrdersByEmail(user.getEmail());

	    // ✅ CALCULATE TOTAL SPENT (AFTER orders created)
	    double totalSpent = 0;
	    for (Order o : orders) {
	        totalSpent += o.getAmount();
	    }

	    // ✅ SEND DATA
	    model.addAttribute("user", user);
	    model.addAttribute("wishlist", products);
	    model.addAttribute("wishlistCount", products.size());
	    model.addAttribute("orders", orders);
	    model.addAttribute("totalSpent", totalSpent);

	    return "userProfile";
	}
}