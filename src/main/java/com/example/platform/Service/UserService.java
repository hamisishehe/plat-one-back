package com.example.platform.Service;


import com.example.platform.Model.BalanceModel;
import com.example.platform.Model.UserModel;
import com.example.platform.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (userModel.isLocked()) {
            throw new UsernameNotFoundException("User account is locked");
        }


        return new org.springframework.security.core.userdetails.User(
                userModel.getEmail(),
                userModel.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + userModel.getRole().name()))
        );
    }

    public Optional<UserModel> authenticateUser(String email, String password) {
        Optional<UserModel> user = userRepository.findByEmail(email);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user; // Return user if authentication is successful
        }
        return Optional.empty(); // Return empty if authentication fails
    }

    public String registerUser(String username, String email, String phonenumber, String password, String referralCode) {

        UserModel checkemail = userRepository.findByEmail(email).orElse(null);

        if (checkemail != null){
            return "User Already Exist";
        }
        // Create a new UserModel and set the fields
        UserModel user = new UserModel();
        user.setUsername(username);
        user.setEmail(email);
        user.setPhoneNumber(phonenumber);

        String generatedReferralCode = generateUniqueReferralCode();
        user.setReferralCode(generatedReferralCode);

        // Encrypt the password
        user.setPassword(passwordEncoder.encode(password));

        // Set the user role (default role can be set, e.g., USER)
        user.setRole(UserModel.Role.USER);

        // Generate a balance for the new user
        BalanceModel balance = new BalanceModel();
        balance.setAvailableBalance(0);
        balance.setLockedBalance(0);
        balance.setUser(user);
        user.setBalance(balance);

        // Handle referral logic
        if (referralCode != null && !referralCode.isEmpty()) {
            Optional<UserModel> referrer = userRepository.findByReferralCode(referralCode);
            if (referrer.isPresent()) {
                user.setReferredBy(referrer.get());
                referrer.get().getReferrals().add(user);
            } else {
                return "Invalid referral code";
            }
        }

        // Save the new user
        UserModel savedUser = userRepository.save(user);

//        // Send a welcome email
//        emailService.sendSimpleEmail(user.getEmail(), "Welcome to Enatech",
//                "Dear " + user.getUsername() + ",\n\nThank you for registering with Enatech!");
//
//        // Notify referrer if applicable
//        if (user.getReferredBy() != null) {
//            emailService.sendSimpleEmail(user.getReferredBy().getEmail(), "New Referral Alert",
//                    "Dear " + user.getReferredBy().getUsername() + ",\n\n" + user.getUsername() + " has registered using your referral code!");
//        }

        return "Registration Successfully";
    }


    private String generateUniqueReferralCode() {
        Random random = new Random();
        String referralCode;
        boolean exists;

        do {
            referralCode = String.format("%06d", random.nextInt(999999));
            exists = userRepository.existsByReferralCode(referralCode);
        } while (exists);

        return referralCode;
    }

    public List<UserModel> getLevelOneReferrals(UserModel user) {
        return userRepository.findByReferredBy(user);  // Direct referrals (level one)
    }

    public List<UserModel> getLevelTwoReferrals(UserModel user) {
        List<UserModel> levelTwoReferrals = new ArrayList<>();
        List<UserModel> levelOneReferrals = getLevelOneReferrals(user);

        for (UserModel levelOne : levelOneReferrals) {
            levelTwoReferrals.addAll(getLevelOneReferrals(levelOne)); // Get referrals of level one users
        }

        return levelTwoReferrals;
    }

    public List<UserModel> getLevelThreeReferrals(UserModel user) {
        List<UserModel> levelThreeReferrals = new ArrayList<>();
        List<UserModel> levelTwoReferrals = getLevelTwoReferrals(user);

        for (UserModel levelTwo : levelTwoReferrals) {
            levelThreeReferrals.addAll(getLevelOneReferrals(levelTwo)); // Get referrals of level two users
        }

        return levelThreeReferrals;
    }

    public List<List<UserModel>> getAllReferrals(UserModel user) {
        List<List<UserModel>> allReferrals = new ArrayList<>();

        // Add level one referrals
        allReferrals.add(getLevelOneReferrals(user));

        // Add level two referrals
        allReferrals.add(getLevelTwoReferrals(user));

        // Add level three referrals
        allReferrals.add(getLevelThreeReferrals(user));

        return allReferrals;
    }

    public List<UserModel> getReferrals(Long userId) {
        Optional<UserModel> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get().getReferrals(); // Return the list of referrals
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    // Calculate referral earnings (Example: 3-level referral system)
    public double calculateReferralEarnings(UserModel user) {
        double totalEarnings = 0;

        // Level 1 referrals
        totalEarnings += user.getReferrals().size() * 10; // Example: 10 units per level 1 referral

        // Level 2 and Level 3 logic goes here...

        return totalEarnings;
    }



    public UserModel getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Add the new method to fetch user profile by username
    public UserModel getUserProfile(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }


    public List<UserModel>  getallusers(){
        return userRepository.findAllByOrderByIdDesc();
    }

    public void lockUserAccount(Long id)  {
        UserModel user = userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("user not found"));

        if (user.isLocked()) {
            user.setLocked(false);  // Unlock the user if locked
        } else {
            user.setLocked(true);   // Lock the user if unlocked
        }

        userRepository.save(user);
    }

    public String deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return "User deleted successfully.";
        } else {
            return "User not found.";
        }
    }


}
