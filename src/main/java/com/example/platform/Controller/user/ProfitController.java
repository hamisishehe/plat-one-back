package com.example.platform.Controller.user;



import com.example.platform.Model.ProfitModel;
import com.example.platform.Service.ProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/profit")
public class ProfitController {

    @Autowired
    private ProfitService profitService;
    @CrossOrigin(
            origins = {"https://enatokens.online", "https://enatokens-admin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @GetMapping("/show-balance/{userId}")
    public ResponseEntity<Optional<ProfitModel>> getbalance (@PathVariable Long userId){
        return ResponseEntity.ok(profitService.getenaprofit(userId));
    }

}
