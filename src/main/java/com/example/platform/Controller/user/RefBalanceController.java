package com.example.platform.Controller.user;


import com.example.platform.Model.RefBalanceModel;
import com.example.platform.Service.RefBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/balance")
public class RefBalanceController {


    @Autowired
    private RefBalanceService refBalanceService;


    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @GetMapping("/show-ref-balance/{userId}")
    public ResponseEntity<Optional<RefBalanceModel>> getRefBalance(@PathVariable Long userId) {

        Optional<RefBalanceModel> balanceModel = refBalanceService.getRefBalance(userId);
        return ResponseEntity.ok(balanceModel);
    }



}
