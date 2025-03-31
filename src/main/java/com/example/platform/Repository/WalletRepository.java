package com.example.platform.Repository;



import com.example.platform.Model.WalletModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletModel,Long> {
   Optional<WalletModel> findByUserId(Long user_id);
}

