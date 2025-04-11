package com.example.platform.Controller.user;



import com.example.platform.Model.WalletModel;
import com.example.platform.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @PostMapping("/address")
    public ResponseEntity<String> getwallet(@RequestBody Map<String, String> walletForm){
                 Long userId = Long.valueOf(walletForm.get("userId"));
                 String wallet = walletForm.get("wallet");

                 return ResponseEntity.ok(walletService.addWallet(wallet, userId));
    }

    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @GetMapping("/show-address/{userId}")
    public ResponseEntity<Optional<WalletModel>> showwallet(@PathVariable Long userId){
        return ResponseEntity.ok(walletService.getWallet(userId));
    }


}
