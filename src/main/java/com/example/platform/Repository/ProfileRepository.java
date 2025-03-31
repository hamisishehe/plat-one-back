package com.example.platform.Repository;



import com.example.platform.Model.ProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileModel,Long> {
    Optional<ProfileModel> findByUserId(Long user_id);
}
