package com.example.platform.Repository;



import com.example.platform.Model.RefWithdrawModel;
import com.example.platform.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RefWithdrawRepository extends JpaRepository<RefWithdrawModel, Long> {

    List<RefWithdrawModel> findByUser(UserModel user);
    List<RefWithdrawModel> findAllByUserId(Long user_id);

    @Query("SELECT rw, u FROM RefWithdrawModel rw JOIN rw.user u")
    List<Object[]> findAllRefWithdrawsWithUser();

    List<RefWithdrawModel> findAllByStatus(RefWithdrawModel.Status status);
}
