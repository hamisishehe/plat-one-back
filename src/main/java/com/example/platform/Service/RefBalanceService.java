package com.example.platform.Service;



import com.example.platform.Model.RefBalanceModel;
import com.example.platform.Repository.RefBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RefBalanceService {

    @Autowired
    private RefBalanceRepository refBalanceRepository;


    public Optional<RefBalanceModel> getRefBalance (Long user_id){
        return refBalanceRepository.findByUserId(user_id);
    }
}
