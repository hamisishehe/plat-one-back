package com.example.platform.Repository;



import com.example.platform.Model.EnaTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnaTokenRepository extends JpaRepository<EnaTokenModel, Long> {
    Optional<EnaTokenModel> findByUserId(Long user_id);
    EnaTokenModel findAllByUserId(Long user_id);
}
