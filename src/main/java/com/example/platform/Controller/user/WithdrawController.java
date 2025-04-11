package com.example.platform.Controller.user;


import com.example.platform.Model.UserModel;
import com.example.platform.Model.WithdrawModel;
import com.example.platform.Service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/withdraw")
public class WithdrawController {

    @Autowired
    private WithdrawService withdrawService;



    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com/auth/login", "https://cwsadmin.netlify.app/login"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @PostMapping("/create")
    public ResponseEntity<String> withdraw(@RequestBody Map<String, String> formData) {
        try {
            // Sanitize and parse inputs
            Long userId = Long.valueOf(HtmlUtils.htmlEscape(formData.get("userId").trim()));
            Double amount = Double.valueOf(HtmlUtils.htmlEscape(formData.get("amount").trim()));
            String transactionId = HtmlUtils.htmlEscape(formData.get("transactionId").trim());
            String phoneNumber = HtmlUtils.htmlEscape(formData.get("phonenumber").trim());
            String wallet = HtmlUtils.htmlEscape(formData.get("wallet").trim());
            String withdrawMethod = HtmlUtils.htmlEscape(formData.get("withdrawmethod").trim());

            // Basic validation checks
            if (userId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID");
            }
            if (amount <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Amount must be greater than zero");
            }


            // Proceed with withdrawal processing
            String response = withdrawService.createWithdrawal(userId, amount, transactionId, phoneNumber, wallet, withdrawMethod);
            return ResponseEntity.ok(response);

        } catch (NumberFormatException e) {
            // Handle cases where userId or amount are not valid numbers
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input format");
        } catch (Exception e) {
            // Catch any unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }


    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com/auth/login", "https://cwsadmin.netlify.app/login"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @GetMapping("/show-withdraw/{userId}")
    public ResponseEntity<List<WithdrawModel>> getWithdrawalsByUserId(@PathVariable Long userId) {
        List<WithdrawModel> withdrawals = withdrawService.getWithdrawalsByUserId(userId);
        return ResponseEntity.ok(withdrawals);
    }

    @CrossOrigin(
            origins = {"http://enatokens.online", "https://enatokens-admin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @GetMapping("/users-withdraw/show-all")
    public ResponseEntity<List<WithdrawModel>> getallwithdraw() {
        List<WithdrawModel> withdrawals = withdrawService.getallwithdraw();
        return ResponseEntity.ok(withdrawals);
    }

    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com/auth/login", "https://cwsadmin.netlify.app/login"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @PostMapping("/confirm")
    public ResponseEntity<String> getallwithdraw(@RequestBody Map<String, String> FormData) {
        Long id = Long.valueOf(FormData.get("wid"));

        return ResponseEntity.ok(withdrawService.updatewithdraw(id));
    }



    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com/auth/login", "https://cwsadmin.netlify.app/login"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @GetMapping("/all-with-user")
    public ResponseEntity<List<Map<String, Object>>> getAllWithdrawalsWithUser() {
        List<Object[]> results = withdrawService.getAllWithdrawalsWithUser();
        List<Map<String, Object>> withdrawalsWithUsers = new ArrayList<>();

        for (Object[] result : results) {
            WithdrawModel withdraw = (WithdrawModel) result[0];
            UserModel user = (UserModel) result[1];

            Map<String, Object> map = new HashMap<>();
            map.put("id", withdraw.getId());
            map.put("amount", withdraw.getAmount());
            map.put("transactionId", withdraw.getTransactionId());
            map.put("withdrawalDate", withdraw.getWithdrawalDate());
            map.put("phoneNumber", withdraw.getPhoneNumber());
            map.put("withdrawMethod", withdraw.getWithdrawMethod());
            map.put("wallet", withdraw.getWallet());
            map.put("status", withdraw.getStatus());

            // Add user information
            map.put("userId", user.getId());
            map.put("username", user.getUsername());
            map.put("email", user.getEmail());
            map.put("phoneNumber", user.getPhoneNumber());

            withdrawalsWithUsers.add(map);
        }

        return ResponseEntity.ok(withdrawalsWithUsers);
    }



    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com/auth/login", "https://cwsadmin.netlify.app/login"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
            maxAge = 3600 // Cache the preflight response for 1 hour
    )
    @GetMapping("/getallpending")
    public ResponseEntity<List<WithdrawModel>> getallpending() {

        List<WithdrawModel> withdrawModels = withdrawService.getAllPendingWithdraw();
        return ResponseEntity.ok(withdrawModels);
    }


}

