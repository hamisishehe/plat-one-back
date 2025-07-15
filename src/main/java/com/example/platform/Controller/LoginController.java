package com.example.platform.Controller;



import com.example.platform.Component.JwtUtils;
import com.example.platform.Model.UserModel;
import com.example.platform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;



    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        // Retrieve and sanitize inputs
        String email = HtmlUtils.htmlEscape(loginRequest.get("email").trim());
        String password = HtmlUtils.htmlEscape(loginRequest.get("password").trim());

        System.out.println(email);
        System.out.println(password);

//        // Basic validation
//        if (email.isEmpty() || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Invalid email format."));
//        }
//        if (password.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Password cannot be empty."));
//        }

        Optional<UserModel> user = userService.authenticateUser(email, password);

        if (user.isPresent()) {
            String token = jwtUtils.generateToken(email, user.get().getRole().name()); // Pass the role here
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", user.get().getRole()); // Assuming `getRole()` method returns the user's role
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
        }
    }

}
