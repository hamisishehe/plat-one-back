package com.example.platform.Controller.user;



import com.example.platform.Component.JwtUtils;
import com.example.platform.Model.PasswordResetTokenModel;
import com.example.platform.Model.UserModel;
import com.example.platform.Repository.PasswordResetTokenRepository;
import com.example.platform.Repository.UserRepository;
import com.example.platform.Service.EmailService;
import com.example.platform.Service.TokenService;
import com.example.platform.Service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;


    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @CrossOrigin(
            origins = {"https://enatokens.online", "https://enatokens-admin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @GetMapping("/profile")
    public ResponseEntity<UserModel> getUserProfile(@RequestHeader("Authorization") String token) {
        // Extract the token from the "Bearer <token>" format
        String jwt = token.substring(7);

        // Use JwtUtils to extract the username (email) from the token
        String email = jwtUtils.extractUsername(jwt);

        // Fetch the user profile by email
        UserModel user = userService.getUserProfile(email);

        // Return the user profile
        return ResponseEntity.ok(user);
    }
    @CrossOrigin(
            origins = {"https://enatokens.online", "https://enatokens-admin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @GetMapping("/referrals/{userId}")
    public ResponseEntity<List<List<UserModel>>> getAllReferrals(@PathVariable Long userId) {
        UserModel user = userService.getUserById(userId); // Fetch the user by ID


        List<List<UserModel>> referrals = userService.getAllReferrals(user);


        return ResponseEntity.ok(referrals); // Return the referrals
    }





    @CrossOrigin(
            origins = {"https://enatokens.online", "https://enatokens-admin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @GetMapping("/userbyid/{userId}")
    public ResponseEntity<UserModel> getuserby(@PathVariable Long userId) {
        UserModel user = userService.getUserById(userId); // Add method to get user by ID
        return ResponseEntity.ok(user);
    }

    @CrossOrigin(
            origins = {"https://enatokens.online", "https://enatokens-admin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> users(){
        List<UserModel> userModels = userService.getallusers();
        return ResponseEntity.ok(userModels);
    }

    @CrossOrigin(
            origins = {"https://enatokens.online", "https://enatokens-admin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @PostMapping("/lock")
    public ResponseEntity<String> lockAccount(@RequestBody Map<String, String> Formdata) {
        Long id = Long.valueOf(Formdata.get("userId"));
        userService.lockUserAccount(id);
        return ResponseEntity.ok("User account has been locked.");
    }


    @CrossOrigin(
            origins = {"https://enatokens.online", "https://enatokens-admin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }






    @CrossOrigin(
            origins = {"https://enatokens.online", "https://enatokens-admin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> UserData) {
        String email = UserData.get("email");

        PasswordResetTokenModel token = tokenService.createOrUpdateToken(email);
        String resetUrl = "https://enatokens.online/reset-password?token=" + token.getToken();


        emailService.sendPasswordResetEmail(email, resetUrl);

        return ResponseEntity.ok("Password reset link sent to your email");
    }



    @CrossOrigin(
            origins = {"https://enatokens.online", "https://enatokens-admin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> ResetForm) {
        String token = ResetForm.get("token");
        String newPassword = ResetForm.get("newPassword");

        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }

        PasswordResetTokenModel resetToken = passwordResetTokenRepository.findByToken(token);
        UserModel user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetTokenRepository.delete(resetToken); // Delete used token

        return ResponseEntity.ok("Password has been reset successfully");
    }

}
