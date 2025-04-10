package com.example.platform.Repository;



import com.example.platform.Model.UserModel;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByReferralCode(String referralCode); // Fetch user by referral code
    List<UserModel> findByReferredBy(UserModel referredBy);
    Boolean existsByReferralCode(String referralCode);
    List<UserModel> findAllByOrderByIdDesc();

    Optional<UserModel> findByUsername(String username);
}
