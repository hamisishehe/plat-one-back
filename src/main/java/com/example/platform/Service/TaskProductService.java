package com.example.platform.Service;


import com.example.platform.Model.*;
import com.example.platform.Repository.BalanceRepository;
import com.example.platform.Repository.ProfitRepository;
import com.example.platform.Repository.TaskProductRepository;
import com.example.platform.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskProductService {

    @Autowired
    private TaskProductRepository taskProductRepository;

    @Autowired
    private ProfitRepository profitRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceRepository balanceRepository;


    public String DoTask(Long user_id, int amount) {
        System.out.println(amount + "......................");
        ZoneId eastAfricaZone = ZoneId.of("Africa/Nairobi");
        LocalDateTime now = ZonedDateTime.now(eastAfricaZone).toLocalDateTime();
        LocalDateTime maturityDate = now.plusDays(1);

        UserModel userModel = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<TaskProductModel> userTasks = taskProductRepository.findByUser(userModel);

        for (TaskProductModel task : userTasks) {
            if (now.isBefore(task.getFinish_time())
                    && task.getStatus() == TaskProductModel.Status.COMPLETE
                    && task.getCount_product() == 10) {
                return "All today tasks complete";
            }

            if (now.isBefore(task.getFinish_time()) && task.getCount_product() < 10) {
                int newCount = task.getCount_product() + 1;
                task.setCount_product(newCount);

                if (newCount == 10) {
                    task.setStatus(TaskProductModel.Status.COMPLETE);

                    BalanceModel balanceModel = balanceRepository.findAllByUserId(user_id);
                    if (balanceModel == null) throw new RuntimeException("Balance not found");

                    int totalAmount = (int) balanceModel.getAvailableBalance();
                    double rate = getRate(totalAmount);
                    double totalProfit = totalAmount * rate;

                    Optional<ProfitModel> profitOptional = profitRepository.findByUser(userModel);
                    if (profitOptional.isPresent()) {
                        ProfitModel existing = profitOptional.get();
                        existing.setTotalProfit(existing.getTotalProfit() + totalProfit);
                        existing.setLastUpdated(LocalDate.now());
                        profitRepository.save(existing);
                    } else {
                        ProfitModel newProfit = new ProfitModel();
                        newProfit.setUser(userModel);
                        newProfit.setTotalProfit(totalProfit);
                        newProfit.setLastUpdated(LocalDate.now());
                        profitRepository.save(newProfit);
                    }
                }

                taskProductRepository.save(task);
                return "Complete";
            }
        }

        // No active/available tasks found: create a new one
        TaskProductModel newTask = new TaskProductModel();
        newTask.setUser(userModel);
        newTask.setStart_time(now);
        newTask.setFinish_time(maturityDate);
        newTask.setCount_product(1);
        newTask.setTotal_amount(amount);
        newTask.setStatus(TaskProductModel.Status.PENDING);
        taskProductRepository.save(newTask);

        return "Complete";
    }

    private double getRate(int totalAmount) {
        if (totalAmount >= 10 && totalAmount <= 100) return 0.02;
        if (totalAmount >=101 && totalAmount<= 200) return 0.025;
        if (totalAmount >=201 && totalAmount<= 500) return 0.03;
        if (totalAmount >=501 && totalAmount<= 1000) return 0.035;
        if (totalAmount >=1001 && totalAmount<= 2000) return 0.04;
        return 0;
    }

    public Optional<TaskProductModel> GetTask(Long user_id) {

        UserModel userModel = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<TaskProductModel> allTasks = taskProductRepository.findByUser(userModel);

        if (allTasks.isEmpty()) {
            return Optional.empty();
        }

        ZoneId eastAfricaZone = ZoneId.of("Africa/Nairobi");
        LocalDateTime now = ZonedDateTime.now(eastAfricaZone).toLocalDateTime();

        for (TaskProductModel task : allTasks) {
            if (task.getStatus() == TaskProductModel.Status.PENDING &&
                    task.getFinish_time().isAfter(now) &&
                    task.getCount_product() <= 10) {
                return Optional.of(task);
            }
        }

        return Optional.empty();
    }

    public List<TaskProductModel> GetAllTask(){
        return taskProductRepository.findAll();
    }

    public boolean hasExpiredTask(Long user_id) {
        UserModel userModel = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ZoneId eastAfricaZone = ZoneId.of("Africa/Nairobi");
        LocalDateTime now = ZonedDateTime.now(eastAfricaZone).toLocalDateTime();

        List<TaskProductModel> userTasks = taskProductRepository.findByUser(userModel);

        for (TaskProductModel task : userTasks) {
            if (now.isBefore(task.getFinish_time())
                    && task.getStatus() == TaskProductModel.Status.COMPLETE
                    && task.getCount_product() == 10) {
                return false;
            }
        }
            // No expired task found
        return true;
    }



}
