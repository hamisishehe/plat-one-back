package com.example.platform.Controller;



import com.example.platform.Model.UserModel;
import com.example.platform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class RegisterController {

    @Autowired
    private UserService userService;

    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true"
    )
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestBody Map<String, String> userData,
            @RequestParam(required = false) String referralCode) {



        String username = HtmlUtils.htmlEscape(userData.get("username").trim());
        String email = HtmlUtils.htmlEscape(userData.get("email").trim());
        String phonenumber = HtmlUtils.htmlEscape(userData.get("phonenumber").trim());
        String password = HtmlUtils.htmlEscape(userData.get("password").trim());


        System.out.println(username);
        System.out.println(email);
        System.out.println(phonenumber);
        System.out.println(password);


//        // Basic validation for each field
//        if (!username.matches("^[a-zA-Z0-9_]{3,20}$")) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username format.");
//        }
//        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email format.");
//        }
//        if (!phonenumber.matches("^[0-9]{10}$")) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number format.");
//        }
//        if (password.length() < 6) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be at least 6 characters.");
//        }

        System.out.println("not done");
        // Process registration
        String registeredUser = userService.registerUser(username, email, phonenumber, password, referralCode);

        System.out.println("done");


        return ResponseEntity.ok(registeredUser);

    }

    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @GetMapping("/{userId}/referrals")
    public List<UserModel> getReferrals(@PathVariable Long userId) {
        return userService.getReferrals(userId);
    }
}
