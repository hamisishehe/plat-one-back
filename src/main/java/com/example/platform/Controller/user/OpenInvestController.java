//package com.example.platform.Controller.user;
//
//
//import com.example.platform.Service.OnOffService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/openInvest")
//public class OpenInvestController {
//
//    @Autowired
//    private OnOffService onOffService;
//
//
//    @CrossOrigin(
//            origins = {"https://enatokens.online", "https://enatokens-admin.netlify.app"}, // Specify exact origins
//            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
//            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
//            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
//            maxAge = 3600 // Cache the preflight response for 1 hour
//    )
//    @PostMapping("/getStatus/{id}")
//    public String getStatus(@PathVariable Long id){
//        return onOffService.getStatus(id);
//    }
//
//    @CrossOrigin(
//            origins = {"https://enatokens.online", "https://enatokens-admin.netlify.app"}, // Specify exact origins
//            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
//            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
//            allowCredentials = "true", // Keep this only if you need credentials (cookies, authentication)
//            maxAge = 3600 // Cache the preflight response for 1 hour
//    )
//    @PostMapping("/getStatus/{id}")
//    public String changeStatus(@PathVariable Long id){
//        return onOffService.changeStatus(id);
//    }
//
//
//
//}
