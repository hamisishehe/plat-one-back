package com.example.platform.Service;



import com.example.platform.Model.ProfitModel;
import com.example.platform.Model.UserModel;
import com.example.platform.Model.WithdrawModel;
import com.example.platform.Repository.ProfitRepository;
import com.example.platform.Repository.UserRepository;
import com.example.platform.Repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawService {

    @Autowired
    private WithdrawRepository withdrawRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfitRepository profitRepository;

    // Method to create a new withdrawal and deduct from profit
    public String createWithdrawal(Long userId, double amount, String transactionId, String phonenumber, String wallet, String withdrawMethod) {
        Optional<UserModel> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            Optional<ProfitModel> profitOptional = profitRepository.findByUser(user);

            if (profitOptional.isPresent()) {
                ProfitModel profit = profitOptional.get();

                // Check if user has enough profit to withdraw
                if (profit.getTotalProfit() >= amount) {
                     double updatedProfit = profit.getTotalProfit() - amount;

                    profit.setTotalProfit(updatedProfit); // Set rounded value

                    profit.setLastUpdated(LocalDate.now()); // Update the lastUpdated field

                    // Create the withdrawal record
                    WithdrawModel withdrawal = new WithdrawModel();
                    withdrawal.setUser(user);
                    withdrawal.setAmount(amount);
                    withdrawal.setWithdrawMethod(withdrawMethod);
                    withdrawal.setWallet(wallet);
                    withdrawal.setWithdrawalDate(LocalDate.now());
                    withdrawal.setPhoneNumber(phonenumber);
                    withdrawal.setStatus(WithdrawModel.Status.PENDING);
                    withdrawal.setTransactionId(transactionId);

                    // Save withdrawal record
                    withdrawRepository.save(withdrawal);

                    // Save the updated profit model
                    profitRepository.save(profit);

                    return "Withdrawal successful";
                } else {
                    return "Insufficient profit balance.";
                }
            } else {
                return "Profit record not found.";
            }
        } else {
            return "User not found.";
        }
    }

    // Method to fetch all withdrawals by userId
    public List<WithdrawModel> getWithdrawalsByUserId(Long userId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            return withdrawRepository.findByUser(user); // Fetch withdrawals by user
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }

    public List<WithdrawModel> getallwithdraw(){
         return withdrawRepository.findAllByOrderByIdAsc();
    }

    public String updatewithdraw(Long wid) {

        Optional<WithdrawModel> withdrawService = withdrawRepository.findById(wid);

        if (withdrawService.isPresent()) {
            WithdrawModel withdrawModel = withdrawService.get();
            withdrawModel.setStatus(WithdrawModel.Status.PAYED);
            withdrawRepository.save(withdrawModel);
            return "Successfully";
        }

        return "";
    }

    public List<WithdrawModel> getAllPendingWithdraw() {
        return withdrawRepository.findAllByStatus(WithdrawModel.Status.PENDING);
    }
    public List<Object[]> getAllWithdrawalsWithUser() {
        return withdrawRepository.findAllWithdrawsWithUser();
    }
}
