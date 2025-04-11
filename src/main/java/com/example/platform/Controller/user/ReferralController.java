package com.example.platform.Controller.user;



import com.example.platform.Model.UserModel;
import com.example.platform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/referrals")
public class ReferralController {

    @Autowired
    private UserService userService;

    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @GetMapping("/show-referrals/{userId}")
    public ResponseEntity<List<UserModel>> getUserDeposits(@PathVariable Long userId) {

        List<UserModel> userModels = userService.getReferrals(userId);
        return ResponseEntity.ok(userModels);
    }


}
