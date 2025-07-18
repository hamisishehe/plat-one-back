package com.example.platform.Service;

import com.example.platform.Model.*;
import com.example.platform.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DepositService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private AdminDepositRepository adminDepositRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private RefBalanceRepository refBalanceRepository;

    // Step 1: Make deposit (Pending) and update locked balance
    public String makeDeposit(Long userId, double amount, String address,String username) {

        UserModel user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        DepositModel depositModel = new DepositModel();
        depositModel.setAmount(amount);
        depositModel.setAddress(address);
        depositModel.setUsername(username);
        depositModel.setStatus(DepositModel.Status.PENDING);  // Set status as PENDING
        depositModel.setUser(user);
        depositRepository.save(depositModel);

        return "Successfully";
    }


    public String confirmDeposit(Long userid, double amount) {

        UserModel user = userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BalanceModel balance = user.getBalance();

        if (balance !=null){
            balance.setAvailableBalance(balance.getAvailableBalance() + amount);

            AdminDeposit adminDeposit = new AdminDeposit();
            adminDeposit.setAmount(amount);
            adminDeposit.setGateAway("Admin Payment");
            adminDeposit.setUsername(user.getUsername());
            adminDeposit.setPhoneNumber(user.getPhoneNumber());
            adminDeposit.setStatus(DepositModel.Status.CONFIRMED);  // Set status as PENDING
            adminDeposit.setUser(user);
            balanceRepository.save(balance);
            adminDepositRepository.save(adminDeposit);
            handleReferralBonuses(user, amount);

        }
        else{

            BalanceModel balanceModel = new BalanceModel();
            balanceModel.setAvailableBalance(amount);
            balanceModel.setLockedBalance(0.0);
            balanceModel.setUser(user);
            balanceRepository.save(balanceModel);
            handleReferralBonuses(user, amount);

            AdminDeposit adminDeposit = new AdminDeposit();
            adminDeposit.setAmount(amount);
            adminDeposit.setGateAway("Admin Payment");
            adminDeposit.setUsername(user.getUsername());
            adminDeposit.setPhoneNumber(user.getPhoneNumber());
            adminDeposit.setStatus(DepositModel.Status.CONFIRMED);  // Set status as PENDING
            adminDeposit.setUser(user);
            adminDepositRepository.save(adminDeposit);


        }


        return "Successfully";
    }

    public String confirm_Deposit(Long userid, Long depositId) {

        UserModel user = userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DepositModel depositModel = depositRepository.findById(depositId).orElseThrow(() -> new RuntimeException("Deposit Id Not Found"));


        double amount = depositModel.getAmount();


        BalanceModel balance = user.getBalance();

        if (balance !=null){
            balance.setAvailableBalance(balance.getAvailableBalance() + amount);
            balanceRepository.save(balance);

            depositModel.setStatus(DepositModel.Status.CONFIRMED);
            depositRepository.save(depositModel);
        }
        else{
            BalanceModel balanceModel = new BalanceModel();
            balanceModel.setAvailableBalance(amount);
            balanceModel.setLockedBalance(0.0);
            balanceModel.setUser(user);
            balanceRepository.save(balanceModel);

            depositModel.setStatus(DepositModel.Status.CONFIRMED);
            depositRepository.save(depositModel);

        }

        return "Successfully";
    }


    public String rejectDeposit(Long depositId){

        DepositModel depositModel = depositRepository.findById(depositId).orElseThrow(() -> new RuntimeException("Deposit Id Not Found"));

         depositModel.setStatus(DepositModel.Status.REJECTED);
         depositRepository.save(depositModel);

         return "rejected";

    }

    public String changestatus(Long depositId) {

        DepositModel depositModel = depositRepository.findById(depositId).orElseThrow(() -> new RuntimeException("Deposit Id Not Found"));


        UserModel user = depositModel.getUser();

        double amount = depositModel.getAmount();


        BalanceModel balance = user.getBalance();

        if (balance !=null){
            balance.setAvailableBalance(balance.getAvailableBalance() + amount);
            balanceRepository.save(balance);

            depositModel.setStatus(DepositModel.Status.CONFIRMED);
            depositRepository.save(depositModel);

            handleReferralBonuses(user, amount);
        }
        else{
            BalanceModel balanceModel = new BalanceModel();
            balanceModel.setAvailableBalance(amount);
            balanceModel.setLockedBalance(0.0);
            balanceModel.setUser(user);
            balanceRepository.save(balanceModel);

            depositModel.setStatus(DepositModel.Status.CONFIRMED);
            depositRepository.save(depositModel);
            handleReferralBonuses(user, amount);
        }

        // Fetch deposit by ID
        DepositModel depositModel2 = depositRepository.findById(depositId)
                .orElseThrow(() -> new RuntimeException("Deposit not found"));
         depositModel2.setStatus(DepositModel.Status.CONFIRMED);
         depositRepository.save(depositModel2);
        return "Deposit Confirmed";

    }

    public String deleteDeposit(Long depositId) {
        // Check if the deposit exists
        if (!depositRepository.existsById(depositId)) {
            return "Deposit not found";
        }

        // Delete the deposit
        depositRepository.deleteById(depositId);

        return "Deposit deleted successfully";
    }

    public String rejectAmount(Long depositId){

        DepositModel depositModel = depositRepository.findById(depositId).orElseThrow(() -> new RuntimeException("Deposit Id Not Found"));

        DepositModel depositModel1 = new DepositModel();
        depositModel1.setStatus(DepositModel.Status.REJECTED);
        depositRepository.save(depositModel1);

        return "Deposit  successfully rejected";
    }

    private void handleReferralBonuses(UserModel user, double depositAmount) {
        // Level 1 referral bonus (5%)
        if (user.getReferredBy() != null) {
            double level1Bonus = depositAmount * 0.10; // 5%
            addBonusToUser(user.getReferredBy(), level1Bonus);
        }

        // Level 2 referral bonus (3%)
        if (user.getReferredBy() != null && user.getReferredBy().getReferredBy() != null) {
            double level2Bonus = depositAmount * 0.05; // 3%
            addBonusToUser(user.getReferredBy().getReferredBy(), level2Bonus);
        }

        // Level 3 referral bonus (1%)
        if (user.getReferredBy() != null && user.getReferredBy().getReferredBy() != null
                && user.getReferredBy().getReferredBy().getReferredBy() != null) {
            double level3Bonus = depositAmount * 0.02; // 1%
            addBonusToUser(user.getReferredBy().getReferredBy().getReferredBy(), level3Bonus);
        }
    }

    private void addBonusToUser(UserModel referrer, double bonus) {
        // Fetch the RefBalanceModel associated with the referrer
        Optional<RefBalanceModel> refBalanceOptional = refBalanceRepository.findByUserId(referrer.getId());

        // Create a new RefBalanceModel if it doesn't exist
        RefBalanceModel refBalance;
        if (refBalanceOptional.isPresent()) {
            refBalance = refBalanceOptional.get();
//             Update the available balance with the bonus
            double newBalance = refBalance.getAvailableBalance() + bonus;
            refBalance.setAvailableBalance(newBalance);
           System.out.println("Updating bonus of {} for existing user {}"+bonus+referrer.getId());
        } else {
            // Create a new RefBalanceModel since it doesn't exist
            refBalance = new RefBalanceModel();
            refBalance.setAvailableBalance(bonus); // Set the initial balance to the bonus
            refBalance.setUser(referrer); // Associate with the referrer
            System.out.println("Creating new RefBalanceModel with bonus of {} for user {}"+ bonus+referrer.getId());
        }

        // Save the updated or newly created RefBalanceModel
        refBalanceRepository.save(refBalance);
    }


    public List<DepositModel> getUserDeposits(Long userId) {
        return depositRepository.findAllByUserIdOrderByIdDesc(userId);
    }


    public Optional<DepositModel> getDepositById(Long depositId) {
        return depositRepository.findById(depositId);
    }



    public List<DepositModel> getalluserdeposit(){
        return depositRepository.findAllByOrderByIdDesc();
    }

    public List<DepositModel> getAllPendingDeposits() {
        return depositRepository.findByStatus(DepositModel.Status.PENDING);
    }

    public List<DepositModel> getAllConfirmedAndRejectedDeposits() {
        return depositRepository.findByStatusIn(Arrays.asList(DepositModel.Status.CONFIRMED, DepositModel.Status.REJECTED));
    }

    public List<DepositModel> getAllConfirmedDeposits() {
        return depositRepository.findByStatus(DepositModel.Status.CONFIRMED);
    }



    public List<DepositModel> getAllRejectedDeposits() {
        return depositRepository.findByStatus(DepositModel.Status.REJECTED);
    }
    public WalletModel getWalletByDeposit(Long depositId) {
        return depositRepository.findWalletByDepositId(depositId);
    }

    public List<WalletModel> getWalletsByUser(Long userId) {
        return depositRepository.findWalletsByUserId(userId);
    }

    public String removedeposit(Long depositId) {
        // Fetch deposit by ID
        DepositModel depositModel = depositRepository.findById(depositId)
                .orElseThrow(() -> new RuntimeException("Deposit not found"));

        if (depositModel.getStatus() == DepositModel.Status.CONFIRMED) {
            return "Deposit is already confirmed";
        }

        UserModel user = depositModel.getUser();
        BalanceModel balance = user.getBalance();

        // Deduct from locked balance and add to available balance
        balance.setLockedBalance(balance.getLockedBalance() - depositModel.getAmount());
        balance.setAvailableBalance(balance.getAvailableBalance() + depositModel.getAmount());

        // Update deposit status to CONFIRMED
        depositModel.setStatus(DepositModel.Status.CONFIRMED);
        depositRepository.save(depositModel);

        // Save updated balance
        balanceRepository.save(balance);

        return "Successfully";
    }


}
