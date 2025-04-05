//package com.example.platform.Controller.user;
//
//
//
//
//import jakarta.mail.MessagingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api/email")
//public class SendEmailController {
//
//
//    @Autowired
//    private EmailService emailService;
//
//
//    @PostMapping("/send-email")
//    public ResponseEntity<String> sendEmail(
//            @RequestParam("userEmail") String userEmail,
//            @RequestParam("username") String username,
//            @RequestParam("comment") String comment,
//            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
//        try {
//            // Prepare the email subject and body
//            String subject = "Feedback from " + username;
//            String body = "You have received a new comment from username: " + username + " (Email: " + userEmail + ").\n\n"
//                    + "Comment:\n" + comment;
//
//            // Call the EmailService to send the email with an optional attachment
//            emailService.sendEmailWithAttachment("enatokens@gmail.com", subject, body, imageFile);
//
//            return ResponseEntity.ok("Email sent successfully!");
//        } catch (MessagingException | IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
//        }
//    }
//}
