	package com.VogueHub.VogueHub.Controller;
	
	import java.util.*;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.*;
	
	import com.VogueHub.VogueHub.Entity.Product;
	import com.VogueHub.VogueHub.Service.ProductService;
	
	import jakarta.servlet.http.HttpSession;
	
	@Controller
	@RequestMapping("/cart")
	public class CartController {
	
	    @Autowired
	    private ProductService productService;
	
	    // ✅ Add to cart
	    @PostMapping("/add")
	    public String addToCart(@RequestParam int productId, HttpSession session) {
	
	        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
	
	        if (cart == null) {
	            cart = new HashMap<>();
	        }
	
	        cart.put(productId, cart.getOrDefault(productId, 0) + 1);
	
	        session.setAttribute("cart", cart);
	
	        return "redirect:/cart/view";
	    }
	
	    // ✅ View cart (UPDATED 🔥)
	    @GetMapping("/view")
	    public String viewCart(HttpSession session, Model model) {

	        List<Product> products = new ArrayList<>();
	        Map<Integer, Integer> quantities = new HashMap<>();

	        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

	        double total = 0;

	        if (cart != null) {
	            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {

	                Product p = productService.getProductById(entry.getKey());

	                if (p != null) {
	                    products.add(p);

	                    int qty = entry.getValue();
	                    quantities.put(p.getId(), qty);

	                    total += p.getPrice() * qty;
	                }
	            }
	        }

	        // ✅ GET DISCOUNT FROM SESSION
	        Integer discount = (Integer) session.getAttribute("discount");
	        if (discount == null) discount = 0;

	        // ✅ CALCULATE DISCOUNT
	        double discountAmount = total * discount / 100;
	        double finalTotal = total - discountAmount;

	        // ✅ SEND TO UI
	        model.addAttribute("products", products);
	        model.addAttribute("quantities", quantities);
	        model.addAttribute("total", total);
	        model.addAttribute("discount", discount);
	        model.addAttribute("discountAmount", discountAmount);
	        model.addAttribute("finalTotal", finalTotal);
	        model.addAttribute("cartCount", products.size());

	        return "cart";
	    }
	
	    // ✅ Remove item
	    @GetMapping("/remove/{id}")
	    public String removeItem(@PathVariable int id, HttpSession session) {
	
	        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
	
	        if (cart != null) {
	            cart.remove(id);
	        }
	
	        session.setAttribute("cart", cart);
	
	        return "redirect:/cart/view";
	    }
	
	    // ✅ Update quantity (+ / −)
	    @GetMapping("/update/{id}")
	    public String updateQuantity(@PathVariable int id,
	                                 @RequestParam String action,
	                                 HttpSession session) {
	
	        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
	
	        if (cart != null && cart.containsKey(id)) {
	
	            int qty = cart.get(id);
	
	            if (action.equals("inc")) {
	                qty++;
	            } else if (action.equals("dec") && qty > 1) {
	                qty--;
	            }
	
	            cart.put(id, qty);
	        }
	
	        session.setAttribute("cart", cart);
	
	        return "redirect:/cart/view";
	    }
	
	    // ✅ Clear cart (optional bonus)
	    @GetMapping("/clear")
	    public String clearCart(HttpSession session) {
	        session.removeAttribute("cart");
	        return "redirect:/cart/view";
	    }
	    
	    
	    @GetMapping("/add/{id}")
	    public String addToCartFromWishlist(@PathVariable int id, HttpSession session) {
	
	        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
	
	        if (cart == null) {
	            cart = new HashMap<>();
	        }
	
	        cart.put(id, cart.getOrDefault(id, 0) + 1);
	
	        session.setAttribute("cart", cart);
	
	        return "redirect:/cart/view";
	    }
	    
	    @GetMapping("/apply")
	    public String applyCoupon(@RequestParam String code, HttpSession session) {
	
	        if (code.equalsIgnoreCase("VOGUE10")) {
	            session.setAttribute("discount", 10); // 10%
	        } else {
	            session.setAttribute("discount", 0);
	        }
	
	        return "redirect:/cart/view";
	    }
	}