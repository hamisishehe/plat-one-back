package com.example.platform.Repository;



import com.example.platform.Model.DepositModel;
import com.example.platform.Model.WalletModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepositRepository extends JpaRepository<DepositModel, Long> {
    List<DepositModel> findAllByUserIdOrderByIdDesc(Long user_id);

    List<DepositModel> findAllByStatus(DepositModel.Status status);


    @Query("SELECT d.user.walletModel FROM DepositModel d WHERE d.id = :depositId")
    WalletModel findWalletByDepositId(@Param("depositId") Long depositId);

    // Optionally, find all deposits for a specific user and return their wallets
    @Query("SELECT d.user.walletModel FROM DepositModel d WHERE d.user.id = :userId")
    List<WalletModel> findWalletsByUserId(@Param("userId") Long userId);

    List<DepositModel>findAllByOrderByIdDesc();
}
