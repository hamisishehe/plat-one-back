package com.example.platform.Service;


import com.example.platform.Model.PasswordResetTokenModel;
import com.example.platform.Model.UserModel;
import com.example.platform.Repository.PasswordResetTokenRepository;
import com.example.platform.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    public PasswordResetTokenModel createOrUpdateToken(String email) {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email"));

        // Check if a token already exists for this user
        PasswordResetTokenModel existingToken = tokenRepository.findByUser(user);

        if (existingToken != null) {
            // Update the existing token and expiry date
            existingToken.setToken(UUID.randomUUID().toString());
            existingToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
            return tokenRepository.save(existingToken);
        } else {
            // Create a new token if none exists
            PasswordResetTokenModel token = PasswordResetTokenModel.builder()
                    .token(UUID.randomUUID().toString())
                    .user(user)
                    .expiryDate(LocalDateTime.now().plusMinutes(15))
                    .build();
            return tokenRepository.save(token);
        }
    }

    public boolean isTokenValid(String token) {
        PasswordResetTokenModel resetToken = tokenRepository.findByToken(token);
        return resetToken != null && resetToken.getExpiryDate().isAfter(LocalDateTime.now());
    }
}
