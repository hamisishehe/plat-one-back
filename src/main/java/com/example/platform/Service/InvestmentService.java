package com.example.platform.Service;



import com.example.platform.Model.EnaTokenModel;
import com.example.platform.Model.InvestmentModel;
import com.example.platform.Model.ProfitModel;
import com.example.platform.Model.UserModel;
import com.example.platform.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class InvestmentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnaTokenRepository enaTokenRepository;

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private ProfitRepository profitRepository;

    @Autowired
    private RefBalanceRepository refBalanceRepository;


    public String invest(Long userId, double amount,String invest_package) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        EnaTokenModel enaToken = enaTokenRepository.findByUserId(userId)
                .orElse(null);
        if (enaToken == null) {
            return "ENA token balance not found for user.";
        }


        if (enaToken.getAvailableBalance() < amount) {
            return "Insufficient ENA balance for this investment.";
        }

        // Deduct the investment amount from the ENA token balance
//        enaToken.setAvailableBalance(enaToken.getAvailableBalance() - amount);

        double currentBalance = enaToken.getAvailableBalance();
        double amountToDeduct = amount; // Assuming amount is a double

        double newBalance = currentBalance - amountToDeduct;

        enaToken.setAvailableBalance(newBalance);
        enaTokenRepository.save(enaToken);

        // Create a new investment record
        InvestmentModel investment = new InvestmentModel();
        investment.setUser(user);
        investment.setAmount(amount);
        investment.setStatus(InvestmentModel.InvestmentStatus.RUNNING);
        investment.setStartDate(LocalDate.now());
        investment.setMaturityDate(LocalDate.now().plusMonths(3)); // Set maturity to 3 months from now
        investment.setInvestmentPackage(InvestmentModel.InvestmentPackage.valueOf(invest_package)); // Set based on user selection

        investmentRepository.save(investment);

        return "Successfully";
    }

    // Scheduled task to calculate and store daily profit
    @Scheduled(cron = "0 0 9 * * Mon")
    public void calculateAndStoreDailyProfit() {

        List<InvestmentModel> investments = investmentRepository.findAll(); // Fetch all investments
        LocalDate today = LocalDate.now();

        for (InvestmentModel investment : investments) {
            // Check if the investment is mature
            if (!investment.isMature()) {
                // Calculate daily profit

                double dailyProfit = investment.calculateDailyProfit();

                ProfitModel profitModel = profitRepository.findByUser(investment.getUser())
                        .orElseGet(() -> createNewProfitModel(investment.getUser()));

                double updatedTotalProfit = profitModel.getTotalProfit() + dailyProfit;

                profitModel.setTotalProfit(updatedTotalProfit);
                profitModel.setLastUpdated(today);

                profitModel.setTotalProfit(updatedTotalProfit);
                profitModel.setLastUpdated(today);
                profitRepository.save(profitModel);

                handleReferralBonuses(investment.getUser(), dailyProfit);

            }
        }
    }

    // Method to create a new ProfitModel
    private ProfitModel createNewProfitModel(UserModel user) {
        ProfitModel profitModel = new ProfitModel();
        profitModel.setUser(user);
        profitModel.setTotalProfit(0.0); // Initialize with zero profit
        profitModel.setLastUpdated(LocalDate.now()); // Set last updated to today
        return profitRepository.save(profitModel);
    }

    public List<InvestmentModel> getUserInvestments(Long userId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return investmentRepository.findAllByUser(user); // This method needs to be defined in InvestmentRepository
    }

    public List<Object[]> getallplans() {
        return investmentRepository.findAllInvestmentsWithUserData(); // This method needs to be defined in InvestmentRepository
    }

    public InvestmentModel getbyinvestmentid(Long investment_id) {
        return investmentRepository.findById(investment_id)
                .orElseThrow(() -> new RuntimeException("investment not found"));
    }

    public void deleteplan(Long planid){
        Optional<InvestmentModel> investment = investmentRepository.findById(planid);
        if (investment.isPresent()) {
            investmentRepository.deleteById(planid);
        } else {
            new RuntimeException("not found");
        }
    }


    private void handleReferralBonuses(UserModel user, double profitAmount) {
        // Level 1 referral bonus (5%)
        if (user.getReferredBy() != null) {
            double level1Bonus = profitAmount * 0.05;
            addBonusToUser(user.getReferredBy(), level1Bonus);
        }

        // Level 2 referral bonus (3%)
        if (user.getReferredBy() != null && user.getReferredBy().getReferredBy() != null) {
            double level2Bonus = profitAmount * 0.03;
            addBonusToUser(user.getReferredBy().getReferredBy(), level2Bonus);
        }

        // Level 3 referral bonus (1%)
        if (user.getReferredBy() != null && user.getReferredBy().getReferredBy() != null
                && user.getReferredBy().getReferredBy().getReferredBy() != null) {
            double level3Bonus = profitAmount * 0.01;
            addBonusToUser(user.getReferredBy().getReferredBy().getReferredBy(), level3Bonus);
        }
    }

    private void addBonusToUser(UserModel referrer, double bonus) {
//        Optional<RefBalanceModel> refBalanceOptional = refBalanceRepository.findByUserId(referrer.getId());
//
//        RefBalanceModel refBalance;
//        if (refBalanceOptional.isPresent()) {
//            refBalance = refBalanceOptional.get();
//            double newBalance = refBalance.getAvailableBalance() + bonus;
//            refBalance.setAvailableBalance(newBalance);
//            System.out.println("Updating bonus of " + bonus + " for existing user " + referrer.getId());
//        } else {
//            refBalance = new RefBalanceModel();
//            refBalance.setAvailableBalance(bonus);
//            refBalance.setUser(referrer);
//            System.out.println("Creating new RefBalanceModel with bonus of " + bonus + " for user " + referrer.getId());
//        }
//
//        refBalanceRepository.save(refBalance);


        ProfitModel profitModel = profitRepository.findByUser(referrer)
                .orElseGet(() -> createNewProfitModel(referrer));

        // Update the total profit with the bonus amount
        double updatedTotalProfit = profitModel.getTotalProfit() + bonus;
        profitModel.setTotalProfit(updatedTotalProfit);
        profitModel.setLastUpdated(LocalDate.now()); // Update last updated date

        profitRepository.save(profitModel);
    }

}
