package com.example.platform.Component;


import com.example.platform.Model.UserModel;
import com.example.platform.Repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DatabaseSeeder {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void seedDatabase() {
        // Check if the database is already populated
        if (userRepository.count() > 0) {
            return; // Exit if the database is not empty
        }

        UserModel user1 = new UserModel();
        user1.setUsername("platform");
        user1.setEmail("platform@gmail.com");
        user1.setPassword(new BCryptPasswordEncoder().encode("platform@1234"));
        user1.setRole(UserModel.Role.ADMIN);
        user1.setPhoneNumber("1234567890"); // Sample phone number
        user1.setReferralCode("adminReferral"); // Sample referral code
        user1.setReferredBy(null); // No referrer
        userRepository.save(user1);

        UserModel user2 = new UserModel();
        user2.setUsername("hamisi");
        user2.setEmail("hamisishehe@gmail.com");
        user2.setPassword(new BCryptPasswordEncoder().encode("Hamisi2233."));
        user2.setRole(UserModel.Role.USER);
        user2.setPhoneNumber("0744982380"); // Sample phone number
        user2.setReferralCode("adminReferral"); // Sample referral code
        user2.setReferredBy(null); // No referrer
        userRepository.save(user2);

        // Add more users as needed
        System.out.println("Database seeded with initial data.");
    }
}
