package com.example.platform.Service;


import com.example.platform.Model.RefBalanceModel;
import com.example.platform.Model.RefWithdrawModel;
import com.example.platform.Model.UserModel;
import com.example.platform.Repository.RefBalanceRepository;
import com.example.platform.Repository.RefWithdrawRepository;
import com.example.platform.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class RefWithdrawService {

    @Autowired
    private RefWithdrawRepository refWithdrawRepository;

    @Autowired
    private UserRepository userRepository;

   @Autowired
   private RefBalanceRepository refBalanceRepository;

    // Method to create a new withdrawal and deduct from profit
    public String createWithdrawal(Long userId, double amount, String transactionId, String phonenumber, String wallet, String withdrawMethod) {
        Optional<UserModel> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            Optional<RefBalanceModel> refBalanceModel = refBalanceRepository.findByUser(user);

            if (refBalanceModel.isPresent()) {
                RefBalanceModel refb = refBalanceModel.get();

                // Check if user has enough profit to withdraw
                if (refb.getAvailableBalance() >= amount) {
                    double updatedProfit = refb.getAvailableBalance() - amount;

                    refb.setAvailableBalance(updatedProfit); // Set rounded value

                    refb.setCreatedAt(new Date()); // Update the lastUpdated field

                    // Create the withdrawal record
                    RefWithdrawModel withdrawal = new RefWithdrawModel();
                    withdrawal.setUser(user);
                    withdrawal.setAmount(amount);
                    withdrawal.setWithdrawMethod(withdrawMethod);
                    withdrawal.setWallet(wallet);
                    withdrawal.setWithdrawalDate(LocalDate.now());
                    withdrawal.setPhoneNumber(phonenumber);
                    withdrawal.setStatus(RefWithdrawModel.Status.PENDING);
                    withdrawal.setTransactionId(transactionId);

                    // Save withdrawal record
                    refWithdrawRepository.save(withdrawal);

                    // Save the updated profit model
                    refBalanceRepository.save(refb);

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


    public List<RefWithdrawModel> getallwithdraw(){
        return refWithdrawRepository.findAll();
    }

    public List<RefWithdrawModel> getWithdrawalsByUserId(Long userId) {
        return refWithdrawRepository.findAllByUserId(userId);
    }

    public List<RefWithdrawModel> getAllPendingWithdraw() {
        return refWithdrawRepository.findAllByStatus(RefWithdrawModel.Status.PENDING);
    }


    public String updatewithdraw(Long wid) {

        Optional<RefWithdrawModel> withdrawService = refWithdrawRepository.findById(wid);

        if (withdrawService.isPresent()) {
            RefWithdrawModel withdrawModel = withdrawService.get();
            withdrawModel.setStatus(RefWithdrawModel.Status.PAYED);
            refWithdrawRepository.save(withdrawModel);
            return "Successfully";
        }

        return "";
    }

    public List<Object[]> getAllRefWithdrawalsWithUser() {
        return refWithdrawRepository.findAllRefWithdrawsWithUser();
    }
}
