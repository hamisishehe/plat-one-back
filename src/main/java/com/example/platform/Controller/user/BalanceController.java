package com.example.platform.Controller.user;


import com.example.platform.Model.BalanceModel;
import com.example.platform.Service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @GetMapping("/show-balance/{userId}")
    public ResponseEntity<BalanceModel> getUserDeposits(@PathVariable Long userId) {

        BalanceModel balanceModel = balanceService.getbalance(userId);
        return ResponseEntity.ok(balanceModel);
    }

    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @GetMapping("/show-all-balance")
    public ResponseEntity<List<BalanceModel>> showbalance() {

        List<BalanceModel> balanceModel = balanceService.getallbalance();
        return ResponseEntity.ok(balanceModel);
    }
}
