package com.example.platform.Repository;


import com.example.platform.Model.PasswordResetTokenModel;
import com.example.platform.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenModel, Long> {
    PasswordResetTokenModel findByToken(String token);
    PasswordResetTokenModel findByUser(UserModel user);
}
