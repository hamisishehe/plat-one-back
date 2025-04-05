//package com.example.platform.Service;
//
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendSimpleEmail(String toEmail, String subject, String body) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo("enatokens@gmail.com");
//        message.setSubject(subject);
//        message.setText(body);
//        message.setFrom(toEmail);  // Use the email you configured
//
//
//        mailSender.send(message);
//    }
//
//
//    public void sendEmailWithAttachment(String toEmail, String subject, String body, MultipartFile attachment) throws MessagingException, IOException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);  // Set multipart flag to true
//
//        helper.setTo("enatokens@gmail.com");
//        helper.setSubject(subject);
//        helper.setText(body);
//        helper.setFrom(toEmail);
//
//        // If an attachment is provided, add it to the email
//        if (attachment != null && !attachment.isEmpty()) {
//            helper.addAttachment(attachment.getOriginalFilename(), attachment);
//        }
//
//        mailSender.send(message);
//    }
//
//
//    public void sendPasswordResetEmail(String to, String resetUrl) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject("Password Reset Request");
//        message.setText("To reset your password, click the link below:\n" + resetUrl);
//        mailSender.send(message);
//    }
//}
