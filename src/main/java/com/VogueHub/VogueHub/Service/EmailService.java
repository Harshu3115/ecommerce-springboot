package com.VogueHub.VogueHub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.VogueHub.VogueHub.Repository.SellerRepository;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;
    
    @Autowired
    private SellerRepository sellerRepository;

    public void sendTemplateEmail(String to, String subject, String name, String email) {
        try {
            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("email", email); // ✅ ADD THIS

            String htmlContent = templateEngine.process("user-register-email", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (Exception e) {
            System.out.println("Template email failed: " + e.getMessage());
        }
    }

    public void sendAdminTemplateEmail(String to, String username, String email, String mobile, String date) {

        try {
            Context context = new Context();
            context.setVariable("username", username);
            context.setVariable("email", email);
            context.setVariable("mobile", mobile);
            context.setVariable("date", date);

            String html = templateEngine.process("admin-get-user-register-email", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("New User Registered 🚨");
            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            System.out.println("Admin email failed: " + e.getMessage());
        }
    }
}