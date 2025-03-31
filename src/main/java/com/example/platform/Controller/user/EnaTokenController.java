package com.example.platform.Controller.user;



import com.example.platform.Model.EnaTokenModel;
import com.example.platform.Service.ENATokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ena")
public class EnaTokenController {

    @Autowired
    private ENATokenService enaTokenService;




    @CrossOrigin(
            origins = {"https://enatokens.online", "https://enatokens-admin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @PostMapping("/swap")
    public ResponseEntity<String> swapUsdToEna(@RequestBody Map<String, String> userForm) {
        Long userId = Long.valueOf(userForm.get("userId"));
        Double usdAmount = Double.valueOf(userForm.get("usdAmount"));

        String response = enaTokenService.swapUsdToEna(userId, usdAmount);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(
            origins = {"https://enatokens.online", "https://enatokens-admin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @GetMapping("/show-balance/{userId}")
    public ResponseEntity<EnaTokenModel> getUserDeposits(@PathVariable Long userId) {

        EnaTokenModel enaTokenModel = enaTokenService.getBalance(userId);
        return ResponseEntity.ok(enaTokenModel);
    }
}
