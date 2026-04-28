package com.VogueHub.VogueHub.Controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.VogueHub.VogueHub.Entity.Order;
import com.VogueHub.VogueHub.Entity.Product;
import com.VogueHub.VogueHub.Entity.User;
import com.VogueHub.VogueHub.Service.OrderService;
import com.VogueHub.VogueHub.Service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CheckoutController {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    // ✅ Show checkout page
    @GetMapping("/checkout")
    public String checkoutPage(HttpSession session, Model model) {

        // ✅ LOGIN CHECK
        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/";
        }

        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        List<Product> products = new ArrayList<>();
        Map<Integer, Integer> quantities = new HashMap<>();

        double total = 0;

        if (cart != null) {
            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {

                Product p = productService.getProductById(entry.getKey());

                if (p != null) {
                    int qty = entry.getValue();

                    products.add(p);
                    quantities.put(p.getId(), qty);

                    total += p.getPrice() * qty;
                }
            }
        }

        Integer discount = (Integer) session.getAttribute("discount");
        if (discount == null) discount = 0;

        double discountAmount = total * discount / 100;
        double finalTotal = total - discountAmount;

        // ✅ ADD THIS LINE (FIX)
        session.setAttribute("finalTotal", finalTotal);

        User user = (User) session.getAttribute("loggedUser");

        model.addAttribute("products", products);
        model.addAttribute("quantities", quantities);
        model.addAttribute("total", total);
        model.addAttribute("discountAmount", discountAmount);
        model.addAttribute("finalTotal", finalTotal);
        model.addAttribute("user", user);

        return "checkout";
    }
     

    @PostMapping("/placeOrder")
    public String placeOrder(@RequestParam String payment,
                             HttpSession session,
                             Model model) {

        // ✅ LOGIN CHECK
        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/";
        }

        User user = (User) session.getAttribute("loggedUser");

        String paymentMethod = "";
        String transactionId = "";

        if (!payment.equals("cod")) {
            transactionId = "TXN-" + System.currentTimeMillis();
        }

        if(payment.equals("cod")) paymentMethod = "Cash on Delivery";
        else if(payment.equals("upi")) paymentMethod = "UPI";
        else if(payment.equals("card")) paymentMethod = "Card";
        else paymentMethod = "Net Banking";

        String orderId = "VH-" + new Random().nextInt(100000);

        Double finalTotal = (Double) session.getAttribute("finalTotal");
        if(finalTotal == null) finalTotal = 0.0;

        // ✅ CREATE ORDER
        Order order = new Order();
        order.setOrderId(orderId);
        order.setEmail(user.getEmail());
        order.setAmount(finalTotal);
        order.setPaymentMethod(paymentMethod);
        order.setTransactionId(transactionId);
        order.setDate(java.time.LocalDateTime.now());

        // ✅ SAVE
        orderService.saveOrder(order);

        String date = java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));

        model.addAttribute("orderId", orderId);
        model.addAttribute("payment", paymentMethod);
        model.addAttribute("total", finalTotal);
        model.addAttribute("date", date);
        model.addAttribute("email", user.getEmail());

        session.removeAttribute("cart");
        session.removeAttribute("discount");

        return "paymentSucceful"; // ✅ FIXED
    }
}