package com.example.platform.Service;


import com.example.platform.Model.BalanceModel;
import com.example.platform.Model.EnaTokenModel;
import com.example.platform.Repository.BalanceRepository;
import com.example.platform.Repository.EnaTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class ENATokenService {

    @Autowired
    private EnaTokenRepository enaTokenRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    // Conversion rate: 1 ENA = 0.0001 USD => 1 USD = 10,000 ENA
    private static final double ENA_TO_USD_RATE = 0.0001;  // Rate for ENA to USD

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

        // Calculate the equivalent ENA token amount (1 ENA = 0.0001 USD)
        double enaAmount = usdAmount / ENA_TO_USD_RATE; // Convert USD to ENA

        // Deduct the USD amount from the user's available balance
        balance.setAvailableBalance(balance.getAvailableBalance() - usdAmount);
        balanceRepository.save(balance);

        // Fetch or initialize the user's ENA token balance
        Optional<EnaTokenModel> enaTokenOptional = enaTokenRepository.findByUserId(userId);
        EnaTokenModel enaToken;

        if (enaTokenOptional.isPresent()) {
            // Add the converted ENA token amount to the user's existing token balance
            enaToken = enaTokenOptional.get();
            double updatedBalance = enaToken.getAvailableBalance() + enaAmount;
            enaToken.setAvailableBalance(updatedBalance);
        } else {
            // If the user doesn't have an ENA token account, create one
            enaToken = new EnaTokenModel();
            enaToken.setUser(balance.getUser());
            enaToken.setAvailableBalance(enaAmount);
            enaToken.setLockedBalance(0);  // Initial locked balance
        }

        // Save the updated ENA token balance
        enaTokenRepository.save(enaToken);

        return "Swap successful: " + usdAmount + " USD converted to " + enaAmount + " ENA tokens.";
    }

    public EnaTokenModel getBalance(Long userId) {
        return enaTokenRepository.findAllByUserId(userId);
    }
}
