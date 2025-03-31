package com.example.platform.Repository;


import com.example.platform.Model.ProfitModel;
import com.example.platform.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfitRepository extends JpaRepository<ProfitModel, Long> {
    Optional<ProfitModel> findByUser(UserModel user);
    Optional<ProfitModel> findByUserId(Long userId);

}
