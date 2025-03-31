package com.example.platform.Service;



import com.example.platform.Model.AdminDeposit;
import com.example.platform.Repository.AdminDepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDepositService {

    @Autowired
    private AdminDepositRepository adminDepositRepository;

    public List<AdminDeposit> getall (){
        return adminDepositRepository.findAll();
    }
}
