package com.example.platform.Service;



import com.example.platform.Model.UserModel;
import com.example.platform.Model.WalletModel;
import com.example.platform.Repository.UserRepository;
import com.example.platform.Repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    public String addWallet(String walletAddress, Long userId) {

        UserModel userModel = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if a wallet already exists for the user
        Optional<WalletModel> existingWallet = walletRepository.findByUserId(userId);

        if (existingWallet.isPresent()) {
            WalletModel wallet = existingWallet.get();
            wallet.setWallet(walletAddress);  // Update the wallet address
            walletRepository.save(wallet);
            return "Wallet address successfully updated";
        }

        // If no wallet exists, create a new one
        WalletModel newWallet = new WalletModel();
        newWallet.setWallet(walletAddress);
        newWallet.setUser(userModel);

        walletRepository.save(newWallet);
        return "Wallet address successfully added";
    }


    public Optional<WalletModel> getWallet(Long userId){
        Optional<UserModel> userModel = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("not found")));

        return walletRepository.findByUserId(userId);
    }
}
