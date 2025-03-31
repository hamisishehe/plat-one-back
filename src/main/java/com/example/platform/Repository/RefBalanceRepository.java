package com.example.platform.Repository;


import com.example.platform.Model.RefBalanceModel;
import com.example.platform.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefBalanceRepository extends JpaRepository<RefBalanceModel, Long> {
    Optional<RefBalanceModel> findByUserId(Long userId);
    RefBalanceModel findAllByUserId(Long user_id);
    Optional<RefBalanceModel> findByUser(UserModel user);



}
