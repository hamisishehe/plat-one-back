package com.example.platform.Service;

import com.example.platform.Model.*;
import com.example.platform.Repository.BalanceRepository;
import com.example.platform.Repository.ProductRepository;
import com.example.platform.Repository.TaskRepository;
import com.example.platform.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private BalanceRepository balanceRepository;

    public List<ProductModel> getAvailableProducts(Long userId) {


        List<ProductModel> allProducts = productRepo.findAll();

        UserModel user = userRepo.findById(userId).orElseThrow();

        BalanceModel balance =balanceRepository.findByUserId(userId)
                .orElse(null);

        assert balance != null;
        double userAmount = balance.getAvailableBalance();

        // Filter products within user amount
        List<ProductModel> filtered = allProducts.stream()
                .filter(p -> p.getPrice() <= userAmount)
                .collect(Collectors.toList());

        // Shuffle for randomness
        Collections.shuffle(filtered);

        // Select first 10 or less
        return filtered.stream().limit(10).collect(Collectors.toList());
    }

}
