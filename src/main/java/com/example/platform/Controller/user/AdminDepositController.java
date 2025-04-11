package com.example.platform.Controller.user;


import com.example.platform.Model.AdminDeposit;
import com.example.platform.Service.AdminDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin-deposits")
public class AdminDepositController {

    @Autowired
    private AdminDepositService adminDepositService;


    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @GetMapping("/getall")
    public ResponseEntity<List<AdminDeposit>> getall() {

        List<AdminDeposit> deposits = adminDepositService.getall();
        return ResponseEntity.ok(deposits);
    }
}
