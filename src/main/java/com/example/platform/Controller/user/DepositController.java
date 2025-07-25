package com.example.platform.Controller.user;



import com.example.platform.Model.DepositModel;
import com.example.platform.Model.WalletModel;
import com.example.platform.Service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/deposits")
public class DepositController {

    @Autowired
    private DepositService depositService;

    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @PostMapping("/make")
    public ResponseEntity<String> makeDeposit(@RequestBody Map<String, String> depositRequest) {

        System.out.println(depositRequest);
        try {

            Long userId = Long.parseLong(depositRequest.get("userId"));
            double amount = Double.parseDouble(depositRequest.get("amount"));
            String address =depositRequest.get("address");
            String username = depositRequest.get("username");

            // Basic validation checks
            if (userId == null || userId <= 0) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID");
            }
            System.out.println("2");


            String deposit = depositService.makeDeposit(userId, amount,address, username);

            return ResponseEntity.ok(deposit);

        } catch (NumberFormatException e) {
            // Handle cases where userId or amount are not valid numbers
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input format");
        } catch (Exception e) {
            // Catch any unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @PostMapping("/confirm_deposit")
    public ResponseEntity<String> confirm_deposit(@RequestBody Map<String, String> formData) {
        try {
            // Sanitize and parse inputs
            Long userId = Long.parseLong(HtmlUtils.htmlEscape(formData.get("userId").trim()));
            Long depositId = Long.parseLong(HtmlUtils.htmlEscape(formData.get("depositId").trim()));

            // Basic validation checks
            if (userId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID");
            }

            // Proceed with deposit confirmation
            String confirmedDeposit = depositService.confirm_Deposit(userId,depositId);
            return ResponseEntity.ok(confirmedDeposit);

        } catch (NumberFormatException e) {
            // Handle cases where userId or amount are not valid numbers
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input format");
        } catch (Exception e) {
            // Catch any unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }



    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @PostMapping("/confirm")
    public ResponseEntity<String> confirmDeposit(@RequestBody Map<String, String> formData) {
        try {
            // Sanitize and parse inputs
            Long userId = Long.parseLong(HtmlUtils.htmlEscape(formData.get("userId").trim()));
            double amount = Double.parseDouble(formData.get("amount"));

            // Basic validation checks
            if (userId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID");
            }

            // Proceed with deposit confirmation
            String confirmedDeposit = depositService.confirmDeposit(userId, amount);
            return ResponseEntity.ok(confirmedDeposit);

        } catch (NumberFormatException e) {
            // Handle cases where userId or amount are not valid numbers
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input format");
        } catch (Exception e) {
            // Catch any unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }


    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @PutMapping("/change_status/{depositId}")
    public ResponseEntity<String> ChangeStatus(@PathVariable Long depositId) {
        String change_status = depositService.changestatus(depositId);
        return ResponseEntity.ok(change_status);
    }




    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @PutMapping("/reject/{depositId}")
    public ResponseEntity<String>deleteDeposit(@PathVariable Long depositId) {
          String reject= depositService.rejectDeposit(depositId);
        return ResponseEntity.ok(reject);
    }


    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DepositModel>> getUserDeposits(@PathVariable Long userId) {

        List<DepositModel> depositModels = depositService.getUserDeposits(userId);
        return ResponseEntity.ok(depositModels);
    }
    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @GetMapping("/users-deposits")
    public ResponseEntity<List<DepositModel>> getalluserdepo() {

        List<DepositModel> depositModels = depositService.getalluserdeposit();
        return ResponseEntity.ok(depositModels);
    }

    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @GetMapping("/users-pending-deposits")
    public ResponseEntity<List<DepositModel>> getAllPending() {

        List<DepositModel> depositModels = depositService.getAllPendingDeposits();
        return ResponseEntity.ok(depositModels);
    }

    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @GetMapping("/users-all-deposits")
    public ResponseEntity<List<DepositModel>> getAllCandR() {

        List<DepositModel> depositModels = depositService.getAllConfirmedAndRejectedDeposits();
        return ResponseEntity.ok(depositModels);
    }



    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @GetMapping("/getallpending")
    public ResponseEntity<List<DepositModel>> getall() {

        List<DepositModel> deposits = depositService.getAllPendingDeposits();
        return ResponseEntity.ok(deposits);
    }


    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @GetMapping("/wallet/{depositId}")
    public ResponseEntity<WalletModel> getWalletByDeposit(@PathVariable Long depositId) {
        WalletModel wallet = depositService.getWalletByDeposit(depositId);
        if (wallet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(wallet);
    }



    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @GetMapping("/{depositId}")
    public ResponseEntity<Optional<DepositModel>> getDepositById(@PathVariable Long depositId) {
        Optional<DepositModel> deposits = depositService.getDepositById(depositId);

        return ResponseEntity.ok(deposits);
    }

    // Endpoint to get all wallets for a user based on user ID
    @CrossOrigin(
            origins = {"https://novanest-ecommerce.com", "https://novanestecommerce.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600
    )
    @GetMapping("/user/wallets/{userId}")
    public ResponseEntity<List<WalletModel>> getWalletsByUser(@PathVariable Long userId) {
        List<WalletModel> wallets = depositService.getWalletsByUser(userId);
        if (wallets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(wallets);
    }
}
