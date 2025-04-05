package com.example.platform.Repository;



import com.example.platform.Model.UsdTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface USDTokenRepository extends JpaRepository<UsdTokenModel, Long> {
    Optional<UsdTokenModel> findByUserId(Long user_id);
    UsdTokenModel findAllByUserId(Long user_id);
}
