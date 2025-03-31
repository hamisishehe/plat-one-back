package com.example.platform.Service;


import com.example.platform.Model.ProfileModel;
import com.example.platform.Model.UserModel;
import com.example.platform.Repository.ProfileRepository;
import com.example.platform.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<ProfileModel> getprofile(Long user_id){
        return profileRepository.findByUserId(user_id);
    }


    public String updateProfile(String firstName, String secondName, String lastName, String city, String zipCode, Long userId) {
        // Find the user by ID
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find the user's existing profile or create a new one if it doesn't exist
        ProfileModel profileModel = profileRepository.findByUserId(userId)
                .orElse(new ProfileModel());

        // Update the profile fields
        profileModel.setUser(user); // Set the user, either updating or associating a new one
        profileModel.setFirstName(firstName);
        profileModel.setSecondName(secondName);
        profileModel.setLastName(lastName);
        profileModel.setCity(city);
        profileModel.setZipCode(zipCode);

        // Save the profile (either updated or newly created)
        profileRepository.save(profileModel);

        return "Profile successfully updated";
    }

    public Optional<ProfileModel> getenaprofit(Long userId){
        return profileRepository.findByUserId(userId);
    }
}
