package com.example.platform.Controller.user;



import com.example.platform.Model.ProfileModel;
import com.example.platform.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @PostMapping("/update")
    public String updateprofile(@RequestBody Map<String, String> profileData){
        Long userId = Long.valueOf(profileData.get("userId"));
        String firstname = profileData.get("firstname");
        String secondname =profileData.get("secondname");
        String lastname = profileData.get("lastname");
        String city = profileData.get("city");
        String zip_code = profileData.get("zip_code");

        return profileService.updateProfile(firstname,secondname, lastname,city, zip_code, userId);
    }

    @CrossOrigin(
            origins = {"https://cryptowealthsolutionscws.com", "https://cwsadmin.netlify.app"}, // Specify exact origins
            allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"}, // Limit headers to necessary ones
            methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT}, // Allow only required methods
            allowCredentials = "true",
            maxAge = 3600
            // Keep this only if you need credentials (cookies, authentication)
    )
    @GetMapping("/show-profile/{userId}")
    public ResponseEntity<Optional<ProfileModel>> getprofile (@PathVariable Long userId){
        return ResponseEntity.ok(profileService.getenaprofit(userId));
    }
}
