package com.example.platform.Service;



import com.example.platform.Model.BalanceModel;
import com.example.platform.Repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;

    public BalanceModel getbalance (Long user_id){
        return balanceRepository.findAllByUserId(user_id);
    }
    public List<BalanceModel> getallbalance (){
        return balanceRepository.findAll();
    }
}
