package com.example.platform.Service;



import com.example.platform.Model.UsdTokenModel;
import com.example.platform.Model.InvestmentModel;
import com.example.platform.Model.ProfitModel;
import com.example.platform.Model.UserModel;
import com.example.platform.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class InvestmentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private USDTokenRepository USDTokenRepository;

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private ProfitRepository profitRepository;

    @Autowired
    private RefBalanceRepository refBalanceRepository;


    public String invest(Long userId, double amount,String invest_package) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UsdTokenModel enaToken = USDTokenRepository.findByUserId(userId)
                .orElse(null);
        if (enaToken == null) {
            return "USD  balance not found for user.";
        }


        if (enaToken.getAvailableBalance() < amount) {
            return "Insufficient USD balance for this plan.";
        }



        ZoneId eastAfricaZone = ZoneId.of("Africa/Nairobi");
        LocalDateTime startDate = ZonedDateTime.now(eastAfricaZone).toLocalDateTime();
        LocalDateTime maturityDate = startDate.plusDays(1);


        if (investmentRepository.existsByUserAndStatus(user, InvestmentModel.InvestmentStatus.RUNNING)) {
            return "An active investment already exists.";
        }


        else {
        // Create a new investment record
        InvestmentModel investment = new InvestmentModel();
        investment.setUser(user);
        investment.setAmount(amount);
        investment.setStatus(InvestmentModel.InvestmentStatus.RUNNING);
        investment.setStartDate(startDate);
        investment.setMaturityDate(maturityDate); // Set maturity to 1 day from now
        investment.setInvestmentPackage(InvestmentModel.InvestmentPackage.valueOf(invest_package)); // Set based on user selection

        investmentRepository.save(investment);

        return "Successfully";
        }
    }

    // Scheduled task to calculate and store daily profit

    @Transactional
    public void calculateAndStoreProfitForMatureInvestments() {
        System.out.println("Running scheduled investment check...");

        List<InvestmentModel> investments = investmentRepository.findAll();

        ZoneId eastAfricaZone = ZoneId.of("Africa/Nairobi");
        LocalDateTime now = ZonedDateTime.now(eastAfricaZone).toLocalDateTime();


        for (InvestmentModel investment : investments) {
            System.out.println("Running scheduled investment check 1 ...");
            if (isInvestmentMature(investment, now)) {
                System.out.println("Running scheduled investment check 2...");
                processMatureInvestment(investment, now);
            }
        }
    }

    private boolean isInvestmentMature(InvestmentModel investment, LocalDateTime currentDate) {
        return (currentDate.isAfter(investment.getMaturityDate()) || currentDate.isEqual(investment.getMaturityDate()))
                && (investment.getStatus() == null || investment.getStatus() != InvestmentModel.InvestmentStatus.FINISHED);
    }

    private void processMatureInvestment(InvestmentModel investment, LocalDateTime currentDate) {
        System.out.println("Processing mature investment ID: " + investment.getId());

        double totalProfit = investment.calculateDailyProfit();

        ProfitModel profitModel = profitRepository.findByUser(investment.getUser()).orElse(null);

        if (profitModel == null || profitModel.getUser() == null) {
            System.out.println("Creating new profit record...");
            ProfitModel newProfit = new ProfitModel();
            newProfit.setUser(investment.getUser());
            newProfit.setTotalProfit(totalProfit);
            newProfit.setLastUpdated(LocalDate.from(currentDate));
            profitRepository.save(newProfit);
        } else {
            System.out.println("Updating existing profit record...");
            profitModel.setTotalProfit(profitModel.getTotalProfit() + totalProfit);
            profitModel.setLastUpdated(LocalDate.from(currentDate));
            profitRepository.save(profitModel);
        }

        investment.setStatus(InvestmentModel.InvestmentStatus.FINISHED);
        investmentRepository.save(investment);
        System.out.println("Investment ID " + investment.getId() + " marked as FINISHED.");
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
        return investmentRepository.findAllFinishedInvestmentsWithUserData(); // This method needs to be defined in InvestmentRepository
    }


    public List<Object[]> getallpendingplans() {
        return investmentRepository.findAllRunningInvestmentsWithUserData(); // This method needs to be defined in InvestmentRepository
    }


    public InvestmentModel getbyinvestmentid(Long investment_id) {
        return investmentRepository.findById(investment_id)
                .orElseThrow(() -> new RuntimeException("investment not found"));
    }

    public String deleteplan(Long planid){
        Optional<InvestmentModel> investment = investmentRepository.findById(planid);
        if (investment.isPresent()) {
            investmentRepository.deleteById(planid);
            return "deleted";
        } else {
           return "not found";
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
