package com.VogueHub.VogueHub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.VogueHub.VogueHub.Entity.Order;
import com.VogueHub.VogueHub.Entity.User;
import com.VogueHub.VogueHub.Repository.OrderRepository;
import com.VogueHub.VogueHub.Service.EmailService;
import com.VogueHub.VogueHub.Service.OrderService;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderController {
	
	
	

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String myOrders(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/";
        }

        List<Order> orders = orderService.getOrdersByEmail(user.getEmail());

        // ✅ total orders
        int totalOrders = orders.size();

        // ✅ delivered (for now assume COD = delivered)
        long delivered = orders.stream()
                .filter(o -> o.getPaymentMethod().equalsIgnoreCase("Cash on Delivery"))
                .count();

        // ✅ in transit (others)
        long inTransit = totalOrders - delivered;

        // ✅ total spent
        double totalSpent = orders.stream()
                .mapToDouble(Order::getAmount)
                .sum();

        model.addAttribute("orders", orders);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("delivered", delivered);
        model.addAttribute("inTransit", inTransit);
        model.addAttribute("totalSpent", totalSpent);

        return "orders";
    }
    
    @Autowired
    private EmailService emailService;

//    @Autowired
//    private OrderRepository orderRepository;
//
//    public void placeOrder(Order order) {
//
//        // ✅ Save order
//        orderRepository.save(order);
//
//        // ✅ Send email
//        emailService.sendOrderConfirmationEmail(
//                order.getUser().getEmail(),                 // FIXED
//                order.getUser().getName(),
//                order.getId().toString(),
//                LocalDate.now().toString(),
//                order.getTotalAmount().toString(),
//                order.getPaymentMethod(),
//                order.getProduct().getName(),
//                String.valueOf(order.getQuantity()),
//                order.getUser().getAddress()
//        );
//    }
}