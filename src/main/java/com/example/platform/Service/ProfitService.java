package com.example.platform.Service;


import com.example.platform.Model.ProfitModel;
import com.example.platform.Model.UserModel;
import com.example.platform.Repository.ProfitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ProfitService {

    @Autowired
    private ProfitRepository profitRepository;


    // Method to retrieve profit details for a user
    public ProfitModel getProfitByUser(UserModel user) {
        Optional<ProfitModel> profitOptional = profitRepository.findByUser(user);
        return profitOptional.orElseGet(() -> {
            // If no profit record exists, return a new ProfitModel with zero values
            ProfitModel newProfit = new ProfitModel();
            newProfit.setUser(user); // Set the user in the new ProfitModel
            newProfit.setTotalProfit(0.0); // Initialize with 0 profit
            return profitRepository.save(newProfit); // Save the new profit record
        });
    }

    // Method to update profit for a user
    public void updateProfit(UserModel user, double dailyProfit) {
        ProfitModel profit = getProfitByUser(user);
        double updatedProfit = profit.getTotalProfit() + dailyProfit;
        profit.setTotalProfit(updatedProfit);
        profit.setLastUpdated(LocalDate.now()); // Update the last updated date
        profitRepository.save(profit); // Save the updated profit record
    }

    public Optional<ProfitModel> getenaprofit(Long userId){
        return profitRepository.findByUserId(userId);
    }

}
