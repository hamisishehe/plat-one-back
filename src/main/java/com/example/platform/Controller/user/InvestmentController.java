package com.example.platform.Controller.user;


import com.example.platform.Model.InvestmentModel;
import com.example.platform.Service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/investments")
public class InvestmentController {

    @Autowired
    private InvestmentService investmentService;


    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @PostMapping("/invest")
    public ResponseEntity<String> invest(@RequestBody Map<String, String> investmentData) {
        try {
            // Sanitize and parse inputs
            Long userId = Long.parseLong(HtmlUtils.htmlEscape(investmentData.get("userId").trim()));
            Double amount = Double.parseDouble(HtmlUtils.htmlEscape(investmentData.get("amount").trim()));
            String investPackage = HtmlUtils.htmlEscape(investmentData.get("package").trim());

            // Basic validation checks
            if (userId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID");
            }
            if (amount <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Amount must be greater than zero");
            }
            if (investPackage == null || investPackage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Investment package must not be empty");
            }

            // Proceed with investment processing
            String investment = investmentService.invest(userId, amount, investPackage);
            return ResponseEntity.ok(investment);

        } catch (NumberFormatException e) {
            // Handle cases where userId or amount are not valid numbers
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input format");
        } catch (Exception e) {
            // Catch any unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }


    // Optionally, you can add a method to get all investments of a user
    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @GetMapping("/{userId}")
    public ResponseEntity<List<InvestmentModel>> getUserInvestments(@PathVariable Long userId) {
        List<InvestmentModel> investments = investmentService.getUserInvestments(userId);
        return new ResponseEntity<>(investments, HttpStatus.OK);
    }

    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @GetMapping("/users-investments")
    public ResponseEntity<List<Object[]>> getinvestments() {
        return  ResponseEntity.ok(investmentService.getallplans());
    }


    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @GetMapping("/users-pending-investments")
    public ResponseEntity<List<Object[]>> getpending() {
        return  ResponseEntity.ok(investmentService.getallpendingplans());
    }

    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @GetMapping("/user/{invest_id}")
    public ResponseEntity<InvestmentModel> getinvestmentsbyid(@PathVariable Long invest_id) {
        return  ResponseEntity.ok(investmentService.getbyinvestmentid(invest_id));
    }

    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @DeleteMapping("/delete/{invest_id}")
    public ResponseEntity<String>  deleteinvestment(@PathVariable Long invest_id) {
        investmentService.deleteplan(invest_id);
        return new ResponseEntity<>("Investment deleted successfully", HttpStatus.OK);
    }
}
