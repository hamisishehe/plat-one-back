package com.example.platform.Service;


import com.example.platform.Model.BalanceModel;
import com.example.platform.Model.UsdTokenModel;
import com.example.platform.Repository.BalanceRepository;
import com.example.platform.Repository.USDTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class USDTokenService {

    @Autowired
    private USDTokenRepository USDTokenRepository;

    @Autowired
    private BalanceRepository balanceRepository;



    public String swapUsdToEna(Long userId, double usdAmount) {

        // Fetch the user's balance
        Optional<BalanceModel> balanceOptional = balanceRepository.findByUserId(userId);

        if (balanceOptional.isEmpty()) {
            return "Balance not found for user.";
        }

        BalanceModel balance = balanceOptional.get();

        // Check if the user has enough available USD balance for the swap
        if (balance.getAvailableBalance() < usdAmount) {
            return "Insufficient balance for this swap.";
        }



        balance.setAvailableBalance(balance.getAvailableBalance() - usdAmount);
        balanceRepository.save(balance);

        // Fetch or initialize the user's ENA token balance
        Optional<UsdTokenModel> enaTokenOptional = USDTokenRepository.findByUserId(userId);
        UsdTokenModel enaToken;

        if (enaTokenOptional.isPresent()) {
            // Add the converted ENA token amount to the user's existing token balance
            enaToken = enaTokenOptional.get();
            double updatedBalance = enaToken.getAvailableBalance() + usdAmount;
            enaToken.setAvailableBalance(updatedBalance);
        } else {
            // If the user doesn't have an ENA token account, create one
            enaToken = new UsdTokenModel();
            enaToken.setUser(balance.getUser());
            enaToken.setAvailableBalance(usdAmount);
            enaToken.setLockedBalance(0);  // Initial locked balance
        }

        // Save the updated ENA token balance
        USDTokenRepository.save(enaToken);

        return  "Successful "+ usdAmount + "Sent to Work Account";
    }

    public UsdTokenModel getBalance(Long userId) {
        return USDTokenRepository.findAllByUserId(userId);
    }
}
