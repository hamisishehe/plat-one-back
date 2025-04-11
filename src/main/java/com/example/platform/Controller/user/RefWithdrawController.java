package com.example.platform.Controller.user;


import com.example.platform.Model.RefWithdrawModel;
import com.example.platform.Model.UserModel;
import com.example.platform.Service.RefWithdrawService;
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
@RequestMapping("/api/ref-withdraw")
public class RefWithdrawController {

    @Autowired
    private RefWithdrawService refWithdrawService;


// Other necessary imports

    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
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
            String response = refWithdrawService.createWithdrawal(userId, amount, transactionId, phoneNumber, wallet, withdrawMethod);
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
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RefWithdrawModel>> getWithdrawalsByUserId(@PathVariable Long userId) {
        List<RefWithdrawModel> withdrawals = refWithdrawService.getWithdrawalsByUserId(userId);
        return ResponseEntity.ok(withdrawals);
    }


    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @GetMapping("/users-withdraw/show-all")
    public ResponseEntity<List<RefWithdrawModel>> getallwithdraw() {
        List<RefWithdrawModel> withdrawals = refWithdrawService.getallwithdraw();
        return ResponseEntity.ok(withdrawals);
    }

    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @PostMapping("/confirm")
    public ResponseEntity<String> getallwithdraw(@RequestBody Map<String, String> FormData) {
        Long id = Long.valueOf(FormData.get("wid"));

        return ResponseEntity.ok(refWithdrawService.updatewithdraw(id));
    }

    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @GetMapping("/all-with-user")
    public ResponseEntity<List<Map<String, Object>>> getAllRefWithdrawalsWithUser() {
        List<Object[]> results = refWithdrawService.getAllRefWithdrawalsWithUser();
        List<Map<String, Object>> refWithdrawalsWithUsers = new ArrayList<>();

        for (Object[] result : results) {
            RefWithdrawModel refWithdraw = (RefWithdrawModel) result[0];
            UserModel user = (UserModel) result[1];

            Map<String, Object> map = new HashMap<>();
            map.put("id", refWithdraw.getId());
            map.put("amount", refWithdraw.getAmount());
            map.put("transactionId", refWithdraw.getTransactionId());
            map.put("withdrawalDate", refWithdraw.getWithdrawalDate());
            map.put("phoneNumber", refWithdraw.getPhoneNumber());
            map.put("withdrawMethod", refWithdraw.getWithdrawMethod());
            map.put("wallet", refWithdraw.getWallet());
            map.put("status", refWithdraw.getStatus());

            // Add user information
            map.put("userId", user.getId());
            map.put("username", user.getUsername());
            map.put("email", user.getEmail());
            map.put("phoneNumber", user.getPhoneNumber());

            refWithdrawalsWithUsers.add(map);
        }

        return ResponseEntity.ok(refWithdrawalsWithUsers);
    }

    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @GetMapping("/getallpending")
    public ResponseEntity<List<RefWithdrawModel>> getall() {

        List<RefWithdrawModel> withdrawModels = refWithdrawService.getAllPendingWithdraw();
        return ResponseEntity.ok(withdrawModels);
    }
}
