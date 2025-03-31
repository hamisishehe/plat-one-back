package com.example.platform.Repository;



import com.example.platform.Model.BalanceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<BalanceModel, Long> {
    Optional<BalanceModel> findByUserId(Long user_id);
    BalanceModel findAllByUserId(Long user_id);

}
