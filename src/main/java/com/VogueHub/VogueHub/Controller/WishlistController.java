package com.VogueHub.VogueHub.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.VogueHub.VogueHub.Entity.Product;
import com.VogueHub.VogueHub.Service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class WishlistController {

    // ADD
	@PostMapping("/addToWishlist")
	public String addToWishlist(@RequestParam int productId, HttpSession session) {

	    List<Integer> wishlist = (List<Integer>) session.getAttribute("wishlist");

	    // if not exists → create new
	    if (wishlist == null) {
	        wishlist = new ArrayList<>();
	    }

	    // prevent duplicate
	    if (!wishlist.contains(productId)) {
	        wishlist.add(productId);
	    }

	    // save back to session
	    session.setAttribute("wishlist", wishlist);

	    return "redirect:/watchlist";
	}

    // SHOW
    @Autowired
    ProductService productService;

    @GetMapping("/watchlist")
    public String showWishlist(HttpSession session, Model model) {

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

        model.addAttribute("wishlist", products); // ✅ NOW sending Product objects

        return "watchlist";
    }

    // REMOVE ✅ (Step 3)
    @GetMapping("/watchlist/remove/{id}")
    public String removeFromWishlist(@PathVariable int id, HttpSession session) {

        List<Integer> wishlist = (List<Integer>) session.getAttribute("wishlist");

        if (wishlist != null) {
            wishlist.remove(Integer.valueOf(id));
        }

        session.setAttribute("wishlist", wishlist);

        return "redirect:/watchlist";
    }
}